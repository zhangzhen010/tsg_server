package com.game.log.bean;

import org.springframework.data.annotation.Transient;

/**
 * 玩家创建日志
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/27 17:12
 */
public class PlayerCreateLog extends PlayerLog {

    @Transient
    private String tabName = "playerCreateLog_";

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
}
