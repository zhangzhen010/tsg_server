package com.game.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * Jwt工具
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 14:15
 */
@Log4j2
public class JwtUtil {

    public static final long EXPIREPWD = 1000 * 60 * 5;
    /**
     * 加密算法
     */
    private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
    /**
     * 秘钥实例
     */
    public static final SecretKey KEY = Keys.hmacShaKeyFor(GameUtil.JWT_SECRET.getBytes());
    /**
     * jwt签发者
     */
    private final static String JWT_ISS = "zz";
    /**
     * jwt主题
     */
    private final static String SUBJECT = "Peripherals";

    /**
     * 根据用户id和用户名生成token
     *
     * @param userId 玩家id
     * @param userName 账号名称
     * @return JWT规则生成的token
     */
    public static String getJwtToken(Long userId, String userName) {
        return Jwts.builder().header().add("typ", "JWT").add("alg", "HS256").and().claim("userId", userId).claim("userName", userName).id(UUID.randomUUID().toString()).expiration(Date.from(Instant.now().plusSeconds(TimeUtil.DAY_SEC))).issuedAt(new Date()).subject("Orange").issuer("orange").signWith(KEY, ALGORITHM).compact();
    }

    /**
     * 解析token
     *
     * @param token token
     * @return Jws<Claims>
     */
    public static Jws<Claims> parseClaim(String token) {
        try {
            return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token);
        } catch (Exception e) {
            log.error("解析token异常：", e);
            return null;
        }
    }

    public static JwsHeader parseHeader(String token) {
        return parseClaim(token).getHeader();
    }

    /**
     * 获取token绑定数据
     *
     * @param token
     * @param key
     * @return
     */
    public static Object getTokenDataByKey(String token, String key) {
        try {
            return parseClaim(token).getPayload().get(key);
        } catch (Exception e) {
            log.error("获取token绑定数据异常：", e);
            return null;
        }
    }

}