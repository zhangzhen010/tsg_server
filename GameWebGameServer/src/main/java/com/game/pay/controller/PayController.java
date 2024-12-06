package com.game.pay.controller;

import com.game.config.CurrentPlayer;
import com.game.controller.structs.ResponseBean;
import com.game.pay.manager.PayManager;
import com.game.player.structs.WebPlayer;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

/**
 * 充值
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 9:29
 */
@RestController
@Log4j2
@RequestMapping(value = "/tsg/pay")
public class PayController {

    private @Resource PayManager payManager;

    /**
     * 请求MoonPayUrl签名
     *
     * @param player
     * @return
     */
    @PostMapping("/moonPayUrlSign")
    public ResponseBean<Object> moonPayUrlSign(@CurrentPlayer WebPlayer player, @RequestBody String originalUrl) {
        try {
            String signUrl = payManager.moonPayUrlSign(player, originalUrl);
            return ResponseBean.success(signUrl);
        } catch (Exception e) {
            log.error("请求MoonPayUrl签名异常：", e);
            return ResponseBean.fail("request moonPay sign exception:" + e.getMessage());
        }
    }

    /**
     * 请求钱包充值创建订单
     *
     * @param player
     * @param baseCurrencyCode
     * @param baseCurrencyAmount
     * @param currencyCode
     * @return
     */
    @GetMapping("/reqWalletPayCreateOrder")
    public ResponseBean<Object> reqWalletPayCreateOrder(@CurrentPlayer WebPlayer player, @RequestParam("baseCurrencyCode") String baseCurrencyCode, @RequestParam("baseCurrencyAmount") int baseCurrencyAmount, @RequestParam("currencyCode") String currencyCode) {
        try {
            return payManager.reqWalletPayCreateOrder(player, baseCurrencyCode, baseCurrencyAmount, currencyCode);
        } catch (Exception e) {
            log.error("请求钱包充值创建订单异常：", e);
            return ResponseBean.fail("request wallet pay create order exception:" + e.getMessage());
        }
    }

    /**
     * 请求钱包充值
     *
     * @param player
     * @param gameOrderId
     * @param transactionId
     * @return
     */
    @GetMapping("/reqWalletPay")
    public ResponseBean<Object> reqWalletPay(@CurrentPlayer WebPlayer player, @RequestParam("gameOrderId") String gameOrderId, @RequestParam("transactionId") String transactionId) {
        try {
            return payManager.reqWalletPay(player, gameOrderId, transactionId);
        } catch (Exception e) {
            log.error("请求钱包充值异常：", e);
            return ResponseBean.fail("request wallet pay exception:" + e.getMessage());
        }
    }

    /**
     * 请求钱包充值
     *
     * @param player
     * @return
     */
    @GetMapping("/reqWalletPayState")
    public ResponseBean<Object> reqWalletPayState(@CurrentPlayer WebPlayer player) {
        try {
            return payManager.reqWalletPayState(player);
        } catch (Exception e) {
            log.error("请求钱包充值异常：", e);
            return ResponseBean.fail("request wallet pay exception:" + e.getMessage());
        }
    }

}
