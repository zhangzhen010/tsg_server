package com.game.datagroup.structs;

import com.game.player.structs.GachaCard;
import lombok.Getter;
import lombok.Setter;

/**
 * Solana transfer 队列数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/19 10:13
 */
@Getter
@Setter
public class SolanaTransferQueueData {

    /**
     * 卡片数据
     */
    private GachaCard gachaCard;
    /**
     * 目标钱包地址
     */
    private String targetWalletAddress;
    /**
     * 当前账户的token地址（如果为空，先获取）
     */
    private String sourceTokenAddress = "";
    /**
     * 当前的transfer交易id
     */
    private String transactionId = "";
    /**
     * 当前的创建目标ata账户交易id
     */
    private String transactionAtaId = "";
    /**
     * 当前的目标token地址（如果为空，就要先走获取目标token账户地址逻辑，否则走transfer交易验证逻辑）
     */
    private String targetTokenAddress = "";
    /**
     * 查询交易失败次数
     */
    private int failCount = 0;

}
