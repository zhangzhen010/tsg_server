package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumFightTriggerConditionType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumFightTriggerConditionType {
    /**
     * 所有条件均可触发
     */
    NONE(0),
    /**
     * 自身血量低于等于x%
     */
    ME_HP_LOWER(1),
    /**
     * 自身血量高于等于x%
     */
    ME_HP_HIGHER(2),
    /**
     * 敌人血量低于等于x%
     */
    ENEMY_HP_LOWER(3),
    /**
     * 敌人血量高于等于x%
     */
    ENEMY_HP_HIGHER(4),
    /**
     * 不能携带buff类型
     */
    NO_BUFF_TYPE(5),
    /**
     * 携带buff类型
     */
    HAVE_BUFF_TYPE(6),
    /**
     * 从第x回合开始
     */
    ROUND_TIME(7),
    /**
     * 自身携带增益或减益
     */
    ME_BUFF_DEBUFF(8),
    /**
     * 敌人携带增益或减益
     */
    ENEMY_BUFF_DEBUFF(801),

    ;

    private final Integer type;

    private static final Map<Integer, MyEnumFightTriggerConditionType> map = new HashMap<>();

    private static final List<MyEnumFightTriggerConditionType> list = new ArrayList<>();

    MyEnumFightTriggerConditionType(Integer type) {
        this.type = type;
    }

    public static MyEnumFightTriggerConditionType getMyEnumFightTriggerConditionType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumFightTriggerConditionType[] values = MyEnumFightTriggerConditionType.values();
                    for (MyEnumFightTriggerConditionType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<MyEnumFightTriggerConditionType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumFightTriggerConditionType.values()));
                }
            }
        }
        return list;
    }

}