package com.game.player.structs;

import com.game.redis.structs.RedisKey;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lcl
 * @desc "玩家幸存者"
 * @data 2024/7/3
 */
public class PlayerSurvivor extends PlayerOther {

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER_SURVIVOR;
    }

    /**
     * 幸存者列表
     */
    private List<Survivor> survivorList = new ArrayList<>();
    /**
     * 幸存者map<配置id，幸存者>
     */
    @Transient
    private transient Map<Integer, Survivor> survivorMap = new HashMap<>();
    /**
     * 幸存者组列表 3个为一组，没上的用0占位
     */
    private List<Integer> idGroupList = new ArrayList<>();
    /**
     * 共鸣组和等级
     */
    private List<Integer> resonanceList = new ArrayList<>();
    /**
     * 已刷新次数
     */
    private int refreshTimes;
    /**
     * 广告已刷新次数
     */
    private int adTimes;
    /**
     * 上阵组
     */
    private int battleGroup = 1;

    public List<Survivor> getSurvivorList() {
        return survivorList;
    }

    public void setSurvivorList(List<Survivor> survivorList) {
        this.survivorList = survivorList;
    }

    public Map<Integer, Survivor> getSurvivorMap() {
        return survivorMap;
    }

    public void setSurvivorMap(Map<Integer, Survivor> survivorMap) {
        this.survivorMap = survivorMap;
    }

    public List<Integer> getIdGroupList() {
        return idGroupList;
    }

    public void setIdGroupList(List<Integer> idGroupList) {
        this.idGroupList = idGroupList;
    }

    public int getRefreshTimes() {
        return refreshTimes;
    }

    public void setRefreshTimes(int refreshTimes) {
        this.refreshTimes = refreshTimes;
    }

    public int getAdTimes() {
        return adTimes;
    }

    public void setAdTimes(int adTimes) {
        this.adTimes = adTimes;
    }

    public List<Integer> getResonanceList() {
        return resonanceList;
    }

    public void setResonanceList(List<Integer> resonanceList) {
        this.resonanceList = resonanceList;
    }

    public int getBattleGroup() {
        return battleGroup;
    }

    public void setBattleGroup(int battleGroup) {
        this.battleGroup = battleGroup;
    }
}
