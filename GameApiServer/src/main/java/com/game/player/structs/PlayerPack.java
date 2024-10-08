package com.game.player.structs;

import com.game.redis.structs.RedisKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 玩家背包
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 18:37
 */
@Document(collection = "playerPack")
@Getter
@Setter
public class PlayerPack extends PlayerOther {

    /**
     * 玩家各种资源货币map key=ResourceId value=值
     */
    private Map<Integer, Long> resourceMap = new HashMap<>();
    /**
     * 背包道具（不包含需要线程安全的道具）list key=道具唯一id value=道具（与map相等）
     */
    private List<Prop> propList = new ArrayList<>();
    /**
     * 背包道具（不包含需要线程安全的道具）map key=道具唯一id value=道具
     */
    @Transient
    private transient Map<Long, Prop> PropMap = new HashMap<>();
    /**
     * 背包道具（存放需要线程安全的道具）list key=道具唯一id value=道具（与map相等）
     */
    private List<Prop> concurrentPropList = new ArrayList<>();
    /**
     * 背包道具（存放需要线程安全的道具）map key=道具唯一id value=道具
     */
    @Transient
    private transient Map<Long, Prop> concurrentPropMap = new HashMap<>();
    /**
     * 技能
     */
    private List<Skill> skillList = new ArrayList<>();
    /**
     * 背包技能map key=技能唯一id value=技能
     */
    @Transient
    private transient Map<Long, Skill> skillMap = new HashMap<>();
    /**
     * 卡片
     */
    private List<Card> cardList  = new ArrayList<>();
    /**
     * 卡片map key=卡片唯一id(这里使用mint生成的唯一id) value=卡片
     */
    @Transient
    private transient Map<Long, Card> cardMap = new HashMap<>();

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER_PACK;
    }

}
