package com.game.player.structs;

import lombok.Getter;

import java.util.*;

/**
 * burn卡片类型
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/23 11:42
 */
@Getter
public enum GachaCardRefundType {
    /**
     * 销毁卡片获得candy
     */
    CANDY(1),
    /**
     * 销毁卡片获得同质化代币
     */
    FT(2),

    ;

    private final Integer type;

    private static final Map<Integer, GachaCardRefundType> map = new HashMap<>();

    private static final List<GachaCardRefundType> list = new ArrayList<>();

    GachaCardRefundType(Integer type) {
        this.type = type;
    }

    public static GachaCardRefundType getMyEnumQuestType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    GachaCardRefundType[] values = GachaCardRefundType.values();
                    for (GachaCardRefundType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<GachaCardRefundType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(GachaCardRefundType.values()));
                }
            }
        }
        return list;
    }

}