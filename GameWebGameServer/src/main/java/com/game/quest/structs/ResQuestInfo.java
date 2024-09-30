package com.game.quest.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 单个任务数据（网页端）
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/14 15:25
 */
@Getter
@Setter
public class ResQuestInfo {

    /**
     * 任务配置id
     */
    private int questId;
    /**
     * 任务类型
     */
    private int questType;
    /**
     * 任务描述
     */
    private String questDesc = "";
    /**
     * 跳转链接
     */
    private String skipUrl = "";
    /**
     * 任务所属平台（前端验证是否绑定）
     */
    private String platform = "";
    /**
     * 任务状态
     */
    private int questState;
    /**
     * 任务奖励糖果数量
     */
    private int questAwardCandy;
    /**
     * 是否跳转
     */
    private boolean skip;

}
