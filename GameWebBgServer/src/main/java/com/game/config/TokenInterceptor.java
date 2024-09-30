package com.game.config;

import com.alibaba.fastjson2.JSONObject;
import com.game.utils.JwtUtil;
import com.game.utils.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * token验证
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/14 10:56
 */
@Component
@Log4j2
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 跨域支持
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            return true;
        }
        response.setContentType("application/json;charset=UTF-8");
        JSONObject jsonObject = new JSONObject();
        // 获取请求头中的令牌
        try {
            String token = request.getHeader("token");
            if (StringUtil.isEmptyOrNull(token)) {
                jsonObject.put("code", HttpServletResponse.SC_UNAUTHORIZED);
                jsonObject.put("message", "Token is null");
                response.getWriter().print(jsonObject);
                return false;
            }
            //验证令牌
            Jws<Claims> claimsJws = JwtUtil.parseClaim(token);
            if (claimsJws == null) {
                jsonObject.put("code", HttpServletResponse.SC_UNAUTHORIZED);
                jsonObject.put("message", "Token invalid or expired");
                response.getWriter().print(jsonObject);
                return false;
            }
            // token 验证成功
            return true;
        } catch (Exception e) {
            jsonObject.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            jsonObject.put("message", "Token validation failed");
            response.getWriter().print(jsonObject);
            return false;
        }
    }
}