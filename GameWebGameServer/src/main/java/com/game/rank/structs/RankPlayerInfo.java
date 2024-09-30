package com.game.rank.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 排行榜玩家信息
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/14 18:15
 */
@Getter
@Setter
public class RankPlayerInfo {

    /**
     * 玩家名字
     */
    private String playerName = "";
    /**
     * 头像
     */
    private String avatarUrl = "";
    /**
     * 今日糖果分
     */
    private int todayCandyScore;
    /**
     * 糖果分
     */
    private int candyScore;

}
