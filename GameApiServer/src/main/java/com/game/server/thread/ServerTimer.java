package com.game.server.thread;

import com.game.timer.TimerEvent;
import com.game.utils.TimeUtil;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Log4j2
public class ServerTimer {

    /**
     * 所有需要处理的事件
     */
    private final ArrayList<TimerEvent> timerList = new ArrayList<>();
    /**
     * 执行命令线程
     */
    private final ServerThread thread;
    /**
     * 定时任务调度器
     */
    private final ScheduledExecutorService schedulerService;
    /**
     * 是否关闭timer
     */
    private boolean stop;

    public ServerTimer(ServerThread thread, ScheduledExecutorService schedulerService) {
        this.thread = thread;
        this.schedulerService = schedulerService;
    }

    /**
     * 开始监控所有的事件
     */
    public void start() {
        // 立即执行任务。每个任务间隔heart毫秒
        schedulerService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                synchronized (timerList) {
                    if (!stop) {
                        for (int i = 0; i < timerList.size(); i++) {
                            TimerEvent event = timerList.get(i);
                            if (event.remain() <= 0L) {
                                if (event.getLoop() > 0) {
                                    event.setLoop(event.getLoop() - 1);
                                    // 达到执行次数。移除这个事件
                                    if (event.getLoop() == 0) {
                                        timerList.remove(i);
                                        i--;
                                    }
                                } else {
                                    event.setLoop(event.getLoop());
                                }
                                // 添加事件到执行队列
                                thread.addCommand(event);
                            }
                        }
                    }
                }
            }
        }, 0L, thread.getPeriod(), TimeUnit.MILLISECONDS);
    }

    /**
     * 终止此计时器，丢弃所有当前已安排的任务。这不会干扰当前正在执行的任务（如果存在）。
     */
    public void stopTimer() {
        synchronized (this.timerList) {
            try {
                this.stop = true;
                if (!schedulerService.awaitTermination(TimeUtil.THREE_MILLIS, TimeUnit.MILLISECONDS)) {
                    log.error("定时器服务未能在指定时间内优雅关闭，可能有任务未完成！");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("停止定时器时发生中断异常:", e);
                throw new RuntimeException("停止定时器时发生中断异常", e);
            } catch (Exception e) {
                log.error("停止定时器时发生异常:", e);
                throw new RuntimeException("停止定时器时发生异常", e);
            } finally {
                schedulerService.shutdown();
            }
        }
    }

    /**
     * 添加timer处理事件
     *
     * @param event
     */
    public void addTimerEvent(TimerEvent event) {
        try {
            if (stop) {
                log.error("当前timerThread已关闭，不接受新timer！");
                return;
            }
            synchronized (this.timerList) {
                this.timerList.add(event);
            }
        } catch (Exception e) {
            log.error("添加timer处理事件异常：", e);
        }
    }

    /**
     * 移除计时器事件
     *
     * @param event
     */
    public void removeTimerEvent(TimerEvent event) {
        synchronized (this.timerList) {
            this.timerList.remove(event);
        }
    }
}
