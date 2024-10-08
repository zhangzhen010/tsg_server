package com.game.grpc.manager;

import com.game.grpc.service.Server2ServerCloseService;
import com.game.grpc.service.Server2ServerGmReloadService;
import com.game.grpc.service.Server2ServerPingService;
import com.game.redis.manager.RedisManager;
import com.game.redis.structs.RedisKey;
import io.grpc.BindableService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ServerBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Grpc管理类
 * 这里使用的javax的注解，使用jakarta.annotation的在web项目中（springboot2）无法自动注入，其他项目springboot3都支持
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/11 11:19
 */
@Component
@Log4j2
public class GrpcManager {

    private @Resource RedisManager redisManager;
    private @Value("${server.serverId}") int serverId;
    private @Value("${server.serverIp}") String serverIp;
    private @Value("${server.grpcPort}") int grpcServerPort;
    private @Resource Server2ServerPingService server2ServerPingService;
    private @Resource Server2ServerGmReloadService server2ServerGmReloadService;
    private @Resource Server2ServerCloseService server2ServerCloseService;
    /**
     * 服务ServerBuilder
     */
    private ServerBuilder<?> serverBuilder;
    /**
     * grpc连接，key=服务器唯一id value=rpc服务器连接
     */
    private final Map<Integer, ManagedChannel> grpcMap = new HashMap<>();

    /**
     * 这里使用的javax的注解，使用jakarta.annotation的在web项目中（springboot2）无法自动注入，其他项目springboot3都支持
     */
    @PostConstruct
    public void init() {
        try {
            // 绑定rpc服务端口
            serverBuilder = ServerBuilder.forPort(grpcServerPort);
            // 默认netty线程数是cpu的2倍
//            serverBuilder.executor(Executors.newFixedThreadPool(8));
            // 所有使用grpc的服务器都需要支持服务：重新加载配置表service
            serverBuilder.addService(server2ServerGmReloadService);
            serverBuilder.addService(server2ServerPingService);
            serverBuilder.addService(server2ServerCloseService);
            // 更新本服grpc链接地址（这里之前出现过一个问题，在电脑上启用了多个网络设置，导致获取的ip不对，更改适配器选项，禁用其他网络）
            redisManager.mapSet(RedisKey.SERVER_GRPC_INFO_MAP, Integer.toString(serverId), serverIp + "," + grpcServerPort);
            log.info("启动服务注册grpc连接地址：grpcServerId=[" + serverIp + "] grpcServerPort=[" + grpcServerPort + "]");
            // 读取redis的grpc连接
            Map<String, String> grpcInfoMap = redisManager.mapGetAll(RedisKey.SERVER_GRPC_INFO_MAP);
            if (grpcInfoMap != null) {
                for (Map.Entry<String, String> entry : grpcInfoMap.entrySet()) {
                    String[] splits = entry.getValue().split(",");
                    ManagedChannel channel = ManagedChannelBuilder.forAddress(splits[0], Integer.parseInt(splits[1])).usePlaintext().build();
                    grpcMap.put(Integer.parseInt(entry.getKey()), channel);
                }
            }
        } catch (Exception e) {
            log.error("启动服务器，添加grpc服务", e);
        }
    }

    /**
     * 返回所有grpc连接
     *
     * @return
     */
    public List<ManagedChannel> getAllChannelList() {
        try {
            return new ArrayList<>(grpcMap.values());
        } catch (Exception e) {
            log.error("返回所有grpc连接异常：", e);
            return Collections.emptyList();
        }
    }

    /**
     * 获取逻辑服grpc连接，包含所有服务器，连接是读取redis
     *
     * @param serverId
     * @return
     */
    public ManagedChannel getGrpcChannelByServerId(Integer serverId) {
        ManagedChannel rpcChannel = grpcMap.get(serverId);
        if (rpcChannel == null) {//  || rpcChannel.isShutdown() || rpcChannel.isTerminated()
            // 启动时会加载所有已存在的grpc连接，这里一般情况是开了新服第一次使用找不到
            log.info("首次获取服务器[" + serverId + "]rpc连接为空，进行初始化连接！");
            String grpcInfo = redisManager.mapGet(RedisKey.SERVER_GRPC_INFO_MAP, Integer.toString(serverId));
            if (grpcInfo == null) {
                return null;
            }
            String[] splits = grpcInfo.split(",");
            ManagedChannel channel = ManagedChannelBuilder.forAddress(splits[0], Integer.parseInt(splits[1])).usePlaintext().build();
            grpcMap.put(serverId, channel);
            rpcChannel = channel;
            log.info("连接逻辑服[" + serverId + "]rpc服务器成功" + "serverId=" + serverId);
        }
        return rpcChannel;
    }

    /**
     * 添加grpc服务
     *
     * @param service
     */
    public void addService(BindableService service) {
        try {
            serverBuilder.addService(service);
        } catch (Exception e) {
            log.error("添加grpc服务异常：", e);
        }
    }

    /**
     * 启动grpc服务
     */
    public void start() {
        try {
//            Server start =
            serverBuilder.build().start();
            // 这里不需要等待阻塞推出，此线程不会结束，还有后续执行逻辑
//            start.awaitTermination();
            log.info("启动rpc服务port=" + grpcServerPort);
        } catch (Exception e) {
            log.error("启动grpc服务异常：", e);
        }
    }

    /**
     * 关闭所有的gRPC通道
     */
    public void stop() {
        try {
            for (ManagedChannel channel : grpcMap.values()) {
                if (channel != null) {
                    try {
                        channel.shutdownNow().awaitTermination(3, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        log.error("关闭grpc通道时被中断", e);
                        Thread.currentThread().interrupt();
                    }
                }
            }
            log.info("所有gRPC通道已成功关闭");
        } catch (Exception e) {
            log.error("关闭所有的gRPC通道异常：", e);
        }
    }

}
