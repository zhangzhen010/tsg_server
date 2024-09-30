package com.game.solana.program;

import com.game.solana.structs.CreateParams;
import com.game.solana.structs.MetadataArg;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.p2p.solanaj.core.AccountMeta;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.core.TransactionInstruction;
import org.p2p.solanaj.programs.Program;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * NTF Program
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/9 14:21
 */
@Component
@Log4j2
@Getter
public class NFTProgram extends Program {

    /**
     * 合约程序id
     */
    public @Value("${server.solana.programId}") String programId;
    /**
     * 合约公钥
     */
    private PublicKey PROGRAM_ID;

    @PostConstruct
    public void init(){
        PROGRAM_ID = new PublicKey(programId);
    }

    // 将驼峰命名转换为snake_case命名
    private String toSnakeCase(String camelCaseString) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelCaseString.length(); i++) {
            char c = camelCaseString.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * 计算方法标识符
     *
     * @param namespace
     * @param instructionName
     * @return
     */
    private byte[] computeMethodIdentifier(String namespace, String instructionName) {
        try {
            // 构造预映射字符串
            String preimage = namespace + ":" + toSnakeCase(instructionName);
            // 使用SHA-256哈希算法
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(preimage.getBytes(StandardCharsets.UTF_8));
            // 取前8个字节作为方法标识符
            return Arrays.copyOfRange(hash, 0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to compute method identifier", e);
        }
    }

    /**
     * mintCollection
     *
     * @param user                   用户账户，通常是发起交易的人。标记为 isMut: true 表示这个账户的状态可能会在交易中改变，isSigner: true 表示这个账户需要授权交易
     * @param collectionMint         代表新创建的集合（collection）的 Mint 账户，Mint 是 Solana 上 NFT 或 Token 的发行账户。标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param destination            目标账户，通常是接收新铸造的 NFT 的账户。标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param cardInfo               信息账户，可能是存储关于集合的附加信息的账户。标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param metadata               代表 NFT 的元数据账户，存储有关 NFT 的详细信息。标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param masterEdition          如果 NFT 是版次化的（Edition），那么这个账户存储了关于版次的信息。标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param systemProgram          Solana 的系统程序账户，用于创建新的账户。标记为 isMut: false 表示这个账户不会被修改，isSigner: false 表示这个账户不需要授权交易
     * @param tokenProgram           Solana 的 Token 程序账户，用于处理 SPL-Token 相关的操作。标记为 isMut: false 表示这个账户不会被修改。
     * @param associatedTokenProgram Solana 的 Associated Token 账户程序，用于创建与用户账户相关联的 Token 账户。标记为 isMut: false 表示这个账户不会被修改。
     * @param tokenMetadataProgram   Solana 的 Token Metadata 程序账户，用于处理 NFT 的元数据。标记为 isMut: false 表示这个账户不会被修改。
     * @param rent                   租金豁免账户，用于确定账户是否需要支付租金。标记为 isMut: false 表示这个账户不会被修改。
     * @param metadataArg
     * @return
     */
    public TransactionInstruction mintCollection(PublicKey user, PublicKey collectionMint, PublicKey destination, PublicKey cardInfo, PublicKey metadata, PublicKey masterEdition, PublicKey systemProgram, PublicKey tokenProgram, PublicKey associatedTokenProgram, PublicKey tokenMetadataProgram, PublicKey rent, MetadataArg metadataArg) {
        try {
            List<AccountMeta> keys = new ArrayList<>();
            // 这里的顺序因为涉及到了isSigner:true或者isMut:false，所以需要排序，其他先不管，不然无法正确调起合约
            keys.add(new AccountMeta(user, true, true, 1));
            keys.add(new AccountMeta(collectionMint, true, true, 2));
            keys.add(new AccountMeta(destination, false, true, 3));
            keys.add(new AccountMeta(cardInfo, false, true, 4));
            keys.add(new AccountMeta(metadata, false, true, 5));
            keys.add(new AccountMeta(masterEdition, false, true, 6));
            keys.add(new AccountMeta(systemProgram, false, false));
            keys.add(new AccountMeta(tokenProgram, false, false));
            keys.add(new AccountMeta(associatedTokenProgram, false, false));
            keys.add(new AccountMeta(tokenMetadataProgram, false, false));
            keys.add(new AccountMeta(rent, false, false));
            // 计算mintCollection方法标识符
            byte[] methodIdentifier = computeMethodIdentifier("global", "MintCollection");
            // 元数据
            byte[] metadataArgBytes = metadataArg.toBytes();
            // 合并metadataArgBytes和methodIdentifier字节数组
            byte[] combinedBytes = new byte[methodIdentifier.length + metadataArgBytes.length];
            System.arraycopy(methodIdentifier, 0, combinedBytes, 0, methodIdentifier.length);
            System.arraycopy(metadataArgBytes, 0, combinedBytes, methodIdentifier.length, metadataArgBytes.length);
            // 构建交易指令
            return new TransactionInstruction(PROGRAM_ID, keys, combinedBytes);
        } catch (Exception e) {
            log.error("mintCollection异常：", e);
            return null;
        }
    }

    /**
     * mintNft
     *
     * @param user                    用户账户，通常是发起交易的人。标记为 isMut: true 表示这个账户的状态可能会在交易中改变，isSigner: true 表示这个账户需要授权交易
     * @param mint                    代表新铸造的 NFT 的 Mint 账户，Mint 是 Solana 上 NFT 或 Token 的发行账户。标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param destination             目标账户，通常是接收新铸造的 NFT 的账户。标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param metadata                代表 NFT 的元数据账户，存储有关 NFT 的详细信息。标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param masterEdition           如果 NFT 是版次化的（Edition），那么这个账户存储了关于版次的信息。标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param collectionMint          集合 Mint 账户，标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param collectionMetadata      集合元数据账户，标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param collectionMasterEdition 集合版次账户，标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param cardInfo                信息账户，可能是存储关于集合的附加信息的账户。标记为 isMut: true 表示这个账户的状态会在交易中改变。
     * @param systemProgram           Solana 的系统程序账户，用于创建新的账户。标记为 isMut: false 表示这个账户不会被修改，isSigner: false 表示这个账户不需要授权交易
     * @param tokenProgram            Solana 的 Token 程序账户，用于处理 SPL-Token 相关的操作。标记为 isMut: false 表示这个账户不会被修改。
     * @param associatedTokenProgram  Solana 的 Associated Token 账户程序，用于创建与用户账户相关联的 Token 账户。标记为 isMut: false 表示这个账户不会被修改。
     * @param tokenMetadataProgram    Solana 的 Token Metadata 程序账户，用于处理 NFT 的元数据。标记为 isMut: false 表示这个账户不会被修改。
     * @param rent                    租金豁免账户，用于确定账户是否需要支付租金。标记为 isMut: false 表示这个账户不会被修改。
     * @param createParams            创建参数
     * @return
     */
    public TransactionInstruction mintNft(PublicKey user, PublicKey mint, PublicKey destination, PublicKey metadata, PublicKey masterEdition, PublicKey collectionMint, PublicKey collectionMetadata, PublicKey collectionMasterEdition, PublicKey cardInfo, PublicKey systemProgram, PublicKey tokenProgram, PublicKey associatedTokenProgram, PublicKey tokenMetadataProgram, PublicKey rent, CreateParams createParams) {
        List<AccountMeta> keys = new ArrayList<>();
        keys.add(new AccountMeta(user, true, true, 1)); // 用户账户
        keys.add(new AccountMeta(mint, true, true, 2)); // Mint 账户
        keys.add(new AccountMeta(destination, false, true, 3)); // 目标账户
        keys.add(new AccountMeta(metadata, false, true, 4)); // 元数据账户
        keys.add(new AccountMeta(masterEdition, false, true, 5)); // 版次账户
        keys.add(new AccountMeta(collectionMint, false, true, 6)); // 集合 Mint 账户
        keys.add(new AccountMeta(collectionMetadata, false, true, 7)); // 集合元数据账户
        keys.add(new AccountMeta(collectionMasterEdition, false, true, 8)); // 集合版次账户
        keys.add(new AccountMeta(cardInfo, false, true, 9)); // 信息账户
        keys.add(new AccountMeta(systemProgram, false, false)); // 系统程序账户
        keys.add(new AccountMeta(tokenProgram, false, false)); // Token 程序账户
        keys.add(new AccountMeta(associatedTokenProgram, false, false)); // 关联 Token 账户程序
        keys.add(new AccountMeta(tokenMetadataProgram, false, false)); // Token Metadata 程序账户
        keys.add(new AccountMeta(rent, false, false)); // 租金豁免账户
        // 计算mintCollection方法标识符
        byte[] methodIdentifier = computeMethodIdentifier("global", "MintNft");
        // 元数据
        byte[] metadataArgBytes = createParams.toBytes();
        // 合并metadataArgBytes和methodIdentifier字节数组
        byte[] combinedBytes = new byte[methodIdentifier.length + metadataArgBytes.length];
        System.arraycopy(methodIdentifier, 0, combinedBytes, 0, methodIdentifier.length);
        System.arraycopy(metadataArgBytes, 0, combinedBytes, methodIdentifier.length, metadataArgBytes.length);
        // 构建交易指令
        return new TransactionInstruction(PROGRAM_ID, keys, combinedBytes);
    }

}
