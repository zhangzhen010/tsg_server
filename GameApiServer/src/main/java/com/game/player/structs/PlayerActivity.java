package com.game.player.structs;

import com.game.redis.structs.RedisKey;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 玩家活动数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/10 14:33
 */
@Document(collection = "playerActivity")
public class PlayerActivity extends PlayerOther {

    /**
     * 配置类活动activity的版本号数据 如果玩家身上记录的此活动版本号与配置版本号不一致，进行该活动数据的版本变动 key=活动配置id
     * value=上一次记录的版本号
     * 以前玩家活动数据放在一个公共的数据存储，进行版本重置，以后activity配置表的活动（只影响玩家自身的活动）的数据可以放在玩家身上，根据玩家身上的这个进行版本重置
     */
    private Map<Integer, Integer> activityVerMap = new HashMap<>();
    /**
     * 大月卡数据
     */
    private List<PlayerActivityYueKaSuper> yueKaSuperDataList = new ArrayList<>();
    /**
     * 抽奖数据
     */
    private List<LuckyDraw> luckyDrawList = new ArrayList<>();

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER_ACTIVITY;
    }

    public Map<Integer, Integer> getActivityVerMap() {
        return activityVerMap;
    }

    public void setActivityVerMap(Map<Integer, Integer> activityVerMap) {
        this.activityVerMap = activityVerMap;
    }

    public List<PlayerActivityYueKaSuper> getYueKaSuperDataList() {
        return yueKaSuperDataList;
    }

    public void setYueKaSuperDataList(List<PlayerActivityYueKaSuper> yueKaSuperDataList) {
        this.yueKaSuperDataList = yueKaSuperDataList;
    }

    public List<LuckyDraw> getLuckyDrawList() {
        return luckyDrawList;
    }

    public void setLuckyDrawList(List<LuckyDraw> luckyDrawList) {
        this.luckyDrawList = luckyDrawList;
    }
}
