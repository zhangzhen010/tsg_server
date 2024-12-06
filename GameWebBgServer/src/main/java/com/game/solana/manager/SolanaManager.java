package com.game.solana.manager;

import com.alibaba.fastjson2.JSON;
import com.game.datagroup.structs.DataGroup;
import com.game.datagroup.structs.DataGroupType;
import com.game.datagroup.structs.SolanaMintQueueData;
import com.game.player.structs.CardAttribute;
import com.game.player.structs.GachaCard;
import com.game.player.structs.GachaCardTemplate;
import com.game.solana.program.NFTProgram;
import com.game.solana.structs.CreateParams;
import com.game.solana.structs.Metadata;
import com.game.solana.structs.MetadataArg;
import com.game.utils.ID;
import com.game.utils.StringUtil;
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
import org.p2p.solanaj.programs.SystemProgram;
import org.p2p.solanaj.programs.TokenProgram;
import org.p2p.solanaj.rpc.Cluster;
import org.p2p.solanaj.rpc.RpcClient;
import org.p2p.solanaj.rpc.types.AccountInfo;
import org.p2p.solanaj.rpc.types.ConfirmedTransaction;
import org.p2p.solanaj.utils.ByteUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
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

    private @Value("${server.solana.metaUri}") String metaUri;
    private @Resource MongoTemplate mongoTemplate;
    private @Resource NFTProgram nftProgram;

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
     * 当前mint队列数据
     */
    private DataGroup dataGroupMintQueue;
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
            // 读取collectionMint账户私钥
            dataGroupMintCollectionPrivateKeyData = mongoTemplate.findOne(Query.query(Criteria.where("id").is(DataGroupType.SOLANA_NFT_COLLECTION_MINT_KEYPAIR)), DataGroup.class);
            if (dataGroupMintCollectionPrivateKeyData == null) {
                dataGroupMintCollectionPrivateKeyData = new DataGroup();
                dataGroupMintCollectionPrivateKeyData.setId(DataGroupType.SOLANA_NFT_COLLECTION_MINT_KEYPAIR);
                mongoTemplate.insert(dataGroupMintCollectionPrivateKeyData);
                log.info("collectionMintPrivateKey为空，需要重新生成collectionMint账户！");
            }
            if (StringUtil.isEmptyOrNull(dataGroupMintCollectionPrivateKeyData.getCollectionMintPrivateKey())) {
                // 账户私钥存在的情况下，永远只需要执行一次
                mintCollection();
            }
            // 读取当前进行中的mint数据队列
            dataGroupMintQueue = mongoTemplate.findOne(Query.query(Criteria.where("id").is(DataGroupType.SOLANA_NFT_MINT_QUEUE)), DataGroup.class);
            if (dataGroupMintQueue == null) {
                dataGroupMintQueue = new DataGroup();
                dataGroupMintQueue.setId(DataGroupType.SOLANA_NFT_MINT_QUEUE);
                mongoTemplate.insert(dataGroupMintQueue);
                log.info("mintQueue为空，初始化mintQueue数据！");
            }
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
     * 根据mft卡片tokenId验证是否铸造成功
     *
     * @param gachaCard
     */
    public boolean verifyMint(GachaCard gachaCard) {
        try {
            AccountInfo mintAccountInfo = rpcClient.getApi().getAccountInfo(PublicKey.valueOf(gachaCard.getMintPublicKey()));
            if (mintAccountInfo != null) {
                AccountInfo metaAccountInfo = rpcClient.getApi().getAccountInfo(PublicKey.valueOf(gachaCard.getMetaPublicKey()));
                // 获取元数据中的uri
                byte[] decodedData = null;
                try {
                    decodedData = metaAccountInfo.getDecodedData();
                } catch (Exception e) {
                    log.error("Solana 验证铸造NFT失败：还未正确获取到NFT token账户下的元数据，稍后再试...");
                    return false;
                }
                Metadata metadata = Metadata.builder().updateAuthority(PublicKey.readPubkey(decodedData, UPDATE_AUTHORITY_OFFSET)).tokenMint(PublicKey.readPubkey(decodedData, MINT_OFFSET)).name(new String(ByteUtils.readBytes(decodedData, NAME_OFFSET, NAME_SIZE)).trim()).symbol(new String(ByteUtils.readBytes(decodedData, SYMBOL_OFFSET, SYMBOL_SIZE)).trim()).uri(new String(ByteUtils.readBytes(decodedData, URI_OFFSET, URI_SIZE)).trim()).build();
//                // 将新的二进制数组转换为字符串
                String metaData = JSON.toJSONString(metadata);
                log.info("铸造卡片解码元数据：" + metaData);
                if (metadata.getSymbol().contains(nftCardSymbol)) {
                    log.info("Solana 铸造NFT成功：" + metaData);
                    return true;
                } else {
                    log.error("Solana 铸造NFT失败：uri：" + metaData);
                    return false;
                }
            }
            log.info("Solana Mint NFT 失败，mintPublicKey：{}", gachaCard.getMintPublicKey() + " metaPublicKey:" + gachaCard.getMetaPublicKey());
            return false;
        } catch (Exception e) {
            log.error("根据mft卡片tokenId验证是否铸造成功异常：", e);
            return false;
        }
    }

    /**
     * 获取当前mint任务数据
     */
    public SolanaMintQueueData getMintTask() {
        return dataGroupMintQueue.getSolanaMintQueueData();
    }

    /**
     * 根据卡片模板数据生成铸造卡片任务
     *
     * @param gachaCardTemplate
     * @param count
     */
    public void addMintTask(GachaCardTemplate gachaCardTemplate, int count) {
        try {
            if (!dataGroupMintQueue.getSolanaMintQueueData().getGachaCardTemplateId().isEmpty() || dataGroupMintQueue.getSolanaMintQueueData().getMintRemain() > 0) {
                log.info("当前有进行中的mint任务，请等待！" + JSON.toJSONString(dataGroupMintQueue.getSolanaMintQueueData()));
                return;
            }
            SolanaMintQueueData solanaMintQueueData = dataGroupMintQueue.getSolanaMintQueueData();
            solanaMintQueueData.setGachaCardTemplateId(gachaCardTemplate.getId());
            solanaMintQueueData.setMintTotal(count);
            solanaMintQueueData.setMintRemain(count);
            // 保存数据
            mongoTemplate.save(dataGroupMintQueue);
        } catch (Exception e) {
            log.error("根据卡片模板数据生成铸造卡片任务异常：", e);
        }
    }

    /**
     * 取消mint卡片队列任务
     */
    public void mintCardCancel() {
        try {
            log.info("取消mint任务:" + JSON.toJSONString(dataGroupMintQueue.getSolanaMintQueueData()));
            // 因为当前可能正在进行mint任务，只需要把剩余mint数量改为0即可，如果当前正在获取当前交易结果，继续让他走完
            dataGroupMintQueue.getSolanaMintQueueData().setMintRemain(0);
            // 保存数据
            mongoTemplate.save(dataGroupMintQueue);
        } catch (Exception e) {
            log.error("取消mint卡片队列任务异常：", e);
        }
    }

    /**
     * 处理mint队列数据
     */
    public void mint() {
        try {
            if(dataGroupMintQueue == null){
                // 等待初始化完成
                return;
            }
            SolanaMintQueueData solanaMintQueueData = dataGroupMintQueue.getSolanaMintQueueData();
            // 如果当前没有进行中的交易
            if (StringUtil.isEmptyOrNull(solanaMintQueueData.getTransactionId())) {
                // 判断是否还有剩余的mint次数任务
                if (solanaMintQueueData.getMintRemain() > 0) {
                    GachaCardTemplate gachaCardTemplate = mongoTemplate.findOne(Query.query(Criteria.where("id").is(solanaMintQueueData.getGachaCardTemplateId())), GachaCardTemplate.class);
                    if (gachaCardTemplate == null) {
                        log.error("根据mint队列数据获取卡牌模板数据为空，id：{}", solanaMintQueueData.getGachaCardTemplateId());
                        return;
                    }
                    // 开始一个mint卡片交易
                    GachaCard gachaCard = new GachaCard();
                    gachaCard.setId(Long.toString(ID.getId()));
                    gachaCard.setGachaCardTemplateId(gachaCardTemplate.getId());
                    gachaCard.setMintPublicKey("");
                    gachaCard.setName(gachaCardTemplate.getName());
                    gachaCard.setDescription(gachaCardTemplate.getDescription());
                    gachaCard.setImage(gachaCardTemplate.getImage());
                    gachaCard.setUsd(gachaCardTemplate.getUsd());
                    gachaCard.setCost(0);
                    for (int j = 0; j < gachaCardTemplate.getAttributes().size(); j++) {
                        CardAttribute cardAttribute = gachaCardTemplate.getAttributes().get(j);
                        if (cardAttribute.getTraitType().equals("level")) {
                            gachaCard.setLevel(Integer.parseInt(cardAttribute.getValue()));
                        } else if (cardAttribute.getTraitType().equals("quality")) {
                            gachaCard.setQuality(cardAttribute.getValue());
                        }
                    }
                    gachaCard.setGachaPoolId(0);
                    gachaCard.setOwnerPlayerId(0);
                    gachaCard.setBurn(false);
                    gachaCard.setBurnCandyRatio(0);
                    gachaCard.setBurnFtRatio(0);
                    gachaCard.getAttributes().clear();
                    gachaCard.getAttributes().addAll(gachaCardTemplate.getAttributes());
                    // nft铸造
                    String transactionId = mintNft(gachaCard);
                    // 记录本次mint交易id
                    solanaMintQueueData.setTransactionId(transactionId);
                    solanaMintQueueData.setGachaCard(gachaCard);
                    // 记录本次mint交易数量
                    solanaMintQueueData.setMintCount(1);
                    // 记录本次mint检查数量为0
                    solanaMintQueueData.setMintCheckCount(0);
                    // 保存数据
                    mongoTemplate.save(dataGroupMintQueue);
                } else {
                    // 没有mint任务，空闲
                }
            } else {
                // 就处理当前交易
                ConfirmedTransaction transaction = getTransaction(solanaMintQueueData.getTransactionId());
                if (transaction != null) {
                    // 获取交易成功
                    if (transaction.getMeta().getErr() == null) {
                        // 验证是否铸造成功
                        String mintPublicKey = transaction.getMeta().getPostTokenBalances().get(0).getMint();
                        // 设置nft卡片的tokenId（在solana中，就是主要卡片的mint账户公钥）
                        solanaMintQueueData.getGachaCard().setMintPublicKey(mintPublicKey);
                        boolean success = verifyMint(solanaMintQueueData.getGachaCard());
                        if (success) {
                            // nft铸造成功后添加到数据库
                            mongoTemplate.insert(solanaMintQueueData.getGachaCard());
                            log.info("mint NFT 成功，cardInfo：{}", JSON.toJSONString(solanaMintQueueData.getGachaCard()));
                            // 任务进度+1
                            solanaMintQueueData.setMintRemain(solanaMintQueueData.getMintRemain() - solanaMintQueueData.getMintCount());
                            // 重置交易id
                            solanaMintQueueData.setTransactionId("");
                            // 重置卡片数据
                            solanaMintQueueData.setGachaCard(new GachaCard());
                            // 记录成功次数
                            solanaMintQueueData.setSuccessCount(solanaMintQueueData.getSuccessCount() + solanaMintQueueData.getMintCount());
                            // 检查任务是否全部完成
                            if (solanaMintQueueData.getMintRemain() <= 0) {
                                dataGroupMintQueue.setSolanaMintQueueData(new SolanaMintQueueData());
                                log.info("Solana mint NFT 任务全部完成！成功数量：" + solanaMintQueueData.getSuccessCount());
                            }
                            // 保存数据
                            mongoTemplate.save(dataGroupMintQueue);
                        } else {
//                            // 任务进度+1
//                            solanaMintQueueData.setMintRemain(solanaMintQueueData.getMintRemain() - solanaMintQueueData.getMintCount());
//                            // 重置交易id
//                            solanaMintQueueData.setTransactionId("");
//                            // 重置卡片数据
//                            solanaMintQueueData.setGachaCard(new GachaCard());
                            // mint失败验证失败有可能还未正确获取到元数据，重试几次（之前的做法是直接跳过，进入下一个mint任务，但是重试之后发现，可以正确获取验证结果）
                            log.error("mint NFT 验证失败，稍后再试...，tokenId：" + mintPublicKey + " cardInfo:" + JSON.toJSONString(solanaMintQueueData.getGachaCard()));
                            // 检查任务是否全部完成
//                            if (solanaMintQueueData.getMintRemain() <= 0) {
//                                dataGroupMintQueue.setSolanaMintQueueData(new SolanaMintQueueData());
//                                log.info("Solana mint NFT 任务全部完成！成功数量：" + solanaMintQueueData.getSuccessCount());
//                            }
                            // 保存数据
//                            mongoTemplate.save(dataGroupMintQueue);
                        }
                    } else {
                        // 任务进度+1
                        solanaMintQueueData.setMintRemain(solanaMintQueueData.getMintRemain() - solanaMintQueueData.getMintCount());
                        // 重置交易id
                        solanaMintQueueData.setTransactionId("");
                        // 重置卡片数据
                        solanaMintQueueData.setGachaCard(new GachaCard());
                        // mint失败就跳过，进入下一个mint任务（这个跟transfer处理逻辑不一样，失败就失败了，不管）
                        log.error("mint NFT 失败，跳过，进入下一个mint任务，错误信息：" + JSON.toJSONString(transaction.getMeta().getErr()) + " cardInfo:" + JSON.toJSONString(solanaMintQueueData.getGachaCard()));
                        // 检查任务是否全部完成
                        if (solanaMintQueueData.getMintRemain() <= 0) {
                            dataGroupMintQueue.setSolanaMintQueueData(new SolanaMintQueueData());
                            log.info("Solana mint NFT 任务全部完成！成功数量：" + solanaMintQueueData.getSuccessCount());
                        }
                        // 保存数据
                        mongoTemplate.save(dataGroupMintQueue);
                    }
                } else {
                    solanaMintQueueData.setMintCheckCount(solanaMintQueueData.getMintCheckCount() + 1);
                    // 重试次数太多，放弃此任务
                    if (solanaMintQueueData.getMintCheckCount() > 200) {
                        // 任务进度+1
                        solanaMintQueueData.setMintRemain(solanaMintQueueData.getMintRemain() - solanaMintQueueData.getMintCount());
                        // 重置交易id
                        solanaMintQueueData.setTransactionId("");
                        // 重置卡片数据
                        solanaMintQueueData.setGachaCard(new GachaCard());
                        log.error("等待Solana Mint Nft 尝试获取结果次数太多，放弃此任务，跳过，进入下一个mint任务，当前Mint剩余进度：(" + solanaMintQueueData.getMintRemain() + ")" + " 交易id：" + solanaMintQueueData.getTransactionId());
                        // 检查任务是否全部完成
                        if (solanaMintQueueData.getMintRemain() <= 0) {
                            dataGroupMintQueue.setSolanaMintQueueData(new SolanaMintQueueData());
                            log.info("Solana mint NFT 任务全部完成！成功数量：" + solanaMintQueueData.getSuccessCount());
                        }
                    } else {
                        log.info("等待Solana Mint Nft 尝试获取结果次数:(" + solanaMintQueueData.getMintCheckCount() + ") 当前Mint剩余进度：(" + solanaMintQueueData.getMintRemain() + ")" + " 交易id：" + solanaMintQueueData.getTransactionId());
                    }
                    // 保存数据
                    mongoTemplate.save(dataGroupMintQueue);
                }
            }
        } catch (Exception e) {
            log.error("处理mint队列数据异常：", e);
        }
    }

    /**
     * 合约创建集合（永远只执行一次）
     */
    public void mintCollection() {
        try {
            // 准备元数据
            MetadataArg metadataArg = new MetadataArg("TSG Collection", nftCardSymbol, "https://example.com/metadata.json", System.currentTimeMillis() / 1000);
            // 创建collectionMint PublicKey
            Account collectionAccount = new Account();
            PublicKey collectionMint = collectionAccount.getPublicKey();
            // 创建destination PublicKey
            PublicKey destination = PublicKey.findProgramAddress(List.of(userPublicKey.toByteArray(), TokenProgram.PROGRAM_ID.toByteArray(), collectionMint.toByteArray()), AssociatedTokenProgram.PROGRAM_ID).getAddress();
            // 创建cardInfo PublicKey
            PublicKey cardInfo = PublicKey.findProgramAddress(List.of("card".getBytes(StandardCharsets.UTF_8), collectionMint.toByteArray()), nftProgram.getPROGRAM_ID()).getAddress();
            // 创建metadata PublicKey(这个地址是固定的)
            PublicKey metadata = PublicKey.findProgramAddress(List.of("metadata".getBytes(StandardCharsets.UTF_8), TOKEN_METADATA_PROGRAM_ID.toByteArray(), collectionMint.toByteArray()), TOKEN_METADATA_PROGRAM_ID).getAddress();
            // 创建masterEdition PublicKey(这个地址是固定的)
            PublicKey masterEdition = PublicKey.findProgramAddress(List.of("metadata".getBytes(StandardCharsets.UTF_8), TOKEN_METADATA_PROGRAM_ID.toByteArray(), collectionMint.toByteArray(), "edition".getBytes(StandardCharsets.UTF_8)), TOKEN_METADATA_PROGRAM_ID).getAddress();
            // 创建交易
            Transaction transaction = new Transaction();
            // 设置一个高一点的，反正用不完会退给我
            transaction.addInstruction(ComputeBudgetProgram.setComputeUnitLimit(500000));
            transaction.addInstruction(ComputeBudgetProgram.setComputeUnitPrice(10000));
            TransactionInstruction transactionInstruction = nftProgram.mintCollection(userPublicKey, collectionMint, destination, cardInfo, metadata, masterEdition, SystemProgram.PROGRAM_ID, TokenProgram.PROGRAM_ID, AssociatedTokenProgram.PROGRAM_ID, TOKEN_METADATA_PROGRAM_ID, TokenProgram.SYSVAR_RENT_PUBKEY, metadataArg);
            transaction.addInstruction(transactionInstruction);
            // 测试交易
//            transaction.setRecentBlockHash(rpcClient.getApi().getLatestBlockhash());
//            transaction.sign(List.of(account, collectionAccount));
//            byte[] serializedTransaction = transaction.serialize();
//            String base64Trx = Base64.getEncoder().encodeToString(serializedTransaction);
//            SimulatedTransaction simulatedTransaction = rpcClient.getApi().simulateTransaction(base64Trx, List.of(userPublicKey,collectionMint));
//            System.out.println(simulatedTransaction);
            // 发送创建集合的交易
            String result = rpcClient.getApi().sendTransaction(transaction, List.of(account, collectionAccount), rpcClient.getApi().getLatestBlockhash().getValue().getBlockhash());
            log.info("执行mintCollection交易ID：{}", result);
            // 记录collectionMint账户私钥，用于后面创建nft
            dataGroupMintCollectionPrivateKeyData.setCollectionMintPrivateKey(Base58.encode(collectionAccount.getSecretKey()));
            mongoTemplate.save(dataGroupMintCollectionPrivateKeyData);
            log.info("初始化 mintCollection Account账户：" + dataGroupMintCollectionPrivateKeyData.getCollectionMintPrivateKey());
        } catch (Exception e) {
            log.error("mintCollection 异常：", e);
        }
    }

    /**
     * 创建nft卡片
     *
     * @param gachaCard
     * @return
     */
    public String mintNft(GachaCard gachaCard) {
        try {
            // 创建元数据
            CreateParams createParams = new CreateParams(nftCardName, "TSG", metaUri + "/tsg/publicinfo/cardInfo?cardId=" + gachaCard.getId(), System.currentTimeMillis() / 1000);
            // 创建nftMint PublicKey
            Account nftMintAccount = new Account();
            PublicKey nftMint = nftMintAccount.getPublicKey();
            // 创建destination PublicKey
            PublicKey destination = PublicKey.findProgramAddress(List.of(userPublicKey.toByteArray(), TokenProgram.PROGRAM_ID.toByteArray(), nftMint.toByteArray()), AssociatedTokenProgram.PROGRAM_ID).getAddress();
            // 创建metadata PublicKey(这个地址是固定的)
            PublicKey metadata = PublicKey.findProgramAddress(List.of("metadata".getBytes(StandardCharsets.UTF_8), TOKEN_METADATA_PROGRAM_ID.toByteArray(), nftMint.toByteArray()), TOKEN_METADATA_PROGRAM_ID).getAddress();
            // 记录存放元数据地址
            gachaCard.setMetaPublicKey(metadata.toBase58());
            // 创建masterEdition PublicKey(这个地址是固定的)
            PublicKey masterEdition = PublicKey.findProgramAddress(List.of("metadata".getBytes(StandardCharsets.UTF_8), TOKEN_METADATA_PROGRAM_ID.toByteArray(), nftMint.toByteArray(), "edition".getBytes(StandardCharsets.UTF_8)), TOKEN_METADATA_PROGRAM_ID).getAddress();
            // 获取collectionMint
            PublicKey collectionMint = new PublicKey(Account.fromBase58PrivateKey(dataGroupMintCollectionPrivateKeyData.getCollectionMintPrivateKey()).getPublicKeyBase58());
            // 获取collectionMetadata
            PublicKey collectionMetadata = PublicKey.findProgramAddress(List.of("metadata".getBytes(StandardCharsets.UTF_8), TOKEN_METADATA_PROGRAM_ID.toByteArray(), collectionMint.toByteArray()), TOKEN_METADATA_PROGRAM_ID).getAddress();
            // 获取collectionMasterEdition
            PublicKey collectionMasterEdition = PublicKey.findProgramAddress(List.of("metadata".getBytes(StandardCharsets.UTF_8), TOKEN_METADATA_PROGRAM_ID.toByteArray(), collectionMint.toByteArray(), "edition".getBytes(StandardCharsets.UTF_8)), TOKEN_METADATA_PROGRAM_ID).getAddress();
            // 创建cardInfo PublicKey
            PublicKey cardInfo = PublicKey.findProgramAddress(List.of("card".getBytes(StandardCharsets.UTF_8), collectionMint.toByteArray()), nftProgram.getPROGRAM_ID()).getAddress();
            // 创建交易
            Transaction transaction = new Transaction();
            TransactionInstruction transactionInstruction = nftProgram.mintNft(userPublicKey, nftMint, destination, metadata, masterEdition, collectionMint, collectionMetadata, collectionMasterEdition, cardInfo, SystemProgram.PROGRAM_ID, TokenProgram.PROGRAM_ID, AssociatedTokenProgram.PROGRAM_ID, TOKEN_METADATA_PROGRAM_ID, TokenProgram.SYSVAR_RENT_PUBKEY, createParams);
            transaction.addInstruction(transactionInstruction);
            // 测试交易
//            transaction.setRecentBlockHash(rpcClient.getApi().getLatestBlockhash());
//            transaction.sign(List.of(account, nftMintAccount));
//            byte[] serializedTransaction = transaction.serialize();
//            String base64Trx = Base64.getEncoder().encodeToString(serializedTransaction);
//            SimulatedTransaction simulatedTransaction = rpcClient.getApi().simulateTransaction(base64Trx, List.of(userPublicKey, nftMint));
//            System.out.println(simulatedTransaction);
//            return "";
            // 发送创建集合的交易
            String result = rpcClient.getApi().sendTransaction(transaction, List.of(account, nftMintAccount), rpcClient.getApi().getLatestBlockhash().getValue().getBlockhash());
            log.info("执行 Mint Nft Card 交易ID：{}", result);
            return result;
        } catch (Exception e) {
            log.error("Solana MintNft 异常：", e);
            return "";
        }
    }

}
