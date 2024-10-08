package com.game.log.bean;

import org.springframework.data.annotation.Transient;

/**
 * 玩家登录日志
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/27 17:13
 */
public class PlayerLoginLog extends PlayerLog {

    @Transient
    private String tabName = "playerLoginLog_";
    /**
     * 登录类型(E_LOGIN_REQ_TYPE)
     */
    private String loginType;

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
