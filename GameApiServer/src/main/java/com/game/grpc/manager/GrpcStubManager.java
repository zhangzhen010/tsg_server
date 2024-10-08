package com.game.grpc.manager;

import com.game.grpc.proto.*;
import com.game.redis.manager.RedisManager;
import com.game.redis.structs.RedisKey;
import com.game.server.structs.ServerId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * stub管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/6/14 15:04
 */
@Component
@Log4j2
@Getter
public class GrpcStubManager {

    private @Resource GrpcManager grpcManager;
    private @Resource RedisManager redisManager;

    /**
     * 逻辑服--->战斗服stub集合，key=战斗服id value=stub
     */
    @Getter(AccessLevel.PRIVATE)
    private final Map<Integer, LogicServer2FightServerServiceGrpc.LogicServer2FightServerServiceStub> logicServer2FightServerStubMap = new HashMap<>();

    /**
     * 获取逻辑服--->战斗服stub
     *
     * @return
     */
    public LogicServer2FightServerServiceGrpc.LogicServer2FightServerServiceStub getLogicServer2FightServerStub() {
        try {
            String serverIdString = redisManager.listRoundRobin(redisManager.getCenterJedis(), RedisKey.LB_SERVER_FIGHT_LIST, null, null);
            if (serverIdString == null) {
                log.error("请求战斗服务器失败，没有可用战斗服!");
                return null;
            }
            Integer fightServerId = Integer.parseInt(serverIdString);
            LogicServer2FightServerServiceGrpc.LogicServer2FightServerServiceStub stub = logicServer2FightServerStubMap.get(fightServerId);
            if (stub == null) {
                // 首次初始化
                stub = LogicServer2FightServerServiceGrpc.newStub(grpcManager.getGrpcChannelByServerId(fightServerId));
                logicServer2FightServerStubMap.put(fightServerId, stub);
            }
            return stub;
        } catch (Exception e) {
            log.error("获取逻辑服--->战斗服stub异常：", e);
            return null;
        }
    }

    /**
     * 逻辑服--->登录服stub集合，key=登录服id value=stub
     */
    @Getter(AccessLevel.PRIVATE)
    private final Map<Integer, LogicServer2LoginServerServiceGrpc.LogicServer2LoginServerServiceStub> logicServer2LoginServerStubMap = new HashMap<>();

    /**
     * 获取逻辑服--->登录服stub
     *
     * @return
     */
    public LogicServer2LoginServerServiceGrpc.LogicServer2LoginServerServiceStub getLogicServer2LoginServerStub() {
        try {
            String serverIdString = redisManager.listRoundRobin(redisManager.getCenterJedis(), RedisKey.LB_SERVER_LOGIN_LIST, null, null);
            if (serverIdString == null) {
                log.error("请求登录服务器失败，没有可用登录服!");
                return null;
            }
            Integer loginServerId = Integer.parseInt(serverIdString);
            LogicServer2LoginServerServiceGrpc.LogicServer2LoginServerServiceStub stub = logicServer2LoginServerStubMap.get(loginServerId);
            if (stub == null) {
                // 首次初始化
                stub = LogicServer2LoginServerServiceGrpc.newStub(grpcManager.getGrpcChannelByServerId(loginServerId));
                logicServer2LoginServerStubMap.put(loginServerId, stub);
            }
            return stub;
        } catch (Exception e) {
            log.error("获取逻辑服--->登录服stub异常：", e);
            return null;
        }
    }

    /**
     * 逻辑服--->竞技服stub集合，key=竞技服id value=stub
     */
    @Getter(AccessLevel.PRIVATE)
    private Map<Integer, LogicServer2ArenaServerServiceGrpc.LogicServer2ArenaServerServiceStub> logicServer2ArenaServerStubMap = new HashMap<>();

    /**
     * 获取逻辑服--->竞技服stub
     *
     * @return
     */
    public LogicServer2ArenaServerServiceGrpc.LogicServer2ArenaServerServiceStub getLogicServer2ArenaServerStub() {
        try {
            String serverIdString = redisManager.listRoundRobin(redisManager.getCenterJedis(), RedisKey.LB_SERVER_ARENA_LIST, null, null);
            if (serverIdString == null) {
                log.error("请求竞技场服务器失败，没有可用竞技场服!");
                return null;
            }
            Integer arenaServerId = Integer.parseInt(serverIdString);
            LogicServer2ArenaServerServiceGrpc.LogicServer2ArenaServerServiceStub stub = logicServer2ArenaServerStubMap.get(arenaServerId);
            if (stub == null) {
                // 首次初始化
                stub = LogicServer2ArenaServerServiceGrpc.newStub(grpcManager.getGrpcChannelByServerId(arenaServerId));
                logicServer2ArenaServerStubMap.put(arenaServerId, stub);
            }
            return stub;
        } catch (Exception e) {
            log.error("逻辑服--->竞技服stub异常：", e);
            return null;
        }
    }

