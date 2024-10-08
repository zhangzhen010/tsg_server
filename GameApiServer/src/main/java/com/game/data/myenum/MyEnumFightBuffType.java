package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumFightBuffType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumFightBuffType {
    /**
     * 提升属性
     */
    ATT_UP(1),
    /**
     * 降低属性
     */
    ATT_DOWN(2),
    /**
     * 持续回血
     */
    PER_UP_HP(3),
    /**
     * 持续扣血（灼烧）
     */
    PER_DOWN_HP1(4),
    /**
     * 持续回怒
     */
    PER_UP_MP(5),
    /**
     * 偷取敌人某属性
     */
    STEAL1(6),
    /**
     * 眩晕
     */
    VERTIGO(7),
    /**
     * 冰冻
     */
    FROZEN(8),
    /**
     * 根据属性增加属性
     */
    ATT_UP_ATT(9),
    /**
     * 根据属性降低属性
     */
    ATT_DOWN_ATT(10),
    /**
     * 易伤
     */
    HURT(11),
    /**
     * 免疫某属性
     */
    IMM(12),
    /**
     * 护盾
     */
    SHIELD(14),
    /**
     * 复活
     */
    RESURRECTION(16),
    /**
     * 持续扣血（流血）
     */
    PER_DOWN_HP2(401),
    /**
     * 偷取敌人某属性（有上限）
     */
    STEAL2(601),

    ;

    private final Integer type;

    private static final Map<Integer, MyEnumFightBuffType> map = new HashMap<>();

    private static final List<MyEnumFightBuffType> list = new ArrayList<>();

    MyEnumFightBuffType(Integer type) {
        this.type = type;
    }

    public static MyEnumFightBuffType getMyEnumFightBuffType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumFightBuffType[] values = MyEnumFightBuffType.values();
                    for (MyEnumFightBuffType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<MyEnumFightBuffType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumFightBuffType.values()));
                }
            }
        }
        return list;
    }

}