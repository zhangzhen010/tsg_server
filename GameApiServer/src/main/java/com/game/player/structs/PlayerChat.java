package com.game.player.structs;

import com.game.redis.structs.RedisKey;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家聊天数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/10 14:41
 */
@Document(collection = "playerChat")
public class PlayerChat extends PlayerOther {

    /**
     * 当前世界聊天频道id
     */
    @Transient
    private transient int cid;
    /**
     * 当前语言频道id
     */
    @Transient
    private transient int lid;
    /**
     * 禁止聊天结束时间(结束时间戳)
     */
    private long chatBannedTime;
    /**
     * 上一次聊天时间（防止说话太频繁）
     */
    @Transient
    private transient long chatLastTime;
    /**
     * 离线私聊
     */
    private List<ChatData> offLineChatList = new ArrayList<>();

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER_CHAT;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public long getChatBannedTime() {
        return chatBannedTime;
    }

    public void setChatBannedTime(long chatBannedTime) {
        this.chatBannedTime = chatBannedTime;
    }

    public long getChatLastTime() {
        return chatLastTime;
    }

    public void setChatLastTime(long chatLastTime) {
        this.chatLastTime = chatLastTime;
    }

    public List<ChatData> getOffLineChatList() {
        return offLineChatList;
    }

    public void setOffLineChatList(List<ChatData> offLineChatList) {
        this.offLineChatList = offLineChatList;
    }
}
