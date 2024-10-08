package com.game.thread.manager;

import com.game.server.thread.ServerThread;
import com.game.server.timer.ServerHeartBeatTimer;
import com.game.server.timer.ServerInfoTimer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 线程管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/6/12 10:45
 */
@Component
@Log4j2
@Getter
public class ThreadManager {

    private @Resource ServerInfoTimer serverInfoTimer;
    private @Resource ServerHeartBeatTimer serverHeartBeatTimer;
    /**
     * 替换原来ServerTimer中的TimerTask，统一管理所有ServerThread中的timer
     */
    private final ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(2);
    /**
     * 初始化线程队列
     */
    private final ThreadGroup group = new ThreadGroup("group");
    /**
     * 初始化主逻辑线程
     */
    private ServerThread mainThread = null;
    /**
     * 线程map
     */
    private final Map<String, ServerThread> threadMap = new HashMap<>();
    /**
     * 逻辑服登录线程名字
     */
    private final String logicLoginThreadName = "logicLoginThread";
    /**
     * 逻辑服玩家心跳线程名字
     */
    private final String logicHeartThreadName = "logicHeartThread";
    /**
     * 逻辑服活动线程名字
     */
    private final String logicActivityThreadName = "logicActivityThread";
    /**
     * 逻辑服玩家定时事件线程名字
     */
    private final String logicPlayerEventThreadName = "logicPlayerEventThread";
    /**
     * Solana NTF线程
     */
    private final String solanaNftThreadName = "solanaNftThread";


    @PostConstruct
    private void init() {
        // 启动就执行一次心跳更新
        serverHeartBeatTimer.action();
        // 初始化主逻辑线程
        mainThread = addThread("mainThread");
        mainThread.addTimerEvent(serverInfoTimer);
        mainThread.addTimerEvent(serverHeartBeatTimer);
        // 默认启动
        mainThread.start();
    }


    /**
     * 添加线程(默认timer检查执行间隔50ms执行一次)
     *
     * @param threadName 线程名字
     */
    public ServerThread addThread(String threadName) {
        return addThread(threadName, 50);
    }

    /**
     * 添加线程
     *
     * @param threadName 线程名字
     * @param period     此线程timer间隔时间
     */
    public ServerThread addThread(String threadName, int period) {
        ServerThread serverThread = new ServerThread(schedulerService, group, threadName, period);
        threadMap.put(threadName, serverThread);
        return serverThread;
    }

    /**
     * 获取线程
     *
     * @param threadName
     * @return
     */
    public ServerThread getThread(String threadName) {
        return threadMap.get(threadName);
    }

    /**
     * ThreadManager线程关闭
     */
    public void stop() {
        try {
            for (Map.Entry<String, ServerThread> entry : threadMap.entrySet()) {
                ServerThread thread = entry.getValue();
                thread.stopThread();
            }
            log.info("所有线程关闭完成！");
        } catch (Exception e) {
            log.error("ThreadManager线程关闭异常：", e);
        }
    }

}
