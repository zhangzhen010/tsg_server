package com.game.server.timer;

import com.game.redis.manager.RedisManager;
import com.game.redis.structs.RedisKey;
import com.game.timer.TimerEvent;
import com.game.utils.TimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 服务器健康状态心跳timer
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/6/12 17:33
 */
@Component
@Log4j2
public class ServerHeartBeatTimer extends TimerEvent {

    private @Resource RedisManager redisManager;
    /**
     * 获取配置属性注入静态变量中
     */
    public static String serverIdString;

    @Value("${server.serverId}")
    public void setServerId(int serverId) {
        serverIdString = Integer.toString(serverId);
    }

    public ServerHeartBeatTimer() {
        super(-1, TimeUtil.FIVE_MILLIS);
    }

    @Override
    public void action() {
        try {
            // 发送心跳到redis（5秒更新一次）
            redisManager.rankZadd(redisManager.getCenterJedis(), RedisKey.SERVER_HEARTBEAT_RANK, serverIdString, System.currentTimeMillis(), null, null, 0);
        } catch (Exception e) {
            log.error("服务器状态信息timer异常：", e);
        }
    }

}
