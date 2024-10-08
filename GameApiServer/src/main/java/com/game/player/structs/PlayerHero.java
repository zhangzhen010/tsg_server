package com.game.player.structs;

import com.game.redis.structs.RedisKey;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 * 玩家英雄数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 18:38
 */
@Document(collection = "playerHero")
public class PlayerHero extends PlayerOther {

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER_HERO;
    }

    /**
     * 上阵的英雄list（与map相等）
     */
    private List<Hero> battledHeroList = new ArrayList<>();
    /**
     * 上阵的英雄map key=英雄唯一id value=英雄
     */
    @Transient
    private transient Map<Long, Hero> battledHeroMap = new HashMap<>();
    /**
     * 玩家拥有宠物list（与map相等）
     */
    private List<Hero> heroList = new ArrayList<>();
    /**
     * 玩家拥有英雄map key=英雄唯一id value=英雄
     */
    @Transient
    private transient HashMap<Long, Hero> heroMap = new HashMap<>();
    /**
     * 激活的羁绊id列表
     */
    private List<Integer> activeFetterList = new ArrayList<>();
    /**
     * 刷新出来的英雄
     */
    private List<Hero> refreshHeroList = new ArrayList<>();
    /**
     * 刷新出来被召唤过的英雄唯一id列表
     */
    private List<Long> refreshGotIdList = new ArrayList<>();
    /**
     * 获得过的英雄<配置id>
     */
    private Set<Integer> gainedHeroSet = new HashSet<>();
    /**
     * 当前格子数
     */
    private int gridNum;
    /**
     * 已刷新次数(判断是否该保底)
     */
    private int refreshTimes;
    /**
     * 广告刷新次数（每日重置）
     */
    private int adRefreshNum;
    /**
     * 保底选择的英雄配置id
     */
    private int maxAwardChoose;
    /**
     * 最高品质英雄刷新出的次数（在刷新出选择的保底英雄的时候重置）
     */
    private int maxAwardRefreshTimes;
    /**
     * 密码
     */
    private int password;
    /**
     * 强化解锁完成时间
     */
    private long forceUnlockTime;

    public List<Hero> getBattledHeroList() {
        return battledHeroList;
    }

    public void setBattledHeroList(List<Hero> battledHeroList) {
        this.battledHeroList = battledHeroList;
    }

    public Map<Long, Hero> getBattledHeroMap() {
        return battledHeroMap;
    }

    public void setBattledHeroMap(Map<Long, Hero> battledHeroMap) {
        this.battledHeroMap = battledHeroMap;
    }

    public List<Hero> getHeroList() {
        return heroList;
    }

    public void setHeroList(List<Hero> heroList) {
        this.heroList = heroList;
    }

    public HashMap<Long, Hero> getHeroMap() {
        return heroMap;
    }

    public void setHeroMap(HashMap<Long, Hero> heroMap) {
        this.heroMap = heroMap;
    }

    public List<Integer> getActiveFetterList() {
        return activeFetterList;
    }

    public void setActiveFetterList(List<Integer> activeFetterList) {
        this.activeFetterList = activeFetterList;
    }

    public List<Hero> getRefreshHeroList() {
        return refreshHeroList;
    }

    public void setRefreshHeroList(List<Hero> refreshHeroList) {
        this.refreshHeroList = refreshHeroList;
    }

    public List<Long> getRefreshGotIdList() {
        return refreshGotIdList;
    }

    public void setRefreshGotIdList(List<Long> refreshGotIdList) {
        this.refreshGotIdList = refreshGotIdList;
    }

    public Set<Integer> getGainedHeroSet() {
        return gainedHeroSet;
    }

    public void setGainedHeroSet(Set<Integer> gainedHeroSet) {
        this.gainedHeroSet = gainedHeroSet;
    }

    public int getGridNum() {
        return gridNum;
    }

    public void setGridNum(int gridNum) {
        this.gridNum = gridNum;
    }

    public int getRefreshTimes() {
        return refreshTimes;
    }

    public void setRefreshTimes(int refreshTimes) {
        this.refreshTimes = refreshTimes;
    }

    public int getAdRefreshNum() {
        return adRefreshNum;
    }

    public void setAdRefreshNum(int adRefreshNum) {
        this.adRefreshNum = adRefreshNum;
    }

    public int getMaxAwardChoose() {
        return maxAwardChoose;
    }

    public void setMaxAwardChoose(int maxAwardChoose) {
        this.maxAwardChoose = maxAwardChoose;
    }

    public int getMaxAwardRefreshTimes() {
        return maxAwardRefreshTimes;
    }

    public void setMaxAwardRefreshTimes(int maxAwardRefreshTimes) {
        this.maxAwardRefreshTimes = maxAwardRefreshTimes;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public long getForceUnlockTime() {
        return forceUnlockTime;
    }

    public void setForceUnlockTime(long forceUnlockTime) {
        this.forceUnlockTime = forceUnlockTime;
    }
}
