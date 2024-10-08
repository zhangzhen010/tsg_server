package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumFunctionType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumFunctionType {
    /**
     * 主关卡冒险
     */
    MAIN_STAGE(1),
    /**
     * 英雄
     */
    HERO(2),
    /**
     * 幸存者
     */
    SURVIVOR(3),
    /**
     * 翅膀
     */
    WING(4),
    /**
     * 本服竞技场
     */
    LOCAL_ARENA(5),
    /**
     * 跨服竞技场
     */
    PUBLIC_ARENA(6),

    ;

    private final Integer type;

    private static final Map<Integer, MyEnumFunctionType> map = new HashMap<>();

    private static final List<MyEnumFunctionType> list = new ArrayList<>();

    MyEnumFunctionType(Integer type) {
        this.type = type;
    }

    public static MyEnumFunctionType getMyEnumFunctionType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumFunctionType[] values = MyEnumFunctionType.values();
                    for (MyEnumFunctionType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<MyEnumFunctionType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumFunctionType.values()));
                }
            }
        }
        return list;
    }

}