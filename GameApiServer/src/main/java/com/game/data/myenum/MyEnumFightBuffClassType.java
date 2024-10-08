package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumFightBuffClassType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumFightBuffClassType {
    /**
     * 增益buff
     */
    BUFF(1),
    /**
     * 减益buff
     */
    DEBUFF(2),

    ;

    private final Integer type;

    private static final Map<Integer, MyEnumFightBuffClassType> map = new HashMap<>();

    private static final List<MyEnumFightBuffClassType> list = new ArrayList<>();

    MyEnumFightBuffClassType(Integer type) {
        this.type = type;
    }

    public static MyEnumFightBuffClassType getMyEnumFightBuffClassType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumFightBuffClassType[] values = MyEnumFightBuffClassType.values();
                    for (MyEnumFightBuffClassType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<MyEnumFightBuffClassType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumFightBuffClassType.values()));
                }
            }
        }
        return list;
    }

}