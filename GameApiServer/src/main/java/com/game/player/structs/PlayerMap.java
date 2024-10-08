package com.game.player.structs;

import com.game.redis.structs.RedisKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 玩家副本数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 18:30
 */
@Document(collection = "playerMap")
@Getter
@Setter
public class PlayerMap extends PlayerOther {

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER_MAP;
    }

    /**
     * 主关卡当前已通关配置id，初始为0
     */
    private int stageId;

}
