package com.game.log.bean;

import org.springframework.data.annotation.Transient;

/**
 * 玩家登出日志
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/27 17:16
 */
public class PlayerLogoutLog extends PlayerLog {

    @Transient
    private String tabName = "playerLogoutLog_";
    /**
     * 登录时间
     */
    private long loginTime;
    /**
     * 在线时长
     */
    private long onlineTime;
    /**
     * 登出类型
     */
    private String logoutType;

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public long getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(long onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getLogoutType() {
        return logoutType;
    }

    public void setLogoutType(String logoutType) {
        this.logoutType = logoutType;
    }
}
