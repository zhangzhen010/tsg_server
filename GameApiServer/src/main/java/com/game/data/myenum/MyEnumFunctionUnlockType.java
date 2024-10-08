package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumFunctionUnlockType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumFunctionUnlockType {
    /**
     * 无条件开放
     */
    NONE(0),
    /**
     * 等级解锁
     */
    LV(1),
    /**
     * 任务解锁
     */
    QUEST(2),
    /**
     * 等级+开服天数解锁
     */
    LV_DAY(3),

    ;

    private final Integer type;

    private static final Map<Integer, MyEnumFunctionUnlockType> map = new HashMap<>();

    private static final List<MyEnumFunctionUnlockType> list = new ArrayList<>();

    MyEnumFunctionUnlockType(Integer type) {
        this.type = type;
    }

    public static MyEnumFunctionUnlockType getMyEnumFunctionUnlockType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumFunctionUnlockType[] values = MyEnumFunctionUnlockType.values();
                    for (MyEnumFunctionUnlockType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<MyEnumFunctionUnlockType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumFunctionUnlockType.values()));
                }
            }
        }
        return list;
    }

}