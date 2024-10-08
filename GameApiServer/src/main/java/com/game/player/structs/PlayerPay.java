package com.game.player.structs;

import com.game.redis.structs.RedisKey;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 玩家充值数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/10 14:39
 */
@Document(collection = "playerPay")
public class PlayerPay extends PlayerOther {

    /**
     * 总充值金额(单位：分)
     */
    private long sumMoney;
    /**
     * 玩家充值付费次数
     */
    private int payCount;
    /**
     * 最近一次充值时间
     */
    private long lastPayTime;
    /**
     * 月卡到期时间
     */
    private long yuekaEndTime;
    /**
     * 月卡上一次领取时间
     */
    private long yuekaAwardTime;
    /**
     * 最近充值记录
     */
    private List<PayRecord> recordList = new ArrayList<>();
    /**
     * 充值档位次数 key=payMapId value=次数
     */
    private Map<Integer, Integer> payNumMap = new HashMap<>();

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER_PAY;
    }

    public long getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(long sumMoney) {
        this.sumMoney = sumMoney;
    }

    public int getPayCount() {
        return payCount;
    }

    public void setPayCount(int payCount) {
        this.payCount = payCount;
    }

    public long getLastPayTime() {
        return lastPayTime;
    }

    public void setLastPayTime(long lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    public long getYuekaEndTime() {
        return yuekaEndTime;
    }

    public void setYuekaEndTime(long yuekaEndTime) {
        this.yuekaEndTime = yuekaEndTime;
    }

    public long getYuekaAwardTime() {
        return yuekaAwardTime;
    }

    public void setYuekaAwardTime(long yuekaAwardTime) {
        this.yuekaAwardTime = yuekaAwardTime;
    }

    public List<PayRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<PayRecord> recordList) {
        this.recordList = recordList;
    }

    public Map<Integer, Integer> getPayNumMap() {
        return payNumMap;
    }

    public void setPayNumMap(Map<Integer, Integer> payNumMap) {
        this.payNumMap = payNumMap;
    }
}
