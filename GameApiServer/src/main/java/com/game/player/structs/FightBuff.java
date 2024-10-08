package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 战斗buff（下回合生效）
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/5/24 12:00
 */
@Getter
@Setter
public class FightBuff {

    /**
     * buff唯一id(客户端用)
     */
    private long id;
    /**
     * buff配置id
     */
    private Integer configId;
    /**
     * buff层数
     */
    private int num;
    /**
     * buff回合数 -1表示永久生效
     */
    private int round;
    /**
     * buff值
     */
    private long value;
    /**
     * 下一次激活回合数
     */
    private int nextActiveRound;
    /**
     * buff释放者
     */
    private Fighter sourceFighter;
    /**
     * buff拥有者
     */
    private Fighter targetFighter;

}
