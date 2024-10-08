package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

/**
 * 卡池抽卡日志
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/10 10:25
 */
@Getter
@Setter
public class GachaDrawLog {

    /**
     * 日志唯一id
     */
    private long id;
    /**
     * 卡池id
     */
    private long gachaPoolId;
    /**
     * 卡池名称
     */
    private String gachaPoolName;
    /**
     * 抽卡玩家id
     */
    private long playerId;
    /**
     * 卡片价格
     */
    private int price;
    /**
     * 卡片消耗
     */
    private int cost;
    /**
     * 抽卡玩家名称
     */
    @Transient
    private transient String playerName;
    /**
     * 抽卡玩家头像
     */
    @Transient
    private transient String playerAvatar;

}
