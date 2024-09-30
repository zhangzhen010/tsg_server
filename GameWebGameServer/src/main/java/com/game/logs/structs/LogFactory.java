package com.game.logs.structs;

import com.game.data.manager.DataManager;
import com.game.data.myenum.MyEnumResourceId;
import com.game.log.manager.LogsManager;
import com.game.player.structs.WebPlayer;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * 日志工厂
 *
 * @author zhangzhen
 * @time 2020年3月10日
 */
@Component
@Log4j2
public class LogFactory {

    private @Resource LogsManager logsManager;
    private @Resource DataManager dataManager;

    /**
     * 资源增加日志
     *
     * @param player
     * @param resourceId          资源ID
     * @param num                 增加数量
     * @param logReason           日志来源
     * @param resourceBeforeValue 增加前的值
     * @param sumValue            增加后的总值
     */
    public void saveLogForAddResource(WebPlayer player, MyEnumResourceId resourceId, long num, Integer logReason, long resourceBeforeValue, long sumValue) {
        logsManager.savePlayerItemLog(player, resourceId.getId(), resourceId.getId(), resourceBeforeValue, sumValue, logReason);
    }

    /**
     * 资源减少日志
     *
     * @param player
     * @param resourceId          资源ID
     * @param num                 减少数量
     * @param logReason           日志来源
     * @param resourceBeforeValue 减少前的值
     * @param sumValue            减少后的总值
     */
    public void saveLogForReduceResource(WebPlayer player, MyEnumResourceId resourceId, long num, Integer logReason, long resourceBeforeValue, long sumValue) {
        logsManager.savePlayerItemLog(player, resourceId.getId(), resourceId.getId(), resourceBeforeValue, sumValue, logReason);
    }

    /**
     * 保存物品增加日志
     *
     * @param player
     * @param id
     * @param itemConfigId
     * @param changeNum
     * @param logReason
     * @param sumCount
     * @param extInfo
     */
    public void saveLogForAddItem(WebPlayer player, long id, Integer itemConfigId, int changeNum, Integer logReason, int sumCount, String extInfo) {
        try {
            logsManager.savePlayerItemLog(player, id, itemConfigId, sumCount - changeNum, sumCount, logReason, extInfo);
        } catch (Exception e) {
            log.error("保存物品增加日志异常：", e);
        }
    }

    /**
     * 保存物品减少日志
     *
     * @param player
     * @param id
     * @param itemConfigId
     * @param count
     * @param logReason
     * @param sumCount
     * @param extInfo
     */
    public void saveLogForRemoveItem(WebPlayer player, long id, Integer itemConfigId, int count, Integer logReason, int sumCount, String extInfo) {
        try {
            logsManager.savePlayerItemLog(player, id, itemConfigId, sumCount + Math.abs(count), sumCount, logReason, extInfo);
        } catch (Exception e) {
            log.error("保存物品减少日志异常：", e);
        }
    }

}
