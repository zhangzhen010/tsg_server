package com.game.timer;

/**
 * timer执行完成回调处理
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/4 10:36
 */
public interface ITimerCallback {

    /**
     * 在action方法之后执行
     */
    void actionAfter();

}
