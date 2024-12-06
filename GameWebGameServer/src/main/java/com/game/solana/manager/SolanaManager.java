package com.game.solana.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.config.RestTemplateFactory;
import com.game.data.myenum.MyEnumCardHistoryType;
import com.game.datagroup.structs.*;
import com.game.pay.manager.PayManager;
import com.game.player.manager.PlayerManager;
import com.game.player.structs.GachaCard;
import com.game.player.structs.GachaCardRefund;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.bitcoinj.core.Base58;
import org.p2p.solanaj.core.Account;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.core.Transaction;
import org.p2p.solanaj.programs.ComputeBudgetProgram;
import org.p2p.solanaj.programs.TokenProgram;
import org.p2p.solanaj.rpc.Cluster;
import org.p2p.solanaj.rpc.RpcClient;
import org.p2p.solanaj.rpc.types.AccountInfo;
import org.p2p.solanaj.rpc.types.ConfirmedTransaction;
import org.p2p.solanaj.rpc.types.SimulatedTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;

/**
 * Solana管理类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/8 12:59
 */
@Component
@Getter
@Log4j2
public class SolanaManager {

    private @Resource MongoTemplate mongoTemplate;
    private @Resource PlayerManager playerManager;
    private @Resource PayManager payManager;
    private @Resource RestTemplateFactory restTemplateFactory;
    private @Value("${server.solana.cnftUri}") String cnftUri;

    /**
     * Solana Rpc Client实例
     */
    private RpcClient rpcClient;
    /**
     * 钱包主账户账户私钥
     */
    private final String secretKey = "**********************************************************************************";
    /**
     * 钱包账户公钥
     */
    private PublicKey userPublicKey;
    /**
     * 钱包主账户
     */
    private final Account account = new Account(Base58.decode(secretKey));
    /**
     * Metadata Program ID
     */
    private final PublicKey TOKEN_METADATA_PROGRAM_ID = new PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s");
    /**
     * mintCollection 账户私钥数据
     */
    private DataGroup dataGroupMintCollectionPrivateKeyData;
    /**
     * 当前transfer nft队列数据
     */
    private DataGroup dataGroupTransferNftQueue;
    /**
     * 当前burn队列数据
     */
    private DataGroup dataGroupBurnQueue;
    /**
     * 当前transfer sol队列数据
     */
    private DataGroup dataGroupTransferSolQueue;

    /**
     * 这块内容来自https://github.com/skynetcap/solanaj-programs.git库，因为maven暂时导入不进来，直接拷贝代码来使用
     */
    private static final int UPDATE_AUTHORITY_OFFSET = 1;
    private static final int MINT_OFFSET = 33;
    private static final int NAME_OFFSET = 65;
    private static final int SYMBOL_OFFSET = 101;
    private static final int URI_OFFSET = 115;

    // Sizes (in bytes)
    private static final int NAME_SIZE = 36;
    private static final int SYMBOL_SIZE = 14;
    private static final int URI_SIZE = 204;

