package com.game.player.structs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

/**
 * 卡池抽卡概率对应卡片组卡片信息
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/9 16:59
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GachaPoolWeightCardInfo {

    /**
     * 卡片模板id
     */
    private String cardTemplateId;
    /**
     * 可抽卡数量
     */
    private int num;
    /**
     * 卡片模板图片(仅展示)
     */
    @Transient
    private transient String image = "";
    /**
     * 卡片模板价格(仅展示)
     */
    @Transient
    private transient int usd;

    public GachaPoolWeightCardInfo(String cardTemplateId, int num) {
        this.cardTemplateId = cardTemplateId;
        this.num = num;
    }
}
