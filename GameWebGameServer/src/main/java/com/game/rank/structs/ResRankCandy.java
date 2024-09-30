package com.game.rank.structs;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 糖果排行榜
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/14 17:56
 */
@Getter
@Setter
public class ResRankCandy {

    /**
     * 自己的排名（-1=没有入榜）
     */
    private int selfRank = -1;
    /**
     * 今日糖果
     */
    private int selfTodayCandy = 0;
    /**
     * 总榜糖果
     */
    private int selfCandy = 0;
    /**
     * 头像地址
     */
    private String avatarUrl = "";
    /**
     * 排行榜列表(新服可能为空)
     */
    private List<RankPlayerInfo> rankPlayerInfoList = new ArrayList<>();

}