    /**
     * nft卡名称
     */
    private final String nftCardName = "TSG CARD";
    /**
     * nft卡符号
     */
    private final String nftCardSymbol = "TSG";

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        try {
            // 创建一个Solana Rpc Client实例
            rpcClient = new RpcClient(Cluster.MAINNET);
            log.info("Solana version:" + rpcClient.getApi().getVersion());
            userPublicKey = account.getPublicKey();
            long balance = rpcClient.getApi().getBalance(userPublicKey);
            log.info("Solana余额：{}", balance);
            AccountInfo accountInfo = rpcClient.getApi().getAccountInfo(userPublicKey);
            if (accountInfo.getValue() != null) {
                log.info("Solana账户信息：{}", JSON.toJSONString(accountInfo));
            }
            // 读取collectionMint账户私钥
            dataGroupMintCollectionPrivateKeyData = mongoTemplate.findOne(Query.query(Criteria.where("id").is(DataGroupType.SOLANA_NFT_COLLECTION_MINT_KEYPAIR)), DataGroup.class);
            if (dataGroupMintCollectionPrivateKeyData == null || dataGroupMintCollectionPrivateKeyData.getTreePrivateKey().isEmpty()) {
                dataGroupMintCollectionPrivateKeyData = new DataGroup();
                dataGroupMintCollectionPrivateKeyData.setId(DataGroupType.SOLANA_NFT_COLLECTION_MINT_KEYPAIR);
                mongoTemplate.insert(dataGroupMintCollectionPrivateKeyData);
                log.info("collectionMintPrivateKey为空，需要重新生成collectionMint账户！");
                // 抛出错误
                throw new RuntimeException("collectionMintPrivateKey为空，需要重新生成collectionMint账户！");
            }
            // 读取当前进行中的transfer数据队列
            dataGroupTransferNftQueue = mongoTemplate.findOne(Query.query(Criteria.where("id").is(DataGroupType.SOLANA_NFT_TRANSFER_QUEUE)), DataGroup.class);
            if (dataGroupTransferNftQueue == null) {
                dataGroupTransferNftQueue = new DataGroup();
                dataGroupTransferNftQueue.setId(DataGroupType.SOLANA_NFT_TRANSFER_QUEUE);
                mongoTemplate.insert(dataGroupTransferNftQueue);
                log.info("transferQueue为空，初始化transferNftQueue数据！");
            }
            // 读取当前进行中的burn数据队列
            dataGroupBurnQueue = mongoTemplate.findOne(Query.query(Criteria.where("id").is(DataGroupType.SOLANA_NFT_BURN_QUEUE)), DataGroup.class);
            if (dataGroupBurnQueue == null) {
                dataGroupBurnQueue = new DataGroup();
                dataGroupBurnQueue.setId(DataGroupType.SOLANA_NFT_BURN_QUEUE);
                mongoTemplate.insert(dataGroupBurnQueue);
                log.info("burnQueue为空，初始化burnQueue数据！");
            }
            // 读取当前进行中的转账sol数据队列
            dataGroupTransferSolQueue = mongoTemplate.findOne(Query.query(Criteria.where("id").is(DataGroupType.SOLANA_PAY_SOL_TRANSFER_QUEUE)), DataGroup.class);
            if (dataGroupTransferSolQueue == null) {
                dataGroupTransferSolQueue = new DataGroup();
                dataGroupTransferSolQueue.setId(DataGroupType.SOLANA_PAY_SOL_TRANSFER_QUEUE);
                mongoTemplate.insert(dataGroupTransferSolQueue);
                log.info("transferSolQueue为空，初始化transferSolQueue数据！");
            }
            // 测试burn
//            burnNft();
        } catch (Exception e) {
            log.error("初始化异常：", e);
        }
    }

    /**
     * Solana查询已确认的交易详情
     *
     * @param transactionId
     * @return
     */
    public ConfirmedTransaction getTransaction(String transactionId) {
        try {
            // 查询已确认的交易详情
            return rpcClient.getApi().getTransaction(transactionId);
        } catch (Exception e) {
            log.error("Solana查询已确认的交易详情异常：", e);
            return null;
        }
    }

    /**
     * 添加NFT转移任务
     *
     * @param gachaCard
     * @param targetWalletAddress
     */
    public void addTransferNftTask(GachaCard gachaCard, String targetWalletAddress) {
        try {
            SolanaTransferQueueData solanaTransferQueueData = new SolanaTransferQueueData();
            solanaTransferQueueData.setGachaCard(gachaCard);
            solanaTransferQueueData.setTargetWalletAddress(targetWalletAddress);
            dataGroupTransferNftQueue.getSolanaTransferQueueDataList().add(solanaTransferQueueData);
            // 保存数据
            mongoTemplate.save(dataGroupTransferNftQueue);
        } catch (Exception e) {
            log.error("添加NFT转移任务异常：", e);
        }
    }

    /**
     * 添加NFT销毁任务
     *
     * @param reqData
     */
    public void addBurnNftTask(GachaCardRefund reqData) {
        try {
            SolanaBurnQueueData solanaBurnQueueData = new SolanaBurnQueueData();
            solanaBurnQueueData.setData(reqData);
            dataGroupBurnQueue.getSolanaBurnQueueDataList().add(solanaBurnQueueData);
            // 保存数据
            mongoTemplate.save(dataGroupBurnQueue);
        } catch (Exception e) {
            log.error("添加NFT销毁任务异常：", e);
        }
    }

    /**
     * 添加Sol转移任务
     *
     * @param playerId
     * @param gameOrderId
     * @param transactionId
     */
    public void addTransferSolTask(long playerId, String gameOrderId, String transactionId) {
        try {
            SolanaTransferSolQueueData solanaTransferSolQueueData = new SolanaTransferSolQueueData();
            solanaTransferSolQueueData.setPlayerId(playerId);
            solanaTransferSolQueueData.setGameOrderId(gameOrderId);
            solanaTransferSolQueueData.setTransactionId(transactionId);
            dataGroupTransferSolQueue.getSolanaTransferSolQueueDataList().add(solanaTransferSolQueueData);
            // 保存数据
            mongoTemplate.save(dataGroupTransferSolQueue);
        } catch (Exception e) {
            log.error("添加Sol转移任务异常：", e);
        }
    }

    /**
     * 转账nft（非压缩）
     */