    /**
     * 充值服--->逻辑服stub集合，key=逻辑服务器id value=stub
     */
    @Getter(AccessLevel.PRIVATE)
    private Map<Integer, PayServer2LogicServerServiceGrpc.PayServer2LogicServerServiceStub> payServer2LogicServerStubMap = new HashMap<>();

    /**
     * 获取充值服--->逻辑服stub
     *
     * @param logicServerId 逻辑服服务器id
     * @return
     */
    public PayServer2LogicServerServiceGrpc.PayServer2LogicServerServiceStub getPayServer2LogicServerStub(Integer logicServerId) {
        try {
            PayServer2LogicServerServiceGrpc.PayServer2LogicServerServiceStub stub = payServer2LogicServerStubMap.get(logicServerId);
            if (stub == null) {
                // 首次初始化
                stub = PayServer2LogicServerServiceGrpc.newStub(grpcManager.getGrpcChannelByServerId(logicServerId));
                payServer2LogicServerStubMap.put(logicServerId, stub);
            }
            return stub;
        } catch (Exception e) {
            log.error("获取充值服--->逻辑服stub异常：", e);
            return null;
        }
    }

    /**
     * 逻辑服--->充值服stub集合，key=充值服务器id value=stub
     */
    @Getter(AccessLevel.PRIVATE)
    private Map<Integer, LogicServer2PayServerServiceGrpc.LogicServer2PayServerServiceStub> logicServer2PayServerStubMap = new HashMap<>();

    /**
     * 获取逻辑服--->充值服stub
     *
     * @return
     */
    public LogicServer2PayServerServiceGrpc.LogicServer2PayServerServiceStub getLogicServer2PayServerStub() {
        try {
//            String serverIdString = redisManager.listRoundRobin(redisManager.getCenterJedis(), RedisKey.LB_SERVER_LOGIC2PAY_LIST, null, null);
//            if (serverIdString == null) {
//                log.error("请求竞技场服务器失败，没有可用竞技场服!");
//                return null;
//            }
            // TODO 目前充值服务器只有1个，固定，后期量大了改为负载均衡
            Integer payServerId = ServerId.PAY.getStartId();
            LogicServer2PayServerServiceGrpc.LogicServer2PayServerServiceStub stub = logicServer2PayServerStubMap.get(payServerId);
            if (stub == null) {
                // 首次初始化
                stub = LogicServer2PayServerServiceGrpc.newStub(grpcManager.getGrpcChannelByServerId(payServerId));
                logicServer2PayServerStubMap.put(payServerId, stub);
            }
            return stub;
        } catch (Exception e) {
            log.error("获取逻辑服--->充值服stub异常：", e);
            return null;
        }
    }

    /**
     * 服务器执行gm命令阻塞stub集合，key=服务器id value=block stub
     */
    @Getter(AccessLevel.PRIVATE)
    private final Map<Integer, Server2ServerGmReloadServiceGrpc.Server2ServerGmReloadServiceBlockingStub> serverGmReloadBlockStubMap = new HashMap<>();

    /**
     * 获取服务器处理gm grpc block stub
     *
     * @return
     */
    public Server2ServerGmReloadServiceGrpc.Server2ServerGmReloadServiceBlockingStub getServerGmBlockStub(Integer serverId) {
        try {
            Server2ServerGmReloadServiceGrpc.Server2ServerGmReloadServiceBlockingStub stub = serverGmReloadBlockStubMap.get(serverId);
            if (stub == null) {
                // 首次初始化
                stub = Server2ServerGmReloadServiceGrpc.newBlockingStub(grpcManager.getGrpcChannelByServerId(serverId));
                serverGmReloadBlockStubMap.put(serverId, stub);
            }
            return stub;
        } catch (Exception e) {
            log.error("获取服务器处理gm grpc block stub异常：", e);
            return null;
        }
    }


    /**
     * 服务器执行gm命令非阻塞stub集合，key=服务器id value=stub
     */
    @Getter(AccessLevel.PRIVATE)
    private final Map<Integer, Server2ServerGmReloadServiceGrpc.Server2ServerGmReloadServiceStub> serverGmReloadStubMap = new HashMap<>();

    /**
     * 获取服务器处理gm grpc stub
     *
     * @return
     */
    public Server2ServerGmReloadServiceGrpc.Server2ServerGmReloadServiceStub getServerGmStub(Integer serverId) {
        try {
            Server2ServerGmReloadServiceGrpc.Server2ServerGmReloadServiceStub stub = serverGmReloadStubMap.get(serverId);
            if (stub == null) {
                // 首次初始化
                stub = Server2ServerGmReloadServiceGrpc.newStub(grpcManager.getGrpcChannelByServerId(serverId));
                serverGmReloadStubMap.put(serverId, stub);
            }
            return stub;
        } catch (Exception e) {
            log.error("获取服务器处理gm grpc stub异常：", e);
            return null;
        }
    }

}
