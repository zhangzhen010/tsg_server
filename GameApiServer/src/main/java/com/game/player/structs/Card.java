package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 卡片类
 * 
 * @author zhangzhen
 * @time 2020年3月19日
 */
@Getter
@Setter
public class Card extends Item {

    /**
     * 卡片信息
     */
    private GachaCard card;

}
