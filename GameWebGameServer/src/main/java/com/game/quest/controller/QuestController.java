package com.game.quest.controller;

import com.game.config.CurrentPlayer;
import com.game.controller.structs.ResponseBean;
import com.game.login.structs.ResetPlayerType;
import com.game.player.manager.PlayerManager;
import com.game.player.structs.WebPlayer;
import com.game.quest.manager.QuestManager;
import com.game.quest.structs.ReqQuestAward;
import com.game.quest.structs.ResQuestAward;
import com.game.quest.structs.ResQuestList;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

/**
 * 任务
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 9:29
 */
@RestController
@Log4j2
@RequestMapping(value = "/tsg/quest")
public class QuestController {

    private @Resource PlayerManager playerManager;
    private @Resource QuestManager questManager;

    /**
     * 请求任务列表
     *
     * @param player
     * @return
     */
    @PostMapping("/list")
    public ResponseBean<Object> list(@CurrentPlayer WebPlayer player) {
        try {
            // 请求任务之前先检测每日刷新
            playerManager.resetPlayer(player, ResetPlayerType.ONEDAYPLAYER);
            ResQuestList info = questManager.buildQuestAll(player);
            if (info != null) {
                return ResponseBean.success(info);
            } else {
                return ResponseBean.fail("request quest list fail");
            }
        } catch (Exception e) {
            log.error("请求任务列表异常：", e);
            return ResponseBean.fail("request quest list exception:" + e.getMessage());
        }
    }

    /**
     * 请求任务完成跳转
     *
     * @param player
     * @param questConfigId
     * @return
     */
    @PostMapping("/skip")
    public ResponseBean<Object> award(@CurrentPlayer WebPlayer player, @RequestParam("questConfigId") int questConfigId) {
        try {
            questManager.reqQuestSkip(player, questConfigId);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("请求任务完成跳转异常：", e);
            return ResponseBean.fail("request quest skip exception:" + e.getMessage());
        }
    }

    /**
     * 请求任务领取
     *
     * @param player
     * @param reqData
     * @return
     */
    @PostMapping("/award")
    public ResponseBean<Object> award(@CurrentPlayer WebPlayer player, @RequestBody ReqQuestAward reqData) {
        try {
            ResQuestAward info = questManager.reqQuestAward(player, reqData);
            if (info != null) {
                return ResponseBean.success(info);
            } else {
                return ResponseBean.fail("request quest award fail");
            }
        } catch (Exception e) {
            log.error("请求任务领奖异常：", e);
            return ResponseBean.fail("request quest award exception:" + e.getMessage());
        }
    }

}
