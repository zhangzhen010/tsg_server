package com.game.server.timer;

import com.game.timer.TimerEvent;
import com.game.utils.TimeUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * 服务器状态信息timer
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/6/12 11:07
 */
@Component
@Log4j2
public class ServerInfoTimer extends TimerEvent {


    public ServerInfoTimer() {
        super(-1, TimeUtil.MIN_MILLIS);
    }

    @Override
    public void action() {
        try {
            // 内存信息
            long totalMemory = Runtime.getRuntime().totalMemory();
            long memory = totalMemory - Runtime.getRuntime().freeMemory();
            log.info("服务器内存信息:" + " (memory:" + memory + "/" + totalMemory + ")");
        } catch (Exception e) {
            log.error("服务器状态信息timer异常：", e);
        }
    }

}
