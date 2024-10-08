package com.game.player.structs;

import com.game.bean.structs.IntId;

/**
 * 单个抽卡卡池数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/14 10:13
 */
public class LuckyDraw implements IntId {

    /**
     * 抽奖活动配置id
     */
    private int id;
    /**
     * 当前抽奖次数(包含单抽和十连抽次数，大保底或者进度奖励后清空)
     */
    private int count;
    /**
     * 总十连抽次数（记录抽取十连抽次数，本轮抽卡不清空）
     */
    private int tenCount;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTenCount() {
        return tenCount;
    }

    public void setTenCount(int tenCount) {
        this.tenCount = tenCount;
    }
}
