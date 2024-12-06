package com.game.datagroup.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * Solana 后台 burn 队列数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/19 10:13
 */
@Getter
@Setter
public class SolanaBgBurnQueueData {

    /**
     * 卡片模板id
     */
    private String cardTemplateId = "";
    /**
     * burn卡片数量
     */
    private int burnTotal;
    /**
     * burn NFT 剩余数量
     */
    private int burnRemain = 0;
    /**
     * 当前burn卡片唯一id
     */
    private String gachaCardId = "";
    /**
     * 查询交易失败次数
     */
    private int failCount = 0;
    /**
     * 重试次数
     */
    private int retryCount = 0;

}
