package com.game.solana.manager;

import com.alibaba.fastjson2.JSON;
import com.game.datagroup.structs.DataGroup;
import com.game.datagroup.structs.DataGroupType;
import com.game.datagroup.structs.SolanaBurnQueueData;
import com.game.datagroup.structs.SolanaTransferQueueData;
import com.game.player.manager.PlayerManager;
import com.game.player.structs.GachaCard;
import com.game.player.structs.GachaCardRefund;
import com.game.solana.structs.Metadata;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.bitcoinj.core.Base58;
import org.p2p.solanaj.core.Account;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.core.Transaction;
import org.p2p.solanaj.core.TransactionInstruction;
import org.p2p.solanaj.programs.AssociatedTokenProgram;
import org.p2p.solanaj.programs.ComputeBudgetProgram;
import org.p2p.solanaj.programs.TokenProgram;
import org.p2p.solanaj.rpc.Cluster;
import org.p2p.solanaj.rpc.RpcClient;
import org.p2p.solanaj.rpc.RpcException;
import org.p2p.solanaj.rpc.types.AccountInfo;
import org.p2p.solanaj.rpc.types.ConfirmedTransaction;
import org.p2p.solanaj.rpc.types.SimulatedTransaction;
import org.p2p.solanaj.utils.ByteUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
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
@Log4j2
public class SolanaManager {

    private @Resource MongoTemplate mongoTemplate;
    private @Resource PlayerManager playerManager;

