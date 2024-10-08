package com.game.player.structs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 卡片扩展属性
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardAttribute {

    /**
     * 属性类型
     */
    private String traitType;
    /**
     * 属性值
     */
    private String value;

}
