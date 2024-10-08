package com.game.player.structs;

import com.game.redis.structs.RedisKey;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家好友数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/10 14:37
 */
@Document(collection = "playerFriend")
public class PlayerFriend extends PlayerOther {

    /**
     * 上一次好友搜索时间
     */
    private long lastTime;
    /**
     * 今日送礼玩家唯一id列表
     */
    private List<Long> sendList = new ArrayList<>();
    /**
     * 今日收礼玩家唯一id列表
     */
    private List<Long> receiveList = new ArrayList<>();
    /**
     * 今日领取礼物玩家唯一id列表（收礼和领取不使用map是为了验证次数更方便）
     */
    private List<Long> getList = new ArrayList<>();

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER_FRIEND;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public List<Long> getSendList() {
        return sendList;
    }

    public void setSendList(List<Long> sendList) {
        this.sendList = sendList;
    }

    public List<Long> getReceiveList() {
        return receiveList;
    }

    public void setReceiveList(List<Long> receiveList) {
        this.receiveList = receiveList;
    }

    public List<Long> getGetList() {
        return getList;
    }

    public void setGetList(List<Long> getList) {
        this.getList = getList;
    }
}
