package com.game.timer;

/**
 * 事件父类
 *
 * @author zhangzhen
 * @time 2022年8月8日
 */
public abstract class TimerEvent implements ITimerEvent {

    /**
     * 结束时间
     */
    private long end;
    /**
     * 执行时间
     */
    private long remain;
    /**
     * 可循环执行次数 -1为永久执行
     */
    private int loop;
    /**
     * 执行间隔时间
     */
    private long delay;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 回调处理
     */
    private ITimerCallback callback;

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getRemain() {
        return remain;
    }

    public void setRemain(long remain) {
        this.remain = remain;
    }

    public int getLoop() {
        return loop;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public ITimerCallback getCallback() {
        return callback;
    }

    public void setCallback(ITimerCallback callback) {
        this.callback = callback;
    }

    public TimerEvent(long end) {
        this.end = end;
        this.loop = 1;
    }

    public TimerEvent(int loop, long delay) {
        this.loop = loop;
        this.delay = delay;
        this.end = (System.currentTimeMillis() + delay);
    }

    /**
     * 修正定时器延时，只能在投递前调用
     *
     * @param delay
     */
    public void correctDelaybeforeDispatch(long delay) {
        this.delay = delay;
        this.end = (System.currentTimeMillis() + delay);
    }

    public long remain() {
        return this.end - System.currentTimeMillis();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setLoop(int loop) {
        this.loop = loop;
        this.end = (System.currentTimeMillis() + this.delay);
    }

    @Override
    public void action() {
        if (callback != null) {
            callback.actionAfter();
        }
    }
}
