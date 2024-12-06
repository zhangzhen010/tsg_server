package com.game.server;

import com.game.grpc.manager.GrpcManager;
import com.game.grpc.service.Server2ServerGmService;
import com.game.player.manager.PlayerManager;
import com.game.redis.manager.RedisManager;
import com.game.redis.structs.RedisKey;
import com.game.server.task.EverydayTask;
import com.game.server.thread.ServerThread;
import com.game.solana.timer.SolanaNftBurnTimer;
import com.game.solana.timer.SolanaNftTransferSolTimer;
import com.game.solana.timer.SolanaNftTransferTimer;
import com.game.thread.manager.ThreadManager;
import com.game.utils.TimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

/**
 * webserver 其中@ResponseBody，一般是使用在单独的方法上的，需要哪个方法返回json数据格式，就在哪个方法上使用，具有针对性。
 * 其中@RestController，一般是使用在类上的，它表示的意思其实就是结合了@Controller和@ResponseBody两个注解，
 *
 * @author zhangzhen
 * @time 2021年7月13日
 */
@RestController
@Log4j2
@Order(1)
public class WebServer extends Server implements CommandLineRunner {

    /**
     * 获取配置属性注入静态变量中
     */
    public static int serverId;
    /**
     * 获取配置属性注入静态变量中
     */
    public static String serverIdString;

    private @Resource GrpcManager grpcManager;
    private @Resource PlayerManager playerManager;
    private @Resource RedisManager redisManager;
    private @Resource ThreadManager threadManager;

    private @Resource SolanaNftTransferTimer solanaNftTransferNftTimer;
    private @Resource SolanaNftBurnTimer solanaNftBurnTimer;
    private @Resource SolanaNftTransferSolTimer solanaNftTransferSolTimer;

    private @Resource Server2ServerGmService server2ServerGmService;

    @Value("${server.serverId}")
    public void setServerId(int serverId) {
        WebServer.serverId = serverId;
        WebServer.serverIdString = Integer.toString(serverId);
    }

    /**
     * 默认字符集名字
     */
    public String DEFAULT_CHARSET = Charset.defaultCharset().name();
    /**
     * 调度工厂
     */
    private SchedulerFactory schedulerFactory = null;
    /**
     * 调度器
     */
    private Scheduler scheduler = null;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void run(String... args) {
        try {
            if (!DEFAULT_CHARSET.equals("UTF-8")) {
                log.error("Charset err：" + DEFAULT_CHARSET);
                return;
            }
            // 初始化
            init();
            // 时间定时器初始化
            schedulerFactory = new StdSchedulerFactory(this.getClass().getResource("/").getPath() + "quartz.properties");
            scheduler = schedulerFactory.getScheduler();
            // 创建Trigger 每日零点执行
            JobDetail jobDetail0 = JobBuilder.newJob(EverydayTask.class).withIdentity("EverydayTask", "group").build();
            CronScheduleBuilder builder0 = CronScheduleBuilder.cronSchedule("1 0 0 * * ?");
            Trigger trigger0 = TriggerBuilder.newTrigger().startNow().withSchedule(builder0).build();
            scheduler.scheduleJob(jobDetail0, trigger0);
            scheduler.start();
            // 启动线程管理
            ServerThread solanaNftThread = threadManager.addThread(threadManager.getSolanaNftThreadName());
            solanaNftThread.addTimerEvent(solanaNftTransferNftTimer);
            solanaNftThread.addTimerEvent(solanaNftBurnTimer);
            solanaNftThread.addTimerEvent(solanaNftTransferSolTimer);
            solanaNftThread.start();
            // 启动grpc（使用grpc通信，关闭内网通信）
            grpcManager.addService(server2ServerGmService);
            grpcManager.start();
            // 启动通信
            super.run();
            // 启动成功
            log.info("webGame服务器启动成功8");
        } catch (Exception e) {
            log.error("启动web服务器错误", e);
        }
    }

    /**
     * 初始化后台服务器
     */
    private void init() {
        try {

        } catch (Exception e) {
            log.error("初始化后台服务器", e);
        }
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() {
        return "test....";
    }

    @Override
    public void stop() {
        saveAll();
        log.info("WebGame服务器关闭成功");
        redisManager.stringSet(redisManager.getCenterJedis(), RedisKey.SERVER_CLOSE_TIME, null, serverIdString, Long.valueOf(System.currentTimeMillis()).toString().getBytes(), TimeUtil.THIRTY_SEC);
        // 关闭grpc通道
        grpcManager.stop();
    }

    /**
     * 关服保存数据
     */
    public void saveAll() {
        try {
            // 保存角色数据
            playerManager.exitServerSaveAllPlayer();
            log.info("关服保存所有数据成功！");
        } catch (Exception e) {
            log.error("关服保存数据", e);
        }
    }

}
