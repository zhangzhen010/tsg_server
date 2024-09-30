package com.game.award.manager;

import com.alibaba.fastjson2.JSON;
import com.game.award.structs.AwardList;
import com.game.award.utils.AwardUtils;
import com.game.pack.manager.PackManager;
import com.game.player.structs.WebPlayer;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 奖励管理类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/6 8:49
 */
@Component
@Log4j2
public class AwardManager {

    private @Resource PackManager packManager;
    private @Resource AwardUtils awardUtils;

    /**
     * 获取奖励库奖励
     *
     * @param rewardId
     * @return
     */
    public AwardList getAwardList(Integer rewardId) {
        try {
            // 没有附加奖励
            return awardUtils.getAward(rewardId, true);
        } catch (Exception e) {
            log.error("获取奖励库奖励数量rewardId=" + rewardId, e);
            return null;
        }
    }

    /**
     * 获取奖励库奖励数量并且不合并
     *
     * @param rewardId
     * @return
     */
    public AwardList getAwardListNoMerge(Integer rewardId) {
        try {
            // 没有附加奖励
            return awardUtils.getAward(rewardId, false);
        } catch (Exception e) {
            log.error("获取奖励库奖励数量rewardId=" + rewardId, e);
            return null;
        }
    }

    /**
     * 发送奖励给玩家
     *
     * @param player
     * @param list
     * @param logReason
     */
    public void sendAwardList(WebPlayer player, List<Integer> list, Integer logReason) {
        try {
            if (list.size() % 2 != 0) {
                log.error("发送奖励给玩家，奖励列表异常：" + JSON.toJSONString(list));
                return;
            }
            // 发送奖励库奖励给玩家
            for (int i = 0; i < list.size(); i += 2) {
                Integer goodsId = list.get(i);
                Integer value = list.get(i + 1);
                // 发送奖励给玩家
                packManager.addItemByConfigId(player, goodsId, value, logReason);
            }
        } catch (Exception e) {
            log.error("发送奖励库奖励给玩家", e);
        }
    }

}