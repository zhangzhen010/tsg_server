package com.game.player.structs;

import com.game.bean.proto.BeanProto;
import lombok.Getter;
import lombok.Setter;

/**
 * 一场战斗数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/5/24 11:16
 */
@Getter
@Setter
public class FightData {

    /**
     * 战斗唯一id
     */
    private long fightId;
    /**
     * 0=战斗未结束 1=攻击者胜利 2=防守者胜利
     */
    private int win;
    /**
     * 已执行回合数
     */
    private int round = 0;
    /**
     * 当前回合是否已结束（如果一方死亡，则结束，查看是否有复活buff，有则进入下回合，否则战斗结束）
     */
    private boolean roundEnd;
    /**
     * 当前回合执行了递归运算次数，大于上限就不处理递归相关操作
     */
    private int recursionCount = 0;
    /**
     * 战斗攻击者
     */
    private FightTeam atkTeam = new FightTeam();
    /**
     * 战斗防守者
     */
    private FightTeam defTeam = new FightTeam();
    /**
     * 战斗数据（这里每次都new，方便以后改为多线程战斗，目前是单线程，除非有很大的性能影响改为单线程单对象使用）
     */
    private BeanProto.FightInfo.Builder fightInfoBuilder = BeanProto.FightInfo.newBuilder();

}
