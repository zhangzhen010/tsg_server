package com.game.server;

import com.game.grpc.manager.GrpcManager;
import com.game.grpc.service.LogicServer2PayServerService;
import com.game.grpc.service.Server2ServerGmService;
import com.game.pay.timer.PayOrderDelTimer;
import com.game.redis.manager.RedisManager;
import com.game.redis.structs.RedisKey;
import com.game.server.thread.ServerThread;
import com.game.thread.manager.ThreadManager;
import com.game.utils.TimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * 充值服务器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/19 16:17
 */
@Component
@Order(1)
@Log4j2
public class PayServer extends Server implements CommandLineRunner {

    private @Resource GrpcManager grpcManager;
    private @Resource ThreadManager threadManager;

    private @Resource Server2ServerGmService server2ServerGmService;
    private @Resource LogicServer2PayServerService logicServer2PayServerService;

    private @Resource PayOrderDelTimer payOrderDelTimer;
    /**
     * 获取配置属性注入静态变量中
     */
    public static int serverId;
    /**
     * 获取配置属性注入静态变量中
     */
    public static String serverIdString;
    private @Resource RedisManager redisManager;

    @Value("${server.serverId}")
    public void setServerId(int serverId) {
        PayServer.serverId = serverId;
        PayServer.serverIdString = Integer.toString(serverId);
    }

    // 默认字符集名字
    public static String DEFAULT_CHARSET = Charset.defaultCharset().name();

    @Override
    public void run(String... args) {
        try {
            if (!DEFAULT_CHARSET.equals("UTF-8")) {
                log.error("Charset err：" + DEFAULT_CHARSET);
                return;
            }
            log.info("Default Charset：" + DEFAULT_CHARSET);
            // 启动grpc
            grpcManager.addService(server2ServerGmService);
            grpcManager.addService(logicServer2PayServerService);
            grpcManager.start();
            // 启动线程管理
            ServerThread mainThread = threadManager.getMainThread();
            mainThread.addTimerEvent(payOrderDelTimer);
            super.run();
            log.error("充值服务器启动成功8");
        } catch (Exception e) {
            log.error("充值服服务器初始化异常", e);
        }
    }

    @Override
    public void stop() {
        log.info("充值服务器关闭成功");
        redisManager.stringSet(redisManager.getCenterJedis(), RedisKey.SERVER_CLOSE_TIME, null, serverIdString, Long.valueOf(System.currentTimeMillis()).toString().getBytes(), TimeUtil.THIRTY_SEC);
        // 关闭grpc通道
        grpcManager.stop();
    }

}
