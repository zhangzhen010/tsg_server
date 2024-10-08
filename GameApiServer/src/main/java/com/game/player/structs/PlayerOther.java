package com.game.player.structs;

import com.game.redis.structs.RedisKey;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

/**
 * 玩家其他数据父类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/11 16:17
 */
public abstract class PlayerOther {

    /**
     * 玩家id
     */
    @Id
    private Long playerId;
    /**
     * 是否保存玩家副本数据
     */
    @Transient
    private transient boolean save;
    /**
     * 保存玩家副本数据
     */
    @Transient
    private transient byte[] saveData;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public byte[] getSaveData() {
        return saveData;
    }

    public void setSaveData(byte[] saveData) {
        this.saveData = saveData;
    }

    public abstract RedisKey getRedisKey();

}
