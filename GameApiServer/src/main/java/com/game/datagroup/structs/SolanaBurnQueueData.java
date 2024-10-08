package com.game.datagroup.structs;

import com.game.player.structs.GachaCardRefund;
import lombok.Getter;
import lombok.Setter;

/**
 * Solana burn 队列数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/19 10:13
 */
@Getter
@Setter
public class SolanaBurnQueueData {

    /**
     * 销毁卡片交易id
     */
    private GachaCardRefund data;
    /**
     * 查询交易失败次数
     */
    private int failCount = 0;

}
