package com.game.log.manager;

import com.game.log.bean.*;
import com.game.player.structs.WebPlayer;
import com.game.utils.TimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * 日志管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 14:20
 */
@Component
@Log4j2
public class LogsManager {

    @Qualifier("logsMongo")
    private @Resource MongoTemplate logsMongoTemplate;

    /**
     * 初始化玩家日志
     */
    private void initPlayerLog(WebPlayer player, PlayerLog playerLog) {
        try {
            playerLog.setPlayerId(player.getPlayerId());
            playerLog.setUserName(player.getUserName());
            playerLog.setPlayerName(player.getPlayerName());
            // 玩家当前付费金额(暂时不用)
            playerLog.setPay(0);
            playerLog.setIp(player.getIp());
            playerLog.setCreateTime(player.getCreateTime());
        } catch (Exception e) {
            log.error("初始化玩家日志异常：", e);
        }
    }

    /**
     * 保存服务器在线日志
     */
    public void saveServerOnlineLog(int serverId, int onlineNum) {
        try {
            ServerOnlineLog serverOnlineLog = new ServerOnlineLog();
            serverOnlineLog.setServerId(serverId);
            serverOnlineLog.setNum(onlineNum);
            logsMongoTemplate.save(serverOnlineLog);
        } catch (Exception e) {
            log.error("保存服务器在线日志异常：", e);
        }
    }


    /**
     * 发送创建角色日志
     *
     * @param player
     */
    public void savePlayerCreateLog(WebPlayer player) {
        try {
            PlayerCreateLog playerCreateLog = new PlayerCreateLog();
            initPlayerLog(player, playerCreateLog);
            logsMongoTemplate.insert(playerCreateLog, playerCreateLog.getTabName() + TimeUtil.getYyyyMmDd(System.currentTimeMillis()));
        } catch (Exception e) {
            log.error("发送创建角色日志", e);
        }
    }

    /**
     * 发送玩家登录日志
     *
     * @param player
     */
    public void savePlayerLoginLog(WebPlayer player) {
        try {
            PlayerLoginLog playerLoginLog = new PlayerLoginLog();
            initPlayerLog(player, playerLoginLog);
            playerLoginLog.setLoginType("login");
            logsMongoTemplate.insert(playerLoginLog, playerLoginLog.getTabName() + TimeUtil.getYyyyMmDd(System.currentTimeMillis()));
        } catch (Exception e) {
            log.error("发送玩家登录日志", e);
        }
    }

    /**
     * 发送玩家登出日志
     *
     * @param player
     */
    public void savePlayerLogoutLog(WebPlayer player, String msg) {
        try {
            PlayerLogoutLog playerLogoutLog = new PlayerLogoutLog();
            initPlayerLog(player, playerLogoutLog);
            playerLogoutLog.setLoginTime(player.getLoginTime());
            playerLogoutLog.setOnlineTime(System.currentTimeMillis() - player.getLoginTime());
            playerLogoutLog.setLogoutType(msg);
            logsMongoTemplate.insert(playerLogoutLog, playerLogoutLog.getTabName() + TimeUtil.getYyyyMmDd(System.currentTimeMillis()));
        } catch (Exception e) {
            log.error("发送玩家登出日志", e);
        }
    }

    /**
     * 发送资源道具日志
     *
     * @param player
     * @param itemId
     * @param itemConfigId
     * @param before
     * @param after
     * @param logReason
     */
    public void savePlayerItemLog(WebPlayer player, long itemId, int itemConfigId, long before, long after, Integer logReason) {
        try {
            PlayerItemLog playerItemLog = new PlayerItemLog();
            initPlayerLog(player, playerItemLog);
            playerItemLog.setItemId(itemId);
            playerItemLog.setItemConfigId(itemConfigId);
            playerItemLog.setBefore(before);
            playerItemLog.setAfter(after);
            playerItemLog.setLogReason(logReason);
            logsMongoTemplate.insert(playerItemLog, playerItemLog.getTabName() + TimeUtil.getYyyyMmDd(System.currentTimeMillis()));
        } catch (Exception e) {
            log.error("发送资源道具日志异常player=" + player.getPlayerId());
        }
    }

    /**
     * 发送资源道具日志(增加了特殊物品的扩展信息日志字段)
     * @param player
     * @param itemId
     * @param itemConfigId
     * @param before
     * @param after
     * @param logReason
     * @param extInfo
     */
    public void savePlayerItemLog(WebPlayer player, long itemId, int itemConfigId, long before, long after, Integer logReason, String extInfo) {
        try {
            PlayerItemLog playerItemLog = new PlayerItemLog();
            initPlayerLog(player, playerItemLog);
            playerItemLog.setItemId(itemId);
            playerItemLog.setItemConfigId(itemConfigId);
            playerItemLog.setBefore(before);
            playerItemLog.setAfter(after);
            playerItemLog.setLogReason(logReason);
            playerItemLog.setExtInfo(extInfo);
            logsMongoTemplate.insert(playerItemLog, playerItemLog.getTabName() + TimeUtil.getYyyyMmDd(System.currentTimeMillis()));
        } catch (Exception e) {
            log.error("发送资源道具日志异常player=" + player.getPlayerId());
        }
    }

}
