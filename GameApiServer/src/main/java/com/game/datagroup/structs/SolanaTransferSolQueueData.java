package com.game.datagroup.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * Solana transfer sol 充值队列数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/10/17 23:52
 */
@Getter
@Setter
public class SolanaTransferSolQueueData {

    /**
     * 充值转账玩家id
     */
    private long playerId;
    /**
     * 游戏充值订单唯一id
     */
    private String gameOrderId;
    /**
     * 当前的transfer交易id
     */
    private String transactionId = "";
    /**
     * 查询交易失败次数
     */
    private int failCount = 0;
    /**
     * 重试次数
     */
    private int retryCount = 0;

}
