package com.game.datagroup.structs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 卡池中不同品阶卡片动画数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/10/14 17:17
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GachaPoolAnimationDataRarityData {

    /**
     * 卡片品阶
     */
    private String rarity = "";
    /**
     * 卡片动画
     */
    private String imageUrl = "";

}