//    public void transfer() {
//        try {
//            if (dataGroupTransferNftQueue == null) {
//                // 等待初始化完成
//                return;
//            }
//            // 获取队列第一个数据
//            SolanaTransferQueueData data = dataGroupTransferNftQueue.getSolanaTransferQueueDataList().peek();
//            if (data == null) {
//                return;
//            }
//            int size = dataGroupTransferNftQueue.getSolanaTransferQueueDataList().size();
//            if (size > 1000) {
//                log.info("transferQueue数据量过大:" + size);
//            }
//            // 目标钱包账户公钥
//            PublicKey targetWalletPublicKey = new PublicKey(data.getTargetWalletAddress());
//            // NFT mint账户公钥
//            PublicKey tokenMint = new PublicKey(data.getGachaCard().getMintPublicKey());
//            // 获取源token账户（不存在就先获取）
//            PublicKey source = null;
//            if (data.getSourceTokenAddress().isEmpty()) {
//                source = rpcClient.getApi().getTokenAccountsByOwner(userPublicKey, tokenMint);
//                // 记录到数据中
//                data.setSourceTokenAddress(source.toBase58());
//                mongoTemplate.save(dataGroupTransferNftQueue);
//            } else {
//                source = new PublicKey(data.getSourceTokenAddress());
//            }
//            // 获取目标token账户
//            PublicKey destination = null;
//            // 验证目标token账户是否已创建
//            if (!data.getTargetTokenAddress().isEmpty()) {
//                // 如果已经获取了，直接创建目标token公钥账户
//                destination = new PublicKey(data.getTargetTokenAddress());
//            } else {
//                if (!data.getTransactionAtaId().isEmpty()) {
//                    // 获取创建目标ata账户交易结果
//                    ConfirmedTransaction resTransaction = getTransaction(data.getTransactionAtaId());
//                    if (resTransaction != null) {
//                        if (resTransaction.getMeta().getErr() == null) {
//                            // 验证是否铸造成功
//                            String ataPublicKey = resTransaction.getMeta().getPostTokenBalances().get(0).getMint();
//                            destination = new PublicKey(ataPublicKey);
//                            // 获取目标ata账户成功，清空交易id
//                            data.setTransactionAtaId("");
//                            data.setTargetTokenAddress(ataPublicKey);
//                            data.setFailCount(0);
//                            mongoTemplate.save(dataGroupTransferNftQueue);
//                            log.info("solana transfer 创建 目标token账户 成功：" + ataPublicKey);
//                        } else {
//                            data.setTransactionAtaId("");
//                            data.setFailCount(0);
//                            mongoTemplate.save(dataGroupTransferNftQueue);
//                            log.info("solana transfer 创建 目标token账户 失败，等待重新发起交易，错误信息：" + JSON.toJSONString(resTransaction.getMeta().getErr()));
//                        }
//                    } else {
//                        // 这里可以不保存，反正下次启动又从0开始计数
//                        data.setFailCount(data.getFailCount() + 1);
//                        // 1w次都不成功，重新来
//                        if (data.getFailCount() > 100) {
//                            log.info("solana transfer 创建 目标token账户 失败，等待重新发起交易，data=" + JSON.toJSONString(data));
//                            data.setTransactionAtaId("");
//                            data.setFailCount(0);
//                            mongoTemplate.save(dataGroupTransferNftQueue);
//                        } else {
//                            log.info("等待Solana 创建transfer 目标的 ATA Token 账户(" + data.getFailCount() + ")，交易id：" + data.getTransactionAtaId());
//                        }
//                    }
//                } else {
//                    try {
//                        destination = rpcClient.getApi().getTokenAccountsByOwner(targetWalletPublicKey, tokenMint);
//                        // 获取目标ata账户成功，清空交易id
//                        data.setTransactionAtaId("");
//                        data.setTargetTokenAddress(destination.toBase58());
//                        mongoTemplate.save(dataGroupTransferNftQueue);
//                        log.info("solana transfer 创建 目标token账户 成功：" + data.getTargetTokenAddress());
//                    } catch (RpcException e) {
//                        // 获取目标token账户失败，创建关联token账户
//                        Transaction transaction = new Transaction();
//                        // 设置一个高一点的，反正用不完会退给我
//                        transaction.addInstruction(ComputeBudgetProgram.setComputeUnitLimit(500000));
//                        transaction.addInstruction(ComputeBudgetProgram.setComputeUnitPrice(10000));
//                        TransactionInstruction transactionInstruction = AssociatedTokenProgram.create(account.getPublicKey(), targetWalletPublicKey, tokenMint);
//                        transaction.addInstruction(transactionInstruction);
//                        // 测试创建ATA token账户
////                        transaction.setRecentBlockHash(rpcClient.getApi().getLatestBlockhash());
////                        transaction.sign(List.of(account));
////                        byte[] serializedTransaction = transaction.serialize();
////                        String base64Trx = Base64.getEncoder().encodeToString(serializedTransaction);
////                        SimulatedTransaction simulatedTransaction = rpcClient.getApi().simulateTransaction(base64Trx, List.of(account.getPublicKey()));
////                        System.out.println(simulatedTransaction);
//                        String transactionId = rpcClient.getApi().sendTransaction(transaction, account);
//                        // 记录当前交易id，下次timer使用
//                        data.setTransactionAtaId(transactionId);
//                        mongoTemplate.save(dataGroupTransferNftQueue);
//                        log.info("等待Solana 创建transfer 目标的 ATA Token 账户，交易id：" + data.getTransactionAtaId());
//                    }
//                }
//            }
//            // 在没有获取到目标token账户之前，不进入transfer逻辑
//            if (destination == null) {
//                return;
//            }
//            if (data.getTransactionId().isEmpty()) {
//                // 创建transfer交易
//                // 获取目标token账户之后，直接进入transfer交易
//                Transaction transaction = new Transaction();
//                // 设置一个高一点的，反正用不完会退给我
//                transaction.addInstruction(ComputeBudgetProgram.setComputeUnitLimit(200000));
//                transaction.addInstruction(ComputeBudgetProgram.setComputeUnitPrice(10000));
//                // SPL token instruction
//                transaction.addInstruction(TokenProgram.transferChecked(source, destination, 1L, (byte) 0, userPublicKey, tokenMint));
//                // 测试交易
////                transaction.setRecentBlockHash(rpcClient.getApi().getLatestBlockhash());
////                transaction.sign(List.of(account));
////                byte[] serializedTransaction = transaction.serialize();
////                String base64Trx = Base64.getEncoder().encodeToString(serializedTransaction);
////                SimulatedTransaction simulatedTransaction = rpcClient.getApi().simulateTransaction(base64Trx, List.of(userPublicKey));
////                System.out.println(simulatedTransaction);
////                return "";
//                // 发送创建集合的交易
//                String transactionId = rpcClient.getApi().sendTransaction(transaction, account);
//                data.setTransactionId(transactionId);
//                mongoTemplate.save(dataGroupTransferNftQueue);
//                log.info("执行 gacha Transfer NFT 交易ID：{}", transactionId);
//            } else {
//                ConfirmedTransaction resTransaction = getTransaction(data.getTransactionId());
//                if (resTransaction != null) {
//                    if (resTransaction.getMeta().getErr() == null) {
//                        // 移除队列中的当前任务
//                        dataGroupTransferNftQueue.getSolanaTransferQueueDataList().poll();
//                        mongoTemplate.save(dataGroupTransferNftQueue);
//                        // 更新卡片历史记录
//                        playerManager.updateGachaCardHistory(data.getGachaCard().getOwnerPlayerId(), data.getGachaCard().getId(), MyEnumCardHistoryType.GACHA.getName(), data.getTransactionId());
//                        log.info("移除队列中的当前任务：" + JSON.toJSONString(data) + " 剩余transfer任务数量：" + dataGroupTransferNftQueue.getSolanaTransferQueueDataList().size());
//                        log.info("Solana Nft Transfer 交易成功：" + data.getTransactionId() + " " + JSON.toJSONString(resTransaction));
//                    } else {
//                        data.setTransactionId("");
//                        data.setTransactionAtaId("");
//                        data.setSourceTokenAddress("");
//                        data.setTargetTokenAddress("");
//                        data.setFailCount(0);
//                        mongoTemplate.save(dataGroupTransferNftQueue);
//                        log.info("Solana Nft Transfer 交易失败,等待重新发起transfer交易，错误信息：" + JSON.toJSONString(resTransaction.getMeta().getErr()));
//                    }
//                } else {
//                    // 这里可以不保存，反正下次启动又从0开始计数
//                    data.setFailCount(data.getFailCount() + 1);
//                    // 1w次都不成功，重新来
//                    if (data.getFailCount() > 50) {
//                        SolanaTransferQueueData poll = dataGroupTransferNftQueue.getSolanaTransferQueueDataList().poll();
//                        data.setTransactionId("");
//                        data.setTransactionAtaId("");
//                        data.setSourceTokenAddress("");
//                        // 不删除创建好的ATA token账户，免得再次花费sol创建
////                        data.setTargetTokenAddress("");
//                        data.setFailCount(0);
//                        // 移动至队列末尾(重试1次)(这里不能失败，哎，算了，多试几次)
//                        if (poll == data && data.getRetryCount() < 100) {
//                            data.setRetryCount(data.getRetryCount() + 1);
//                            dataGroupTransferNftQueue.getSolanaTransferQueueDataList().add(data);
//                            log.info("transfer移至队列末尾(准备重试第" + data.getRetryCount() + "次)：" + JSON.toJSONString(data));
//                        }
//                        mongoTemplate.save(dataGroupTransferNftQueue);
//                        log.info("Solana Nft Transfer 交易失败，终止此交易，data=" + JSON.toJSONString(data) + " 剩余transfer任务数量：" + dataGroupTransferNftQueue.getSolanaTransferQueueDataList().size());
//                    } else {
//                        log.info("等待Solana Nft Transfer(" + data.getFailCount() + ")，交易id：" + data.getTransactionId());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error("转账nft异常：", e);
//        }
//    }

    /**
     * 转账nft(压缩)
     */
    public void transfer() {
        try {
            if (dataGroupTransferNftQueue == null) {
                // 等待初始化完成
                return;
            }
            // 获取队列第一个数据
            SolanaTransferQueueData data = dataGroupTransferNftQueue.getSolanaTransferQueueDataList().peek();
            if (data == null) {
                return;
            }
            int size = dataGroupTransferNftQueue.getSolanaTransferQueueDataList().size();
            if (size > 1000) {
                log.info("transferQueue数据量过大:" + size);
            }
            if (data.getTransactionId().isEmpty()) {
                // 构建请求头
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                // 构建请求体
                JSONObject body = new JSONObject();
                body.put("assetId", data.getGachaCard().getAssetId());
                body.put("newOwnerPublicKey", data.getTargetWalletAddress());
                // 创建 ObjectMapper 实例
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonBody = objectMapper.writeValueAsString(body);
                // 创建 HttpEntity
                HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
                ResponseEntity<String> response = restTemplateFactory.getRestTemplate().postForEntity(cnftUri + "/transferNft", requestEntity, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    String responseBody = response.getBody();
                    JSONObject jsonObject = JSON.parseObject(responseBody);
                    if (jsonObject.getIntValue("code") == 0) {
                        log.info("执行 Transfer Nft Card 交易ID：{}", jsonObject);
                        data.setTransactionId(jsonObject.getString("transactionId"));
                        mongoTemplate.save(dataGroupTransferNftQueue);
                        log.info("执行 gacha Transfer NFT 交易ID：{}", jsonObject.getString("transactionId"));
                    } else {
                        log.error("执行 Transfer Nft Card异常：" + jsonObject.getString("msg"));
                        SolanaTransferQueueData poll = dataGroupTransferNftQueue.getSolanaTransferQueueDataList().poll();
                        data.setTransactionId("");
                        data.setTransactionAtaId("");
                        data.setSourceTokenAddress("");
                        // 不删除创建好的ATA token账户，免得再次花费sol创建
//                        data.setTargetTokenAddress("");
                        data.setFailCount(0);
                        // 移动至队列末尾(重试1次)(这里不能失败，哎，算了，多试几次)
                        if (poll == data && data.getRetryCount() < 3) {
                            data.setRetryCount(data.getRetryCount() + 1);
                            dataGroupTransferNftQueue.getSolanaTransferQueueDataList().add(data);
                            log.info("transfer移至队列末尾(准备重试第" + data.getRetryCount() + "次)：" + JSON.toJSONString(data));
                        }
                        mongoTemplate.save(dataGroupTransferNftQueue);
                    }
                } else {
                    log.error("执行 Transfer Nft Card异常code=" + response.getStatusCode());
                }
            } else {
                // 构建请求头
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                // 构建请求体
                JSONObject body = new JSONObject();
                body.put("assetId", data.getGachaCard().getAssetId());
                body.put("newOwnerPublicKey", data.getTargetWalletAddress());
                // 创建 ObjectMapper 实例
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonBody = objectMapper.writeValueAsString(body);
                // 创建 HttpEntity
                HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
                ResponseEntity<String> response = restTemplateFactory.getRestTemplate().postForEntity(cnftUri + "/transferVerify", requestEntity, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    String responseBody = response.getBody();
                    JSONObject jsonObject = JSON.parseObject(responseBody);
                    if (jsonObject.getIntValue("code") == 0) {
                        // 移除队列中的当前任务
                        dataGroupTransferNftQueue.getSolanaTransferQueueDataList().poll();
                        mongoTemplate.save(dataGroupTransferNftQueue);
                        // 更新卡片历史记录
                        playerManager.updateGachaCardHistory(data.getGachaCard().getOwnerPlayerId(), data.getGachaCard().getId(), MyEnumCardHistoryType.GACHA.getName(), data.getTransactionId());
                        log.info("移除队列中的当前任务：" + JSON.toJSONString(data) + " 剩余transfer任务数量：" + dataGroupTransferNftQueue.getSolanaTransferQueueDataList().size());
                        log.info("Solana Nft Transfer 交易成功：" + data.getTransactionId() + " " + responseBody);
                    } else {
                        // 这里可以不保存，反正下次启动又从0开始计数
                        data.setFailCount(data.getFailCount() + 1);
                        // 1w次都不成功，重新来
                        if (data.getFailCount() > 50) {
                            SolanaTransferQueueData poll = dataGroupTransferNftQueue.getSolanaTransferQueueDataList().poll();
                            data.setTransactionId("");
                            data.setTransactionAtaId("");
                            data.setSourceTokenAddress("");
                            // 不删除创建好的ATA token账户，免得再次花费sol创建
//                        data.setTargetTokenAddress("");
                            data.setFailCount(0);
                            // 移动至队列末尾(重试1次)(这里不能失败，哎，算了，多试几次)
                            if (poll == data && data.getRetryCount() < 10) {
                                data.setRetryCount(data.getRetryCount() + 1);
                                dataGroupTransferNftQueue.getSolanaTransferQueueDataList().add(data);
                                log.info("transfer移至队列末尾(准备重试第" + data.getRetryCount() + "次)：" + JSON.toJSONString(data));
                            }
                            mongoTemplate.save(dataGroupTransferNftQueue);
                            log.info("Solana Nft Transfer 交易失败，终止此交易，data=" + JSON.toJSONString(data) + " 剩余transfer任务数量：" + dataGroupTransferNftQueue.getSolanaTransferQueueDataList().size());
                        } else {
                            log.info("等待Solana Nft Transfer(" + data.getFailCount() + ")，交易id：" + data.getTransactionId());
                        }
                    }
                } else {
                    data.setTransactionId("");
                    data.setTransactionAtaId("");
                    data.setSourceTokenAddress("");
                    data.setTargetTokenAddress("");
                    data.setFailCount(0);
                    mongoTemplate.save(dataGroupTransferNftQueue);
                    log.info("Solana Nft Transfer 交易失败,等待重新发起transfer交易，错误信息：" + response.getStatusCode());
                }
            }
        } catch (Exception e) {
            log.error("转账nft异常：", e);
        }
    }

    /**
     * 销毁nft(非压缩)
     */
