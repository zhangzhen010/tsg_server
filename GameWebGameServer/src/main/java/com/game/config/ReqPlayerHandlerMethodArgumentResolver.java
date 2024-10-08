package com.game.config;


import com.game.player.manager.PlayerManager;
import com.game.player.structs.WebPlayer;
import com.game.utils.JwtUtil;
import com.game.utils.TimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * 自定义处理器方法参数解析器，用于自动填充方法参数中的当前玩家信息。
 * <p>
 * 该解析器实现了 {@link HandlerMethodArgumentResolver} 接口，用于在Spring MVC控制器方法中
 * 自动注入当前登录的玩家对象。通过检查方法参数是否带有 {@link CurrentPlayer} 注解，
 * 并从HTTP请求头部的token中解析出玩家ID，然后从MongoDB数据库中查询对应的玩家信息。
 * </p>
 *
 * @author zhangzhen
 * @version 1.0
 * @since 2024-08-13
 */
@Component
@Log4j2
public class ReqPlayerHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private @Resource PlayerManager playerManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentPlayer.class) && parameter.getParameterType().isAssignableFrom(WebPlayer.class);
    }

    /**
     * 解析方法参数以获取当前登录玩家信息。
     * <p>
     * 从HTTP请求头部中提取token，并从中解析出玩家ID，然后使用MongoDB查询玩家信息。
     * 如果token不存在或解析失败，则返回null。
     * </p>
     *
     * @param parameter 方法参数元数据
     * @param container Model and View容器
     * @param request   当前Web请求
     * @param factory   Web数据绑定工厂
     * @return 当前登录的玩家信息，如果无法获取则返回null
     */
    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) {
        try {
            // header中获取用户token
            String token = request.getHeader("token");
            if (ObjectUtils.isEmpty(token)) {
                log.error("请求方法中token不存在！");
                return null;
            }
            Object pid = JwtUtil.getTokenDataByKey(token, "playerId");
            if (pid == null) {
                log.error("请求方法中token解析失败,找不到playerId");
                return null;
            }
            // 这里装入的时候是Long类型，但是值比较小，他这里会自动变成Integer类型
            Long playerId = Long.parseLong(pid.toString());
            WebPlayer player = playerManager.getPlayer(playerId, true);
            {
                long currentTime = System.currentTimeMillis();
                // 检查此玩家每秒请求次数，每秒不能超过30个
                if (currentTime / 1000 != player.getLastReqTime() / 1000) {
                    player.setLastReqTime(currentTime);
                    player.setReqCount(0);
                }
                // 请求数+1
                player.setReqCount(player.getReqCount() + 1);
                if (player.getReqCount() > 30) {
                    log.error("请求太频繁，playerId:" + playerId + " count=" + player.getReqCount());
                    return null;
                }
                // 检查是否已被封号
                if (player.getBannedTime() > currentTime) {
                    log.error("玩家被封号，playerId:" + playerId + " 封号截至时间：" + TimeUtil.formatDate(player.getBannedTime()));
                    return null;
                }
            }
            return player;
        } catch (Exception e) {
            log.error("获取当前登录玩家异常：", e);
            return null;
        }
    }
}

