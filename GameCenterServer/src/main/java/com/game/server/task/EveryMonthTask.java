package com.game.server.task;

import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * 每月0点刷新任务
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/2/22 10:34
 */
@Log4j2
public class EveryMonthTask implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        try {
            long startTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis();
            log.info("每月0点刷新执行完毕耗时:" + (endTime - startTime) + "ms");
        } catch (Exception e) {
            log.error("每月0点刷新异常", e);
        }

    }
}
