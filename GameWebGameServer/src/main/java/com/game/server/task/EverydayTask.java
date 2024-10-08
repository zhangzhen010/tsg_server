package com.game.server.task;

import com.game.player.manager.PlayerManager;
import com.game.rank.manager.RankManager;
import com.game.utils.SpringBootUtils;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 每日四点调度任务
 *
 * @author zhangzhen
 */
@Log4j2
public class EverydayTask implements Job {

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
            long startTime = System.currentTimeMillis();

            // 排行榜重置
            SpringBootUtils.getBean(RankManager.class).resetEveryDayRank();
            // 每日刷新
            SpringBootUtils.getBean(PlayerManager.class).resetPlayerAll();

            long endTime = System.currentTimeMillis();
            log.info("每日零点刷新执行完毕耗时:" + (endTime - startTime) + "ms");
        } catch (Exception e) {
            log.error("每日零点调度任务", e);
        }
    }

}
