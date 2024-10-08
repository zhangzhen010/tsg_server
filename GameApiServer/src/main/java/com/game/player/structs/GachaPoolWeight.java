package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡池抽卡概率对应卡片组
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/9 16:59
 */
@Getter
@Setter
public class GachaPoolWeight {

    /**
     * 权重
     */
    private int weight;
    /**
     * 卡片品质[SSR,SR,R,L,NR]
     */
    private String quality;
    /**
     * 销毁获得candy比例（百分比）
     */
    private int burnCandyRatio;
    /**
     * 销毁获得同质化货币比例（百分比）
     */
    private int burnFtRatio;
    /**
     * 卡片信息 key=卡片模板id value=卡片剩余数量
     */
    private List<GachaPoolWeightCardInfo> cardInfoList = new ArrayList<>();

}
