package com.game.log.bean;

import org.springframework.data.annotation.Transient;

/**
 * 玩家竞技场积分变化日志
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/2/19 16:49
 */
public class PlayerArenaScoreLog extends PlayerLog {

    @Transient
    private String tabName = "playerArenaScoreLog_";

    /**
     * 竞技场积分变化前数量
     */
    private long before;
    /**
     * 竞技场积分变化后数量
     */
    private long after;
    /**
     * 积分变化原因
     */
    private String logReasonString;

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public long getBefore() {
        return before;
    }

    public void setBefore(long before) {
        this.before = before;
    }

    public long getAfter() {
        return after;
    }

    public void setAfter(long after) {
        this.after = after;
    }

    public String getLogReasonString() {
        return logReasonString;
    }

    public void setLogReasonString(String logReasonString) {
        this.logReasonString = logReasonString;
    }
}
