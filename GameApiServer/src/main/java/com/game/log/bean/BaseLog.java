package com.game.log.bean;

/**
 * 日志父类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/27 11:40
 */
public class BaseLog {

    /**
     * 日志创建时间
     */
    private long logCreateTime = System.currentTimeMillis();

    public long getLogCreateTime() {
        return logCreateTime;
    }

    public void setLogCreateTime(long logCreateTime) {
        this.logCreateTime = logCreateTime;
    }
}
