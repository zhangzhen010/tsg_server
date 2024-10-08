package com.game.server.thread;

import com.game.command.ICommand;
import com.game.timer.TimerEvent;
import com.game.utils.TimeUtil;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 命令处理类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/5 16:25
 */
@Log4j2
public class ServerThread extends Thread {
    /**
     * 可执行命令队列
     */
    private final LinkedBlockingDeque<ICommand> command_queue = new LinkedBlockingDeque<>();
    /**
     * 事件处理类
     */
    private ServerTimer timer;
    /**
     * 线程名称
     */
    public String threadName;
    /**
     * 上一次打印信息时间
     */
    private long lastInfoTime = System.currentTimeMillis();
    /**
     * 事件处理执行的时间间隔
     */
    private final int period;
    /**
     * 是否关闭线程
     */
    private boolean stop;

    /**
     * 初始化线程
     * @param schedulerService 定时任务执行器
     * @param group            线程组
     * @param threadName       线程名
     * @param period    总的timer间隔时间毫秒
     */
    public ServerThread(ScheduledExecutorService schedulerService, ThreadGroup group, String threadName, int period) {
        super(group, threadName);
        this.threadName = threadName;
        this.period = period;
        // 执行时间间隔大于0的情况创建一个timer 事件管理线程
        if (this.getPeriod() > 0) {
            this.timer = new ServerTimer(this, schedulerService);
        }
        // 未捕获到异常的处理方式
        setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                log.error(e, e);// 打印错误信息
                if (timer != null) {
                    timer.stopTimer();// 停止timer
                }
                command_queue.clear();// 清空队列
            }
        });
    }

    @Override
    public void run() {
        try {
            // 间隔时间大于0 时间管理线程开始
            if (getPeriod() > 0 && timer != null) {
                timer.start();
            }
            stop = false;
            while (!stop) {
                ICommand command = command_queue.take();
                // 开始时间
                long start = System.currentTimeMillis();
                command.action();// 执行命令
                long end = System.currentTimeMillis();
                if (end - start > 50L) {// 单个执行大于50毫秒。提示
                    log.error(getName() + "-->" + command.getClass().getSimpleName() + "run:" + (end - start));
                }
            }
        } catch (Exception e) {
            log.error("ServerThread执行run异常：", e);
        }
    }

    /**
     * 停止线程。清理命令执行队列
     */
    public void stopThread() {
        stop = true;
        if (timer != null) {
            timer.stopTimer();
        }
        // 关闭线程不移除，让已有的执行完成
//        command_queue.clear();
    }

    /**
     * 添加执行命令
     *
     * @param command
     */
    public void addCommand(ICommand command) {
        try {
            if (stop) {
                log.error("服务器已关闭，不能接受新增command命令:" + command.getClass().getName());
                return;
            }
            int queueSize = command_queue.size();
            if ((queueSize > 30000)) {
                log.error("异常[" + threadName + "]线程待处理数量：" + queueSize + " 当前命令抛弃command=" + command.getClass().getSimpleName());
                return;
            }
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastInfoTime > TimeUtil.MIN_MILLIS) {
                if (queueSize > 10000) {
                    log.info("警告[" + threadName + "]线程待处理数量：" + queueSize);
                }
                lastInfoTime = currentTime;
            }
            command_queue.add(command);
        } catch (Exception e) {
            log.error(threadName + " 添加执行命令异常", e);
        }
    }

    /**
     * 添加事件
     *
     * @param timerEvent
     */
    public void addTimerEvent(TimerEvent timerEvent) {
        if (timerEvent != null) {
            if (stop) {
                log.error("服务器已关闭，不能接受新增timer命令:" + timerEvent.getClass().getName());
                return;
            }
            timer.addTimerEvent(timerEvent);
        }
    }

    /**
     * 移除事件
     *
     * @param event
     */
    public void removeTimerEvent(TimerEvent event) {
        if (timer != null) {
            timer.removeTimerEvent(event);
        }
    }

    public boolean isStrop() {
        return stop;
    }

    public String getThreadName() {
        return threadName;
    }

    public int getPeriod() {
        return period;
    }

}
