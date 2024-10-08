package com.game.player.structs;

import com.game.redis.structs.RedisKey;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcl
 * @desc "玩家翅膀数据"
 * @data 2024/7/23
 */
public class PlayerWing extends PlayerOther {
    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER_WING;
    }

    /**
     * 已解锁的翅膀列表
     */
    private List<Integer> wingIdList = new ArrayList<>();
    /**
     * 当前穿戴翅膀id
     */
    private int wearWingId;
    /**
     * 当前翅膀阶段
     */
    private int stage;
    /**
     * 当前翅膀等级
     */
    private int level;
    /**
     * 当前翅膀进度
     */
    private int progress;

    public List<Integer> getWingIdList() {
        return wingIdList;
    }

    public void setWingIdList(List<Integer> wingIdList) {
        this.wingIdList = wingIdList;
    }

    public int getWearWingId() {
        return wearWingId;
    }

    public void setWearWingId(int wearWingId) {
        this.wearWingId = wearWingId;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
