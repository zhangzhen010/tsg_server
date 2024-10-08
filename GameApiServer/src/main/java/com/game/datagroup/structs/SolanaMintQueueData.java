package com.game.datagroup.structs;

import com.game.player.structs.GachaCard;
import lombok.Getter;
import lombok.Setter;

/**
 * Solana mint 队列数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/19 10:13
 */
@Getter
@Setter
public class SolanaMintQueueData {

    /**
     * 关联卡片模板唯一id
     */
    private String gachaCardTemplateId = "";
    /**
     * mint NFT 总数量
     */
    private int mintTotal = 0;
    /**
     * mint NFT 剩余数量
     */
    private int mintRemain = 0;
    /**
     * mint NFT 成功数量
     */
    private int successCount = 0;
    /**
     * 当前进行中的交易id
     */
    private String transactionId = "";
    /**
     * 当前进行中铸造的卡片数据
     */
    private GachaCard gachaCard = new GachaCard();
    /**
     * 当前进行中的交易 mint nft数量(目前为1，以后看是否支持一个交易mint多张卡片)
     */
    private int mintCount = 0;
    /**
     * 当前进行中的交易 mint nft 验证次数
     */
    private int mintCheckCount = 0;

}
