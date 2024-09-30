package com.game.server;

import com.game.grpc.manager.GrpcManager;
import com.game.message.Handler;
import com.game.message.MessagePool;
import com.game.message.MessageProtoPool;
import com.game.netty.NettyInServer;
import com.game.redis.manager.RedisManager;
import com.game.redis.structs.RedisKey;
import com.game.server.structs.CtxAttType;
import com.game.server.thread.ServerThread;
import com.game.server.timer.ServerCheckHeartBeatTimer;
import com.game.thread.manager.ThreadManager;
import com.game.utils.TimeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * 中心服务器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/6/12 18:09
 */
@Component
@Order(1)
@Log4j2
public class CenterServer extends NettyInServer implements CommandLineRunner {

    private @Resource GrpcManager grpcManager;
    private @Resource ThreadManager threadManager;
    private @Resource ServerCheckHeartBeatTimer serverCheckHeartBeatTimer;
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
        CenterServer.serverId = serverId;
        CenterServer.serverIdString = Integer.toString(serverId);
    }

    /**
     * 默认字符集名字
     */
    public static String DEFAULT_CHARSET = Charset.defaultCharset().name();
    // 调度器
    private Scheduler scheduler = null;

    /**
     * 中心服初始化
     */
    @PostConstruct
    public void init() {
        try {
            // quartz定时调度工具，调度工厂
            SchedulerFactory schedulerFactory = new StdSchedulerFactory("quartz.properties");
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            log.error("中心服初始化异常：", e);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if (!DEFAULT_CHARSET.equals("UTF-8")) {
                log.error("Charset err：" + DEFAULT_CHARSET);
                return;
            }
            log.info("Default Charset：" + DEFAULT_CHARSET);
            // 启动线程管理
            ServerThread mainThread = threadManager.getMainThread();
            mainThread.addTimerEvent(serverCheckHeartBeatTimer);
            // 启动grpc
            grpcManager.start();
            // 启动通信（使用grpc通信，关闭内网通信）
//            super.run();
            log.info("中心服务器启动成功8");
        } catch (Exception e) {
            log.error("中心服务器初始化异常", e);
        }
    }

    /**
     * 关服保存数据
     */
    private void saveAll() {
        try {
            redisManager.stringSet(redisManager.getCenterJedis(), RedisKey.SERVER_CLOSE_TIME, null, serverIdString, Long.valueOf(System.currentTimeMillis()).toString().getBytes(), TimeUtil.THIRTY_SEC);
            log.info("关服保存所有数据成功！");
        } catch (Exception e) {
            log.error("关服保存数据", e);
        }
    }

    @Override
    public void doCommand(ChannelHandlerContext ctx, ByteBuf buf) {
        try {
            long currentTime = System.currentTimeMillis();
            // 获取消息id(这里采用大端读取数据)
            int id = buf.readInt();
            // 解析消息
            byte[] data = new byte[buf.readableBytes()];
            buf.readBytes(data);
            Method m = MessageProtoPool.getInstance().createProto(id);
            if (m == null) {
                log.error("收到了MessageProtoPool不存在的消息msgId=" + id);
                return;
            }
            Object proto = null;
            try {
                proto = m.invoke(null, data);// 静态方法参数可以为null
            } catch (Exception e) {
                log.error("接收消息无法正确反射成对应的Proto对象msgId:" + id, e);
            }
            // 生成处理函数
            Handler handler = MessagePool.getInstance().createHandler(id);
            handler.setCtx(ctx);
            handler.setProto(proto);
            handler.setCreateTime(currentTime);
            if (handler.getQueue() != null) {
                if ("center".equals(handler.getQueue())) {
                    threadManager.getMainThread().addCommand(handler);
                } else {
                    threadManager.getMainThread().addCommand(handler);
                }
            } else {
                handler.action();
            }
        } catch (Exception e) {
            log.error("解析协议，处理协议异常：", e);
        }
    }

    @Override
    public void ctxCreate(ChannelHandlerContext ctx) {
        ctx.channel().attr(CtxAttType.CTX_CREATE_TIME).set(System.currentTimeMillis());
    }

    @Override
    public void ctxOpened(ChannelHandlerContext ctx) {
        ctx.channel().attr(CtxAttType.CTX_CREATE_TIME).set(System.currentTimeMillis());
    }

    @Override
    public void ctxClosed(ChannelHandlerContext ctx) {

    }

    @Override
    public void ctxCancel(ChannelHandlerContext ctx) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable paramThrowable) {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {

    }

    @Override
    public void stop() {
        try {
            saveAll();
            log.info("中心服务器关闭成功");
            // 关闭grpc通道
            grpcManager.stop();
            if (scheduler != null) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            log.error("中心服务器关闭异常：", e);
        }
    }

}
