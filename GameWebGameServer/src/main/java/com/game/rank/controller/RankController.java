package com.game.rank.controller;

import com.game.config.CurrentPlayer;
import com.game.controller.structs.ResponseBean;
import com.game.player.structs.WebPlayer;
import com.game.rank.manager.RankManager;
import com.game.rank.structs.ResRankCandy;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 排行榜
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 9:29
 */
@RestController
@Log4j2
@RequestMapping(value = "/tsg/rank")
public class RankController {

    private @Resource RankManager rankManager;

    /**
     * 请求糖果排行榜列表
     *
     * @param player
     * @return
     */
    @PostMapping("/candy")
    public ResponseBean<Object> candy(@CurrentPlayer WebPlayer player) {
        try {
            ResRankCandy info = rankManager.reqRankCandyInfo(player);
            if (info != null) {
                return ResponseBean.success(info);
            } else {
                return ResponseBean.fail("request rank list fail");
            }
        } catch (Exception e) {
            log.error("请求排行榜列表异常：", e);
            return ResponseBean.fail("request rank list exception:" + e.getMessage());
        }
    }

}
