package com.game.pay.controller;

import com.alibaba.fastjson2.JSONObject;
import com.game.pay.timer.MoonPayBackTimer;
import com.game.thread.manager.ThreadManager;
import com.game.utils.CodeUtils;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 充值
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 9:29
 */
@RestController
@Log4j2
@RequestMapping(value = "/tsgpay/pay")
public class PayController {

    // moonPay参数
    private @Value("${server.moonPay.url}") String moonPayUrl;
    private @Value("${server.moonPay.apiKey}") String moonPayApiKey;
    private @Value("${server.moonPay.secret}") String moonPaySecret;
    private @Value("${server.moonPay.webhook}") String moonPayWebhook;

    private @Resource ThreadManager threadManager;

    /**
     * 解析签名头部
     *
     * @param signatureHeader 签名头部字符串
     * @return 签名元素映射
     */
    private Map<String, String> parseSignatureHeader(String signatureHeader) {
        Map<String, String> elements = new HashMap<>();
        String[] parts = signatureHeader.split(",");
        for (String part : parts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2) {
                elements.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return elements;
    }

    /**
     * 验证 MoonPay Webhook 签名
     *
     * @param signatureHeader
     * @param body
     * @return
     */
    @PostMapping("/moonPayVerifyWebhook")
    public JSONObject verifyWebhook(@RequestHeader("moonpay-signature-v2") String signatureHeader, @RequestBody String body) {
        JSONObject jsonObject = new JSONObject();
        try {
            log.info("收到moonPay回调数据 signatureHeader=" + signatureHeader + ", body=" + body);
            if (signatureHeader == null || signatureHeader.isEmpty()) {
                jsonObject.put("code", HttpStatus.SC_BAD_REQUEST);
                jsonObject.put("msg", "Missing moonpay-signature-v2 header");
                return jsonObject;
            }
            Map<String, String> signatureElements = parseSignatureHeader(signatureHeader);
            String timestamp = signatureElements.get("t");
            String signature = signatureElements.get("s");
            // 构造签名字符串
            String content = timestamp + "." + body;
            String expectedSignature = CodeUtils.createHmacSha256(moonPayWebhook, content);
            // 验证签名
            if (!expectedSignature.equals(signature)) {
                log.error("MoonPay 回调Create签名验证失败：源字符串[" + content + "]，moonPay签名 " + signature + " 服务器签名：" + expectedSignature);
                jsonObject.put("code", HttpStatus.SC_BAD_REQUEST);
                jsonObject.put("msg", "Invalid signature");
                return jsonObject;
            }
            log.info("MoonPay 回调签名验证成功：源字符串[" + content + "]，moonPay签名 " + signature + " 服务器签名：" + expectedSignature);
            // 处理回调数据
            JSONObject data = JSONObject.parseObject(body);
            MoonPayBackTimer moonPayBackTimer = new MoonPayBackTimer(data);
            threadManager.getMainThread().addTimerEvent(moonPayBackTimer);
            // 签名验证成功
            jsonObject.put("code", HttpStatus.SC_OK);
            jsonObject.put("msg", "Signature verified successfully");
            return jsonObject;
        } catch (Exception e) {
            log.error("验证 MoonPay Webhook 签名异常：", e);
            jsonObject.put("code", HttpStatus.SC_BAD_REQUEST);
            jsonObject.put("msg", "Verification failed: " + e.getMessage());
            return jsonObject;
        }
    }

}
