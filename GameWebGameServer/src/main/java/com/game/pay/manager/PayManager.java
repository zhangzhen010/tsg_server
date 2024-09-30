package com.game.pay.manager;

import com.game.player.structs.WebPlayer;
import com.game.utils.CodeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 充值管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/4 9:22
 */
@Component
@Log4j2
public class PayManager {

    // moonPay参数
    private @Value("${server.moonPay.url}") String moonPayUrl;
    private @Value("${server.moonPay.apiKey}") String moonPayApiKey;
    private @Value("${server.moonPay.secret}") String moonPaySecret;
    private @Value("${server.moonPay.webhook}") String moonPayWebhook;

    /**
     * moonPay获取加密后的url
     *
     * @param player
     * @param originalUrl
     * @return
     */
    public String moonPayUrlSign(WebPlayer player, String originalUrl) {
        try {
            String signature = CodeUtils.createHmacSha256(originalUrl, moonPaySecret);
            String encodeString = URLEncoder.encode(signature, StandardCharsets.UTF_8);
            String urlWithSignature = originalUrl + "&signature=" + encodeString;
            log.info("MoonPay Url签名,originalUrl=" + originalUrl + ",signature=" + signature + ",urlWithSignature=" + urlWithSignature);
            return urlWithSignature;
        } catch (Exception e) {
            log.error("moonPay获取加密后的url异常", e);
            return originalUrl;
        }
    }

}
