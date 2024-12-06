package com.game.pay.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * tsg交易订单
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/19 17:20
 */
@Document(collection = "tsgPayOrder")
@Getter
@Setter
public class TsgPayOrder {

    /**
     * 游戏订单id
     */
    @Id
    private String gameOrderId;
    /**
     * 平台订单号
     */
    private String pfOrderId = "";
    /**
     * 订单类型 wallet moonPay crypto
     */
    private String orderType = "";
    /**
     * 角色id
     */
    private long playerId;
    /**
     * 角色名称
     */
    private String playerName;
    /**
     * 订单状态
     */
    private int payState;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 扩展信息
     */
    private String extInfo = "";

}
