package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 战斗效果（立即生效）
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/5/24 11:37
 */
@Getter
@Setter
public class FightEffect {

    /**
     * 效果唯一id(客户端用)
     */
    private long id;
    /**
     * 效果配置id
     */
    private Integer configId;
    /**
     * 效果释放者
     */
    private Fighter sourceFighter;
    /**
     * 效果拥有者
     */
    private Fighter targetFighter;

}
