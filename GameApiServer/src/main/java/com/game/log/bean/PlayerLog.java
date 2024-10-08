package com.game.log.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 玩家日志基类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/27 17:01
 */
@Getter
@Setter
public class PlayerLog extends BaseLog {

    /**
     * 玩家唯一id
     */
    private long playerId;
    /**
     * 设备号
     */
    private String deviceId = "";// 设备号
    /**
     * SdkId
     */
    private int sdkId;
    /**
     * pfId
     */
    private int pfId;
    /**
     * 账号
     */
    private String userName = "";
    /**
     * 玩家名
     */
    private String playerName = "";
    /**
     * 玩家等级
     */
    private int playerLv;
    /**
     * 当前服务器Id
     */
    private int currentServerId;
    /**
     * 创建时服务器Id
     */
    private int createServerId;
    /**
     * 累计充值金额（分）
     */
    private long pay;
    /**
     * ip
     */
    private String ip = "";
    /**
     * 角色创建时间
     */
    private long createTime;

}
