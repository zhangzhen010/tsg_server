package com.game.datagroup.structs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 卡池图片视频信息
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/20 15:34
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GachaPoolAnimationData {

    /**
     * 卡池抽卡动画图片地址
     */
    private String imageUrl;
    /**
     * 卡池抽卡动画视频地址
     */
    private String videoUrl;

}
