package com.game.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * google登录验证
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/5 16:49
 */
@Component
@Log4j2
public class GoogleAuthorUtil {

    private @Value("${server.google.clientId}") String CLIENT_ID;
    private @Value("${server.google.clientSecret}") String CLIENT_SECRET;
    private @Value("${server.google.redirectUri}") String REDIRECT_URI;

    /**
     * google登录验证
     *
     * @param code
     * @return
     */
    public JSONObject googleLoginVerify(String code) {
        try {
            String accessToken = getAccessToken(code);
            if (accessToken == null) {
                log.error("google登录验证异常：accessToken is null");
                return null;
            }
            return getUserInfo(accessToken);
        } catch (Exception e) {
            log.error("google登录验证异常：", e);
            return null;
        }
    }

    /**
     * Google登录获取accessToken
     *
     * @param code
     * @return
     * @throws IOException
     */
    private String getAccessToken(String code) throws IOException {
        try {
            String url = "https://oauth2.googleapis.com/token";
            String authHeader = Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes(StandardCharsets.UTF_8));
            RequestBody requestBody = new FormBody.Builder().add("code", code).add("client_id", CLIENT_ID).add("client_secret", CLIENT_SECRET).add("redirect_uri", REDIRECT_URI).add("grant_type", "authorization_code").build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).header("Authorization", "Basic " + authHeader).post(requestBody).build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
//            {
//                "access_token": "ya29.a0AcM612xx-qGsebodsLLVPjFiyoVK3nSH9Moi7az5FetqveZd4R2vjO4IxFJOYtn-B_azlKGqM3dLElSZFJX7ZNB1TNvDml_pI6Vc2uW7AnvjEWdhvYM6dpRUEJAMdGHE5ejMHGRrOcM2oT7IOSPPlpua9HZNqfQab_mazrxlaCgYKAacSARASFQHGX2Mi3wJKXo33OLnc6b3QXN8teA0175",
//                    "expires_in": 3599,
//                    "refresh_token": "1//0emq6aiAiqz9cCgYIARAAGA4SNwF-L9IrTzUd7Fiucs8ziOPZeGI8BIeHbWAoJ1ZMIerw32CzYa63cLZb3nrPxspMKw1Nk9QyKkA",
//                    "scope": "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile openid",
//                    "token_type": "Bearer",
//                    "id_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjVhYWZmNDdjMjFkMDZlMjY2Y2NlMzk1YjIxNDVjN2M2ZDQ3MzBlYTUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1NjY0NjI2NzE3ODctY3NrZDNwMHY0ampnYTEzMmhtNG5vOG04cWl0ZGRzOHAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1NjY0NjI2NzE3ODctY3NrZDNwMHY0ampnYTEzMmhtNG5vOG04cWl0ZGRzOHAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTcyNzAzNDU3MzU3ODk3NzM0MzkiLCJlbWFpbCI6IjkzOTc4MzAwNkBxcS5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6Ii1kamZBMkZ4VXVGbHBmYzZuVUZlbkEiLCJuYW1lIjoi55Sz5b-D6L-cIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0pSb1hLR0F6Sjh0WkJiOEV6X0NDYlZUMmdQZkZueUI3eFJXNm15aWFqOWNwelRtZz1zOTYtYyIsImdpdmVuX25hbWUiOiLlv4Pov5wiLCJmYW1pbHlfbmFtZSI6IueUsyIsImlhdCI6MTcyNzE3NDA3NSwiZXhwIjoxNzI3MTc3Njc1fQ.hfxnM7pnTNuh6rQiQPpXvwtl1SiHP_rTV4qJUcPn8prpGDkVE8IVrRct_Gb6-qhoysu9SPTlRmFmgHdTTE9MKg32vXsTKvidItNE8b3YeqNwrILrRv_vRiv5AtVMTBHjSCd3y6Ds42PYG_PwS3q5ywwrGM21KDmV8p6DLOjc1fyYbKgKYuYfE_MDo3xu11VQZ6Atrig3sgoc89gU_9JNr2OgQpaIZTCeqoy6eshkQn--8tk8-tZIxcEtCV_kFekV_wS1eEkpH-wpoQI4p9y1ixT8XqRB-rDGSu6voY8oM2Y3SZUHhi3E5sCKyg95vsEhrVDRIP2N6CnfvAxzA6tfjA"
//            }
                String responseBody = response.body().string();
                log.info("google登录验证返回：" + responseBody);
                JSONObject data = JSON.parseObject(responseBody);
                return data.getString("access_token");
            }
        } catch (Exception e) {
            log.error("Google登录获取accessToken异常：", e);
            return null;
        }
    }

    /**
     * Google登录获取用户信息
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    private JSONObject getUserInfo(String accessToken) throws IOException {
        try {
            String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
//            {
//                "sub": "117270345735789773439",
//                    "name": "申心远",
//                    "given_name": "心远",
//                    "family_name": "申",
//                    "picture": "https://lh3.googleusercontent.com/a/ACg8ocJRoXKGAzJ8tZBb8Ez_CCbVT2gPfFnyB7xRW6myiaj9cpzTmg\u003ds96-c",
//                    "email": "939783006@qq.com",
//                    "email_verified": true
//            }
                String responseBody = response.body().string();
                return JSONObject.parseObject(responseBody);
            }
        } catch (Exception e) {
            log.error("Google登录获取用户信息异常：", e);
            return null;
        }
    }

}
