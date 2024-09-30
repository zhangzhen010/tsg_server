package com.game.quest.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回任务领奖
 */
@Getter
@Setter
public class ResQuestAward {

    /**
     * 领取的任务id
     */
    private int questId;
    /**
     * 领取的奖励糖果数量
     */
    private int questAwardCandy;
    /**
     * 玩家当前糖果数量
     */
    private int playerCandy;

}