    /**
     * Solana Rpc Client实例
     */
    private RpcClient rpcClient;
    /**
     * 钱包主账户账户私钥
     */
    private final String secretKey = "2TN32qYEM51YE7WrJ7jSRsGjAxiKdyoNNbskRxYpnJFB5ccHRPsVa7zDKauVnKCFeJcrqwtbemHJTkpZXn4ak6pj";
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
     * 当前transfer队列数据
     */
    private DataGroup dataGroupTransferQueue;
    /**
     * 当前burn队列数据
     */
    private DataGroup dataGroupBurnQueue;

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
            rpcClient = new RpcClient(Cluster.TESTNET);
            log.info("Solana version:" + rpcClient.getApi().getVersion());
            userPublicKey = account.getPublicKey();
            long balance = rpcClient.getApi().getBalance(userPublicKey);
            log.info("Solana余额：{}", balance);
            AccountInfo accountInfo = rpcClient.getApi().getAccountInfo(userPublicKey);
            log.info("Solana账户信息：{}", JSON.toJSONString(accountInfo));
            // 读取当前进行中的transfer数据队列
            dataGroupTransferQueue = mongoTemplate.findOne(Query.query(Criteria.where("id").is(DataGroupType.SOLANA_NFT_TRANSFER_QUEUE)), DataGroup.class);
            if (dataGroupTransferQueue == null) {
                dataGroupTransferQueue = new DataGroup();
                dataGroupTransferQueue.setId(DataGroupType.SOLANA_NFT_TRANSFER_QUEUE);
                mongoTemplate.insert(dataGroupTransferQueue);
                log.info("transferQueue为空，初始化transferQueue数据！");
            }
            // 读取当前进行中的burn数据队列
            dataGroupBurnQueue = mongoTemplate.findOne(Query.query(Criteria.where("id").is(DataGroupType.SOLANA_NFT_BURN_QUEUE)), DataGroup.class);
            if (dataGroupBurnQueue == null) {
                dataGroupBurnQueue = new DataGroup();
                dataGroupBurnQueue.setId(DataGroupType.SOLANA_NFT_BURN_QUEUE);
                mongoTemplate.insert(dataGroupBurnQueue);
                log.info("burnQueue为空，初始化burnQueue数据！");
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
            dataGroupTransferQueue.getSolanaTransferQueueDataList().add(solanaTransferQueueData);
            // 保存数据
            mongoTemplate.save(dataGroupTransferQueue);
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
     * 转账nft
     */
    public void transfer() {
        try {
            if (dataGroupTransferQueue == null) {
                // 等待初始化完成
                return;
            }
            // 获取队列第一个数据
            SolanaTransferQueueData data = dataGroupTransferQueue.getSolanaTransferQueueDataList().peek();
            if (data == null) {
                return;
            }
            int size = dataGroupTransferQueue.getSolanaTransferQueueDataList().size();
            if (size > 1000) {
                log.info("transferQueue数据量过大:" + size);
            }
            // 目标钱包账户公钥
            PublicKey targetWalletPublicKey = new PublicKey(data.getTargetWalletAddress());
            // NFT mint账户公钥
            PublicKey tokenMint = new PublicKey(data.getGachaCard().getMintPublicKey());
            // 获取源token账户（不存在就先获取）
            PublicKey source = null;
            if (data.getSourceTokenAddress().isEmpty()) {
                source = rpcClient.getApi().getTokenAccountsByOwner(userPublicKey, tokenMint);
                // 记录到数据中
                data.setSourceTokenAddress(source.toBase58());
                mongoTemplate.save(dataGroupTransferQueue);
            } else {
                source = new PublicKey(data.getSourceTokenAddress());
            }
            // 获取目标token账户
            PublicKey destination = null;
            // 验证目标token账户是否已创建
            if (!data.getTargetTokenAddress().isEmpty()) {
                // 如果已经获取了，直接创建目标token公钥账户
                destination = new PublicKey(data.getTargetTokenAddress());
            } else {
                if (!data.getTransactionAtaId().isEmpty()) {
                    // 获取创建目标ata账户交易结果
                    ConfirmedTransaction resTransaction = getTransaction(data.getTransactionAtaId());
                    if (resTransaction != null) {
                        if (resTransaction.getMeta().getErr() == null) {
                            // 验证是否铸造成功
                            String ataPublicKey = resTransaction.getMeta().getPostTokenBalances().get(0).getMint();
                            destination = new PublicKey(ataPublicKey);
                            // 获取目标ata账户成功，清空交易id
                            data.setTransactionAtaId("");
                            data.setTargetTokenAddress(ataPublicKey);
                            data.setFailCount(0);
                            mongoTemplate.save(dataGroupTransferQueue);
                            log.info("solana transfer 创建 目标token账户 成功：" + ataPublicKey);
                        } else {
                            data.setTransactionAtaId("");
                            data.setFailCount(0);
                            mongoTemplate.save(dataGroupTransferQueue);
                            log.info("solana transfer 创建 目标token账户 失败，等待重新发起交易，错误信息：" + JSON.toJSONString(resTransaction.getMeta().getErr()));
                        }
                    } else {
                        // 这里可以不保存，反正下次启动又从0开始计数
                        data.setFailCount(data.getFailCount() + 1);
                        // 1w次都不成功，重新来
                        if (data.getFailCount() > 100) {
                            log.info("solana transfer 创建 目标token账户 失败，等待重新发起交易，data=" + JSON.toJSONString(data));
                            data.setTransactionAtaId("");
                            data.setFailCount(0);
                            mongoTemplate.save(dataGroupTransferQueue);
                        } else {
                            log.info("等待Solana 创建transfer 目标的 ATA Token 账户(" + data.getFailCount() + ")，交易id：" + data.getTransactionAtaId());
                        }
                    }
                } else {
                    try {
                        destination = rpcClient.getApi().getTokenAccountsByOwner(targetWalletPublicKey, tokenMint);
                        // 获取目标ata账户成功，清空交易id
                        data.setTransactionAtaId("");
                        data.setTargetTokenAddress(destination.toBase58());
                        mongoTemplate.save(dataGroupTransferQueue);
                        log.info("solana transfer 创建 目标token账户 成功：" + data.getTargetTokenAddress());
                    } catch (RpcException e) {
                        // 获取目标token账户失败，创建关联token账户
                        Transaction transaction = new Transaction();
                        // 设置一个高一点的，反正用不完会退给我
                        transaction.addInstruction(ComputeBudgetProgram.setComputeUnitLimit(500000));
                        transaction.addInstruction(ComputeBudgetProgram.setComputeUnitPrice(10000));
                        TransactionInstruction transactionInstruction = AssociatedTokenProgram.create(account.getPublicKey(), targetWalletPublicKey, tokenMint);
                        transaction.addInstruction(transactionInstruction);
                        // 测试创建ATA token账户
//                        transaction.setRecentBlockHash(rpcClient.getApi().getLatestBlockhash());
//                        transaction.sign(List.of(account));
//                        byte[] serializedTransaction = transaction.serialize();
//                        String base64Trx = Base64.getEncoder().encodeToString(serializedTransaction);
//                        SimulatedTransaction simulatedTransaction = rpcClient.getApi().simulateTransaction(base64Trx, List.of(account.getPublicKey()));
//                        System.out.println(simulatedTransaction);
                        String transactionId = rpcClient.getApi().sendTransaction(transaction, account);
                        // 记录当前交易id，下次timer使用
                        data.setTransactionAtaId(transactionId);
                        mongoTemplate.save(dataGroupTransferQueue);
                        log.info("等待Solana 创建transfer 目标的 ATA Token 账户，交易id：" + data.getTransactionAtaId());
                    }
                }
            }
            // 在没有获取到目标token账户之前，不进入transfer逻辑
            if (destination == null) {
                return;
            }
            if (data.getTransactionId().isEmpty()) {
                // 创建transfer交易
                // 获取目标token账户之后，直接进入transfer交易
                Transaction transaction = new Transaction();
                // 设置一个高一点的，反正用不完会退给我
                transaction.addInstruction(ComputeBudgetProgram.setComputeUnitLimit(200000));
                transaction.addInstruction(ComputeBudgetProgram.setComputeUnitPrice(10000));
                // SPL token instruction
                transaction.addInstruction(TokenProgram.transferChecked(source, destination, 1L, (byte) 0, userPublicKey, tokenMint));
                // 测试交易
//                transaction.setRecentBlockHash(rpcClient.getApi().getLatestBlockhash());
//                transaction.sign(List.of(account));
//                byte[] serializedTransaction = transaction.serialize();
//                String base64Trx = Base64.getEncoder().encodeToString(serializedTransaction);
//                SimulatedTransaction simulatedTransaction = rpcClient.getApi().simulateTransaction(base64Trx, List.of(userPublicKey));
//                System.out.println(simulatedTransaction);
//                return "";
                // 发送创建集合的交易
                String transactionId = rpcClient.getApi().sendTransaction(transaction, account);
                data.setTransactionId(transactionId);
                mongoTemplate.save(dataGroupTransferQueue);
                log.info("执行 gacha Transfer NFT 交易ID：{}", transactionId);
            } else {
                ConfirmedTransaction resTransaction = getTransaction(data.getTransactionId());
                if (resTransaction != null) {
                    if (resTransaction.getMeta().getErr() == null) {
                        // 移除队列中的当前任务
                        dataGroupTransferQueue.getSolanaTransferQueueDataList().poll();
                        mongoTemplate.save(dataGroupTransferQueue);
                        log.info("移除队列中的当前任务：" + JSON.toJSONString(data) + " 剩余transfer任务数量：" + dataGroupTransferQueue.getSolanaTransferQueueDataList().size());
                        log.info("Solana Nft Transfer 交易成功：" + data.getTransactionId() + " " + JSON.toJSONString(resTransaction));
                    } else {
                        data.setTransactionId("");
                        data.setTransactionAtaId("");
                        data.setSourceTokenAddress("");
                        data.setTargetTokenAddress("");
                        data.setFailCount(0);
                        mongoTemplate.save(dataGroupTransferQueue);
                        log.info("Solana Nft Transfer 交易失败,等待重新发起transfer交易，错误信息：" + JSON.toJSONString(resTransaction.getMeta().getErr()));
                    }
                } else {
                    // 这里可以不保存，反正下次启动又从0开始计数
                    data.setFailCount(data.getFailCount() + 1);
                    // 1w次都不成功，重新来
                    if (data.getFailCount() > 100) {
                        log.info("Solana Nft Transfer 交易失败,等待重新发起transfer交易，data=" + JSON.toJSONString(data));
                        data.setTransactionId("");
                        data.setTransactionAtaId("");
                        data.setSourceTokenAddress("");
                        data.setTargetTokenAddress("");
                        data.setFailCount(0);
                        mongoTemplate.save(dataGroupTransferQueue);
                    } else {
                        log.info("等待Solana Nft Transfer(" + data.getFailCount() + ")，交易id：" + data.getTransactionId());
                    }
                }
            }
        } catch (Exception e) {
            log.error("转账nft异常：", e);
        }
    }

    /**
     * 销毁nft
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
            ConfirmedTransaction resTransaction = getTransaction(data.getTransactionId());
            if (resTransaction != null) {
                if (resTransaction.getMeta().getErr() == null) {
                    // 验证是否burn成功
                    String burnMintPublicKey = resTransaction.getMeta().getPostTokenBalances().get(0).getMint();
                    // 根据mint账户获得meta账户公钥
                    PublicKey metadataPublicKey = PublicKey.findProgramAddress(List.of("metadata".getBytes(StandardCharsets.UTF_8), TOKEN_METADATA_PROGRAM_ID.toByteArray(), PublicKey.valueOf(burnMintPublicKey).toByteArray()), TOKEN_METADATA_PROGRAM_ID).getAddress();
                    AccountInfo metaAccountInfo = rpcClient.getApi().getAccountInfo(metadataPublicKey);
                    // 获取元数据中的uri
                    byte[] decodedData = null;
                    try {
                        decodedData = metaAccountInfo.getDecodedData();
                    } catch (Exception e) {
                        // 这里可以不保存，反正下次启动又从0开始计数
                        queueData.setFailCount(queueData.getFailCount() + 1);
                        // 1w次都不成功，打印日志，放弃
                        if (queueData.getFailCount() > 100) {
                            // 移除队列中的当前任务
                            dataGroupBurnQueue.getSolanaBurnQueueDataList().poll();
                            mongoTemplate.save(dataGroupBurnQueue);
                            log.error("销毁卡片异常，多次尝试找不到此销毁卡片的元数据信息，metadataPublicKey=" + metadataPublicKey + " data:" + JSON.toJSONString(data));
                        } else {
                            log.error("Solana 验证 burn NFT失败：还未正确获取到NFT token账户下的元数据，稍后再试... burnTokenPublicKey=" + burnMintPublicKey);
                        }
                        return;
                    }
                    Metadata metadata = Metadata.builder().updateAuthority(PublicKey.readPubkey(decodedData, UPDATE_AUTHORITY_OFFSET)).tokenMint(PublicKey.readPubkey(decodedData, MINT_OFFSET)).name(new String(ByteUtils.readBytes(decodedData, NAME_OFFSET, NAME_SIZE)).trim()).symbol(new String(ByteUtils.readBytes(decodedData, SYMBOL_OFFSET, SYMBOL_SIZE)).trim()).uri(new String(ByteUtils.readBytes(decodedData, URI_OFFSET, URI_SIZE)).trim()).build();
//                // 将新的二进制数组转换为字符串
                    String metaData = JSON.toJSONString(metadata);
                    log.info("burn 卡片解码元数据：" + metaData);
                    if (metadata.getSymbol().contains(nftCardSymbol)) {
                        log.info("移除队列中的当前burn任务：" + JSON.toJSONString(data) + " 剩余burn任务数量：" + dataGroupBurnQueue.getSolanaBurnQueueDataList().size());
                        log.info("Solana Nft burn 交易验证成功：" + data.getTransactionId() + " " + JSON.toJSONString(resTransaction));
                        // 获取卡片id
                        String cardId = metadata.getUri().split("cardId=")[1];
                        // burn成功后，删除卡片信息发放奖励
                        playerManager.burnCard(data.getPlayerId(), cardId, data);
                        log.info("burn 卡片逻辑完成playerId=" + data.getPlayerId() + " id：" + cardId);
                    } else {
                        log.info("移除队列中的当前burn任务：" + JSON.toJSONString(data) + " 剩余burn任务数量：" + dataGroupBurnQueue.getSolanaBurnQueueDataList().size());
                        log.error("Solana Nft burn 交易验证失败：" + data.getTransactionId() + " " + JSON.toJSONString(resTransaction));
                    }
                    // 移除队列中的当前任务
                    dataGroupBurnQueue.getSolanaBurnQueueDataList().poll();
                    mongoTemplate.save(dataGroupBurnQueue);
                } else {
                    // 移除队列中的当前任务
                    dataGroupBurnQueue.getSolanaBurnQueueDataList().poll();
                    mongoTemplate.save(dataGroupBurnQueue);
                    log.info("Solana Nft burn 交易失败，data=" + JSON.toJSONString(data) + " 错误信息：" + JSON.toJSONString(resTransaction.getMeta().getErr()));
                }
            } else {
                // 这里可以不保存，反正下次启动又从0开始计数
                queueData.setFailCount(queueData.getFailCount() + 1);
                // 1w次都不成功，打印日志，放弃
                if (queueData.getFailCount() > 100) {
                    // 移除队列中的当前任务
                    dataGroupBurnQueue.getSolanaBurnQueueDataList().poll();
                    mongoTemplate.save(dataGroupBurnQueue);
                    log.error("销毁卡片异常，多次尝试找不到此销毁交易信息transactionId=" + data.getTransactionId() + " data:" + JSON.toJSONString(data));
                } else {
                    log.info("等待Solana Nft burn(" + queueData.getFailCount() + ")，交易id：" + data.getTransactionId());
                }
            }
        } catch (Exception e) {
            log.error("销毁nft异常：", e);
        }
    }

    /**
     * 测试burnNft（游戏中，用户burn自己的卡片，这里只是测试burn自己的卡片是否能跑通）
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
