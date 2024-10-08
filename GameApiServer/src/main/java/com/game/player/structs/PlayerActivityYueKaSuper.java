package com.game.player.structs;

import com.game.bean.structs.IntId;

import java.util.ArrayList;
import java.util.List;

/**
 * 大月卡活动数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/22 9:32
 */
public class PlayerActivityYueKaSuper implements IntId {

    /**
     * 活动配置id
     */
    private int id;
    /**
     * 本周获得经验（不包含付费购买和本期任务，只验证每日和每周）
     */
    private int weekExp;
    /**
     * 已充值配置id列表
     */
    private List<Integer> payList = new ArrayList<>();

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeekExp() {
        return weekExp;
    }

    public void setWeekExp(int weekExp) {
        this.weekExp = weekExp;
    }

    public List<Integer> getPayList() {
        return payList;
    }

    public void setPayList(List<Integer> payList) {
        this.payList = payList;
    }
}
