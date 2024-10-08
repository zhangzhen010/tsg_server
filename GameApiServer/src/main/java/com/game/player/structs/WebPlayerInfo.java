package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台操作展示玩家信息
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/22 16:09
 */
@Getter
@Setter
public class WebPlayerInfo {

    /**
     * 玩家唯一id
     */
    private long playerId;
    /**
     * 玩家名
     */
    private String playerName = "";
    /**
     * 用户名
     */
    private String userName = "";
    /**
     * 玩家等级
     */
    private int playerLv;
    /**
     * 玩家创建时间
     */
    private long createTime;
    /**
     * 封号截至时间
     */
    private long bannedTime;
    /**
     * 总充值金额(单位：分)
     */
    private long sumMoney;
    /**
     * 主关卡配置id
     */
    private int stageId;
    /**
     * 当前主关卡名
     */
    private String stageName;
}
