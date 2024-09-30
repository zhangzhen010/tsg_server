package com.game.server.timer;

import com.game.data.bean.B_z_server_serverlist_Bean;
import com.game.data.define.MyDefineServerType;
import com.game.data.manager.DataManager;
import com.game.redis.manager.RedisManager;
import com.game.redis.structs.RedisKey;
import com.game.timer.TimerEvent;
import com.game.utils.TimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import redis.clients.jedis.resps.Tuple;

import java.util.List;

/**
 * 检查服务器状态timer
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/6 19:21
 */
@Component
@Log4j2
public class ServerCheckHeartBeatTimer extends TimerEvent {

    private @Resource DataManager dataManager;
    private @Resource RedisManager redisManager;

    public ServerCheckHeartBeatTimer() {
        // 10秒就检查一次服务器心跳状态
        super(-1, TimeUtil.FIVE_MILLIS);
    }

    @Override
    public void action() {
        // 当前时间
        long currentTime = System.currentTimeMillis();
        // 获取所有服务器心跳时间
        List<Tuple> serverHeartList = redisManager.rankZrevrange(redisManager.getCenterJedis(), RedisKey.SERVER_HEARTBEAT_RANK, -1, null, null);
        if (serverHeartList.isEmpty()) {
            return;
        }
        for (int i = 0; i < serverHeartList.size(); i++) {
            Tuple tuple = serverHeartList.get(i);
            // 心跳大于10秒，表示此服务器有可能已经嘎了，针对此情况做处理
            if (currentTime - tuple.getScore() > TimeUtil.TEN_MILLIS) {
                log.error("服务器[" + tuple.getElement() + "]心跳超时，进行下线处理...");
                // 移除心跳排行数据，以免下次再次执行
                redisManager.rankZrem(redisManager.getCenterJedis(), RedisKey.SERVER_HEARTBEAT_RANK, tuple.getElement(), null, null);
            }
        }
    }

    /**
     * 根据服务器类型获取对应的redisKey
     *
     * @param serverId 服务器id
     * @return RedisKey
     */
    public RedisKey getRedisKeyByServerType(int serverId) {
        try {
            // 获取服务器配置
            B_z_server_serverlist_Bean serverBean = dataManager.c_z_server_serverlist_Container.getMap().get(serverId);
            // 更新在线人数到排行榜
            if (serverBean.getType() == MyDefineServerType.LOGIC.intValue()) {
                return RedisKey.LB_LOGIC_PLAYER_NUM_RANK;
            } else {
                return RedisKey.LB_ROOM_PLAYER_NUM_RANK;
            }
        } catch (Exception e) {
            log.error("根据服务器类型获取对应的redisKey异常：", e);
            return null;
        }
    }

}
