package com.game.pay.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * crypto充值交易订单
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/19 17:20
 */
@Getter
@Setter
public class CryptoPayOrder extends TsgPayOrder {

    /**
     * 充值商品名字
     */
    private String productName;
    /**
     * 为此付款收取的加密货币。
     */
    private String cryptoCurrency;
    /**
     * 为此付款收取的加密货币金额
     */
    private String cryptoAmount;

}
