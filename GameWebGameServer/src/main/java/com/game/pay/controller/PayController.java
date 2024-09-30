package com.game.pay.controller;

import com.game.config.CurrentPlayer;
import com.game.controller.structs.ResponseBean;
import com.game.pay.manager.PayManager;
import com.game.player.structs.WebPlayer;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
