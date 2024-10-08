package com.game.pay.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MoonPay交易订单
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/19 17:20
 */
@Document(collection = "moonPayOrder")
@Getter
@Setter
public class MoonPayOrder {

    /**
     * moonPay交易id
     */
    @Id
    private String id;
    /**
     * 创建时间
     */
    private String createdAt;
    /**
     * 更新时间
     */
    private String updatedAt;
    /**
     * 交易状态
     */
    private String status;
    /**
     * 交易的基础货币金额
     */
    private double baseCurrencyAmount;
    /**
     * 交易的报价货币金额
     */
    private double quoteCurrencyAmount;
    /**
     * 交易的手续费
     */
    private double feeAmount;
    /**
     * 交易的网络手续费
     */
    private double networkFeeAmount;
    /**
     * 交易涉及的钱包地址
     */
    private String walletAddress;
    /**
     * 交易的区块链交易id
     */
    private String cryptoTransactionId;
    /**
     * 客户的 ID
     */
    private String customerId;
    /**
     * 支付方式
     */
    private String paymentMethod;
    /**
     * 交易的基础货币
     */
    private String baseCurrency;
    /**
     * 交易的目标货币的信息
     */
    private String currency;
    /**
     * 订单逻辑处理状态（游戏服务器自己的状态，用于是否成功已处理）
     */
    private int payState;
    /**
     * 完整的交易信息
     */
    private String data;

}
