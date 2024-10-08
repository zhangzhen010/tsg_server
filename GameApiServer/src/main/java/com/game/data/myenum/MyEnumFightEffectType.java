package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumFightEffectType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumFightEffectType {
    /**
     * 造成伤害
     */
    DAMAGE(1),
    /**
     * 恢复生命
     */
    ADD_HP(2),
    /**
     * 恢复怒气
     */
    ADD_MP(4),
    /**
     * 获得buff
     */
    ADD_BUFF(12),
    /**
     * 造成x属性的附加伤害，且不超过攻击者攻击力的x
     */
    EXTRA_DAMAGE(13),
    /**
     * 对双方造成等量伤害，且不超过攻击者攻击力的x
     */
    ME_ENEMY_DAMAGE(14),
    /**
     * 结算流血伤害并使流血伤害增加x%
     */
    BLOOD_DAMAGE(15),
    /**
     * 权重造成伤害
     */
    ODDS_DAMAGE(17),

    ;

    private final Integer type;

    private static final Map<Integer, MyEnumFightEffectType> map = new HashMap<>();

    private static final List<MyEnumFightEffectType> list = new ArrayList<>();

    MyEnumFightEffectType(Integer type) {
        this.type = type;
    }

    public static MyEnumFightEffectType getMyEnumFightEffectType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumFightEffectType[] values = MyEnumFightEffectType.values();
                    for (MyEnumFightEffectType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<MyEnumFightEffectType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumFightEffectType.values()));
                }
            }
        }
        return list;
    }

}