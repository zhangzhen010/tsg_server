package com.game.player.structs;

import com.game.redis.structs.RedisKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 * 玩家任务数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/10 10:24
 */
@Getter
@Setter
@Document(collection = "playerQuest")
public class PlayerQuest extends PlayerOther {

    /**
     * 上一次周任务刷新时间
     */
    private long weekLastResetTime;
    /**
     * 上一次月任务刷新时间
     */
    private long monthLastResetTime;
    /**
     * 任务目标数据 key=MyEnumQuestTargetType value=此类型任务采集数据
     */
    private Map<Integer, QuestTargetDataList> questTargetDataMap = new HashMap<>();
    /**
     * 当前已接取的任务数据
     */
    private List<Quest> questList = new ArrayList<>();
    /**
     * 当前已接取任务IdSet
     */
    @Transient
    private transient Set<Integer> questSet = new HashSet<>();
    /**
     * 任务已领取记录Set(任务配置id)
     */
    private Set<Integer> getIdSet = new HashSet<>();

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER_QUEST;
    }

    public long getWeekLastResetTime() {
        return weekLastResetTime;
    }

    public void setWeekLastResetTime(long weekLastResetTime) {
        this.weekLastResetTime = weekLastResetTime;
    }

    public long getMonthLastResetTime() {
        return monthLastResetTime;
    }

    public void setMonthLastResetTime(long monthLastResetTime) {
        this.monthLastResetTime = monthLastResetTime;
    }

    public Map<Integer, QuestTargetDataList> getQuestTargetDataMap() {
        return questTargetDataMap;
    }

    public void setQuestTargetDataMap(Map<Integer, QuestTargetDataList> questTargetDataMap) {
        this.questTargetDataMap = questTargetDataMap;
    }

    public List<Quest> getQuestList() {
        return questList;
    }

    public void setQuestList(List<Quest> questList) {
        this.questList = questList;
    }

    public Set<Integer> getQuestSet() {
        return questSet;
    }

    public void setQuestSet(Set<Integer> questSet) {
        this.questSet = questSet;
    }

    public Set<Integer> getGetIdSet() {
        return getIdSet;
    }

    public void setGetIdSet(Set<Integer> getIdSet) {
        this.getIdSet = getIdSet;
    }
}
