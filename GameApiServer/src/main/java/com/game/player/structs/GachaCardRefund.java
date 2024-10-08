package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 卡片退款信息
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/9 20:57
 */
@Getter
@Setter
@Document(collection = "gachaCardRefund")
public class GachaCardRefund {

    /**
     * 唯一id
     */
    @Id
    private String id;
    /**
     * 玩家唯一id
     */
    private long playerId;
    /**
     * burn交易id
     */
    private String transactionId;
    /**
     * 销毁卡片获得货币类型（GachaCardRefundType）
     */
    private int type;
    /**
     * 选择的区块链类型
     */
    private String chainType;
    /**
     * 选择的区块链货币类型
     */
    private String currencyType;
    /**
     * 返现比例（百分比，值50=50%）
     */
    private int ratio;
    /**
     * 选择获得的货币值（1candy=0.01USD）
     */
    private String value;
    /**
     * 目标钱包地址
     */
    private String targetWalletAddress;
    /**
     * 管理员是否已操作返现
     */
    private boolean refund;
    /**
     * 销毁卡片详情
     */
    private GachaCard gachaCard;
    /**
     * 创建时间
     */
    private long createTime;

}