//    public void burn() {
//        try {
//            if (dataGroupBurnQueue == null) {
//                // 等待初始化完成
//                return;
//            }
//            // 获取队列第一个数据
//            SolanaBurnQueueData queueData = dataGroupBurnQueue.getSolanaBurnQueueDataList().peek();
//            if (queueData == null) {
//                return;
//            }
//            int size = dataGroupBurnQueue.getSolanaBurnQueueDataList().size();
//            if (size > 1000) {
//                log.info("burnQueue数据量过大:" + size);
//            }
//            GachaCardRefund data = queueData.getData();
//            ConfirmedTransaction resTransaction = getTransaction(data.getTransactionId());
//            if (resTransaction != null) {
//                if (resTransaction.getMeta().getErr() == null) {
//                    log.info("burn 收到交易返回 resTransaction=" + JSON.toJSONString(resTransaction));
//                    // 验证是否burn成功
//                    String burnMintPublicKey = resTransaction.getMeta().getPreTokenBalances().get(0).getMint();
//                    // 根据mint账户获得meta账户公钥
//                    PublicKey metadataPublicKey = PublicKey.findProgramAddress(List.of("metadata".getBytes(StandardCharsets.UTF_8), TOKEN_METADATA_PROGRAM_ID.toByteArray(), PublicKey.valueOf(burnMintPublicKey).toByteArray()), TOKEN_METADATA_PROGRAM_ID).getAddress();
//                    AccountInfo metaAccountInfo = rpcClient.getApi().getAccountInfo(metadataPublicKey);
//                    // 获取元数据中的uri
//                    byte[] decodedData = null;
//                    try {
//                        decodedData = metaAccountInfo.getDecodedData();
//                    } catch (Exception e) {
//                        // 这里可以不保存，反正下次启动又从0开始计数
//                        queueData.setFailCount(queueData.getFailCount() + 1);
//                        // 1w次都不成功，打印日志，放弃
//                        if (queueData.getFailCount() > 100) {
//                            // 移除队列中的当前任务
//                            dataGroupBurnQueue.getSolanaBurnQueueDataList().poll();
//                            mongoTemplate.save(dataGroupBurnQueue);
//                            log.error("销毁卡片异常，多次尝试找不到此销毁卡片的元数据信息，metadataPublicKey=" + metadataPublicKey + " data:" + JSON.toJSONString(data));
//                        } else {
//                            log.error("Solana 验证 burn NFT失败：还未正确获取到NFT token账户下的元数据，稍后再试... burnTokenPublicKey=" + burnMintPublicKey);
//                        }
//                        return;
//                    }
//                    Metadata metadata = Metadata.builder().updateAuthority(PublicKey.readPubkey(decodedData, UPDATE_AUTHORITY_OFFSET)).tokenMint(PublicKey.readPubkey(decodedData, MINT_OFFSET)).name(new String(ByteUtils.readBytes(decodedData, NAME_OFFSET, NAME_SIZE)).trim()).symbol(new String(ByteUtils.readBytes(decodedData, SYMBOL_OFFSET, SYMBOL_SIZE)).trim()).uri(new String(ByteUtils.readBytes(decodedData, URI_OFFSET, URI_SIZE)).trim()).build();
////                // 将新的二进制数组转换为字符串
//                    String metaData = JSON.toJSONString(metadata);
//                    log.info("burn 卡片解码元数据：" + metaData);
//                    if (metadata.getSymbol().contains(nftCardSymbol)) {
//                        log.info("移除队列中的当前burn任务：" + JSON.toJSONString(data));
//                        log.info("Solana Nft burn 交易验证成功：" + data.getTransactionId() + " " + JSON.toJSONString(resTransaction));
//                        // 获取卡片id
//                        String cardId = metadata.getUri().split("cardId=")[1];
//                        // burn成功后，删除卡片信息发放奖励
//                        playerManager.burnCard(data.getPlayerId(), cardId, data);
//                        log.info("burn 卡片逻辑完成playerId=" + data.getPlayerId() + " id：" + cardId);
//                    } else {
//                        log.info("移除队列中的当前burn任务：" + JSON.toJSONString(data));
//                        log.error("Solana Nft burn 交易验证失败：" + data.getTransactionId() + " " + JSON.toJSONString(resTransaction));
//                    }
//                    // 移除队列中的当前任务
//                    dataGroupBurnQueue.getSolanaBurnQueueDataList().poll();
//                    mongoTemplate.save(dataGroupBurnQueue);
//                    log.info("剩余burn任务数量：" + dataGroupBurnQueue.getSolanaBurnQueueDataList().size());
//                } else {
//                    // 移除队列中的当前任务
//                    dataGroupBurnQueue.getSolanaBurnQueueDataList().poll();
//                    mongoTemplate.save(dataGroupBurnQueue);
//                    log.info("Solana Nft burn 交易失败，data=" + JSON.toJSONString(data) + " 错误信息：" + JSON.toJSONString(resTransaction.getMeta().getErr()));
//                }
//            } else {
//                // 这里可以不保存，反正下次启动又从0开始计数
//                queueData.setFailCount(queueData.getFailCount() + 1);
//                // 1w次都不成功，打印日志，放弃
//                if (queueData.getFailCount() > 100) {
//                    // 移除队列中的当前任务
//                    SolanaBurnQueueData poll = dataGroupBurnQueue.getSolanaBurnQueueDataList().poll();
//                    // 添加至末尾(移至队列末尾，最多重试2次)
//                    if (poll != null && poll.getRetryCount() < 2) {
//                        poll.setRetryCount(poll.getRetryCount() + 1);
//                        dataGroupBurnQueue.getSolanaBurnQueueDataList().addLast(poll);
//                        log.info("burn 卡片重试次数：" + poll.getRetryCount() + " burn信息：" + JSON.toJSONString(poll));
//                    }
//                    mongoTemplate.save(dataGroupBurnQueue);
//                    log.error("销毁卡片异常，多次尝试找不到此销毁交易信息，移至队列末尾，transactionId=" + data.getTransactionId() + " data:" + JSON.toJSONString(data));
//                } else {
//                    log.info("等待Solana Nft burn(" + queueData.getFailCount() + ")，交易id：" + data.getTransactionId());
//                }
//            }
//        } catch (Exception e) {
//            log.error("销毁nft异常：", e);
//        }
//    }

    /**
     * 销毁nft(压缩)
     */
    public void burn() {
        try {
            if (dataGroupBurnQueue == null) {
                // 等待初始化完成
                return;
            }
            // 获取队列第一个数据
            SolanaBurnQueueData queueData = dataGroupBurnQueue.getSolanaBurnQueueDataList().peek();
            if (queueData == null) {
                return;
            }
            int size = dataGroupBurnQueue.getSolanaBurnQueueDataList().size();
            if (size > 1000) {
                log.info("burnQueue数据量过大:" + size);
            }
            GachaCardRefund data = queueData.getData();
            GachaCard gachaCard = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(data.getGachaCardId())), GachaCard.class);
            if (gachaCard == null) {
                log.error("销毁卡片返还资源异常：卡片不存在 id=" + data.getGachaCardId());
                return;
            }
            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // 构建请求体
            JSONObject body = new JSONObject();
            body.put("assetId", gachaCard.getAssetId());
            // 创建 ObjectMapper 实例
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(body);
            // 创建 HttpEntity
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<String> response = restTemplateFactory.getRestTemplate().postForEntity(cnftUri + "/burnVerify", requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                JSONObject jsonObject = JSON.parseObject(responseBody);
                if (jsonObject.getIntValue("code") == 0) {
                    log.info("移除队列中的当前burn任务：" + JSON.toJSONString(data));
                    log.info("Solana Nft burn 交易验证成功!");
                    // burn成功后，删除卡片信息发放奖励
                    playerManager.burnCard(data.getPlayerId(), gachaCard, data);
                    log.info("burn 卡片逻辑完成playerId=" + data.getPlayerId() + " id：" + gachaCard.getId());
                    // 移除队列中的当前任务
                    dataGroupBurnQueue.getSolanaBurnQueueDataList().poll();
                    mongoTemplate.save(dataGroupBurnQueue);
                    log.info("剩余burn任务数量：" + dataGroupBurnQueue.getSolanaBurnQueueDataList().size());
                } else {
                    // 这里可以不保存，反正下次启动又从0开始计数
                    queueData.setFailCount(queueData.getFailCount() + 1);
                    // 1w次都不成功，打印日志，放弃
                    if (queueData.getFailCount() > 100) {
                        // 移除队列中的当前任务
                        SolanaBurnQueueData poll = dataGroupBurnQueue.getSolanaBurnQueueDataList().poll();
                        // 添加至末尾(移至队列末尾，最多重试2次)
                        if (poll != null && poll.getRetryCount() < 2) {
                            poll.setRetryCount(poll.getRetryCount() + 1);
                            dataGroupBurnQueue.getSolanaBurnQueueDataList().addLast(poll);
                            log.info("burn 卡片重试次数：" + poll.getRetryCount() + " burn信息：" + JSON.toJSONString(poll));
                        }
                        mongoTemplate.save(dataGroupBurnQueue);
                        log.error("销毁卡片异常，多次尝试找不到此销毁交易信息，移至队列末尾，transactionId=" + data.getTransactionId() + " data:" + JSON.toJSONString(data));
                    } else {
                        log.info("等待Solana Nft burn(" + queueData.getFailCount() + ")，交易id：" + data.getTransactionId());
                    }
                }
            } else {
                // 移除队列中的当前任务
                dataGroupBurnQueue.getSolanaBurnQueueDataList().poll();
                mongoTemplate.save(dataGroupBurnQueue);
                log.info("Solana Nft burn 交易失败，data=" + JSON.toJSONString(data) + " 错误信息：" + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("销毁nft异常：", e);
        }
    }

    /**
     * 充值转账Sol（钱包充值到游戏钱包账户）
     */
    public void transferSol() {
        try {
            if (dataGroupTransferSolQueue == null) {
                // 等待初始化完成
                return;
            }
            // 获取队列第一个数据
            SolanaTransferSolQueueData data = dataGroupTransferSolQueue.getSolanaTransferSolQueueDataList().peek();
            if (data == null) {
                return;
            }
            int size = dataGroupTransferSolQueue.getSolanaTransferSolQueueDataList().size();
            if (size > 1000) {
                log.info("transfer Sol Queue数据量过大:" + size);
            }
            ConfirmedTransaction resTransaction = getTransaction(data.getTransactionId());
            if (resTransaction != null) {
                log.info("solana transfer sol 获取交易结果(钱包充值)：" + JSON.toJSONString(resTransaction));
                if (resTransaction.getMeta().getErr() == null) {
                    // 解析钱包充值，转账sol数据
                    // 转账sol数量，这里是1sol=10亿Balance
                    Long transferSol = resTransaction.getMeta().getPostBalances().get(1) - resTransaction.getMeta().getPreBalances().get(1);
                    // 获取转账者的钱包
                    String sourceWalletAddress = resTransaction.getTransaction().getMessage().getAccountKeys().get(0);
                    // 获取转账目标的钱包
                    String targetWalletAddress = resTransaction.getTransaction().getMessage().getAccountKeys().get(1);
                    log.info("钱包充值转账获取转账数据，发起转账钱包地址=" + sourceWalletAddress + " 目标钱包地址=" + targetWalletAddress + " 实际充值sol=" + transferSol);
                    // 移除队列中的当前任务
                    dataGroupTransferSolQueue.getSolanaTransferSolQueueDataList().poll();
                    mongoTemplate.save(dataGroupTransferSolQueue);
                    log.info("移除队列中的当前任务：" + JSON.toJSONString(data) + " 剩余transfer sol(钱包充值)任务数量：" + dataGroupTransferSolQueue.getSolanaTransferSolQueueDataList().size());
                    log.info("Solana Sol Transfer 交易成功(钱包充值)，进入充值验证：" + data.getTransactionId() + " " + JSON.toJSONString(resTransaction));
                    // 执行充值发放奖励
                    payManager.updatePlayerPayData(data, sourceWalletAddress, targetWalletAddress, transferSol);
                } else {
                    // 移除队列中的当前任务
                    dataGroupTransferSolQueue.getSolanaTransferSolQueueDataList().poll();
                    mongoTemplate.save(dataGroupTransferSolQueue);
                    log.info("Solana Sol Transfer (钱包充值)充值转账交易失败,终止transfer sol（钱包转账）交易，错误信息：" + JSON.toJSONString(resTransaction.getMeta().getErr()));
                }
            } else {
                // 这里可以不保存，反正下次启动又从0开始计数
                data.setFailCount(data.getFailCount() + 1);
                // 1w次都不成功，重新来
                if (data.getFailCount() > 100) {
                    SolanaTransferSolQueueData poll = dataGroupTransferSolQueue.getSolanaTransferSolQueueDataList().poll();
                    data.setFailCount(0);
                    // 移动至队列末尾(重试1次)
                    if (poll == data && data.getRetryCount() < 1) {
                        data.setRetryCount(data.getRetryCount() + 1);
                        dataGroupTransferSolQueue.getSolanaTransferSolQueueDataList().add(data);
                        log.info("transfer sol (钱包充值)移至队列末尾(重试" + data.getRetryCount() + ")：" + JSON.toJSONString(data));
                    }
                    mongoTemplate.save(dataGroupTransferSolQueue);
                    log.info("Solana Sol Transfer (钱包充值)交易失败，终止此交易，data=" + JSON.toJSONString(data) + " 剩余transfer sol任务数量：" + dataGroupTransferSolQueue.getSolanaTransferSolQueueDataList().size());
                } else {
                    log.info("等待Solana Sol (钱包充值)Transfer(" + data.getFailCount() + ")，交易id：" + data.getTransactionId());
                }
            }
        } catch (Exception e) {
            log.error("充值转账Sol异常：", e);
        }
    }

    /**
     * 测试burnNft（游戏中，用户burn自己的卡片，这里只是测试burn自己的卡片是否能跑通）(非压缩)
     */
    public void burnNft() {
        try {
            // 测试burn
            GachaCard card = mongoTemplate.findOne(Query.query(Criteria.where("id").is("7417668863929614339")), GachaCard.class);
            // NFT mint账户公钥
            PublicKey tokenMint = new PublicKey(card.getMintPublicKey());
            // 获取源token账户（不存在就先获取）
            PublicKey source = rpcClient.getApi().getTokenAccountsByOwner(userPublicKey, tokenMint);
            Transaction transaction = new Transaction();
            // 设置一个高一点的，反正用不完会退给我
            transaction.addInstruction(ComputeBudgetProgram.setComputeUnitLimit(200000));
            transaction.addInstruction(ComputeBudgetProgram.setComputeUnitPrice(10000));
            // SPL token instruction
            transaction.addInstruction(TokenProgram.burn(source, tokenMint, userPublicKey, 1));
            // 测试交易
            transaction.setRecentBlockHash(rpcClient.getApi().getLatestBlockhash().getValue().getBlockhash());
            transaction.sign(List.of(account));
            byte[] serializedTransaction = transaction.serialize();
            String base64Trx = Base64.getEncoder().encodeToString(serializedTransaction);
            SimulatedTransaction simulatedTransaction = rpcClient.getApi().simulateTransaction(base64Trx, List.of(userPublicKey));
            System.out.println("测试burn：" + simulatedTransaction);
            // 发送创建集合的交易
//            String transactionId = rpcClient.getApi().sendTransaction(transaction, List.of(account), rpcClient.getApi().getLatestBlockhash());
//            System.out.println(transactionId);
        } catch (Exception e) {
            log.error("测试burnNft（游戏中，用户burn自己的卡片，这里只是测试burn自己的卡片是否能跑通）异常：", e);
        }
    }

}
