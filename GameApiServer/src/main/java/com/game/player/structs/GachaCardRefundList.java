package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡片退款信息列表
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/9 20:57
 */
@Getter
@Setter
public class GachaCardRefundList {

    /**
     * 退款信息列表
     */
    private List<GachaCardRefund> refundList = new ArrayList<>();

}
