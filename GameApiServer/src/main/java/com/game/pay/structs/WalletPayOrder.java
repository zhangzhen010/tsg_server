package com.game.pay.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 钱包充值交易订单
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/19 17:20
 */
@Getter
@Setter
public class WalletPayOrder extends TsgPayOrder {

    /**
     * 充值源配置id
     */
    private int payId;
    /**
     * 金额（单位：sol）
     */
    private double sol;
    /**
     * 发起充值转账钱包地址
     */
    private  String sourceWalletAddress;
    /**
     * 接收充值转账钱包地址
     */
    private String targetWalletAddress;
    /**
     * 转账sol数量，以10亿为单位
     */
    private long solNum;

}
