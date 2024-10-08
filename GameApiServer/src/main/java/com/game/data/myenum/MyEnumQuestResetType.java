package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumQuestResetType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 01:00
 */
@Getter
public enum MyEnumQuestResetType {
    /**
     * 按功能刷新
     */
    NONE(0),
    /**
     * 每日刷新
     */
    DAY(1),
    /**
     * 每周刷新
     */
    WEEK(2),
    /**
     * 每月刷新
     */
    MONTH(3),

    ;

    private final Integer type;

    private static final Map<Integer, MyEnumQuestResetType> map = new HashMap<>();

    private static final List<MyEnumQuestResetType> list = new ArrayList<>();

    MyEnumQuestResetType(Integer type) {
        this.type = type;
    }

    public static MyEnumQuestResetType getMyEnumQuestResetType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumQuestResetType[] values = MyEnumQuestResetType.values();
                    for (MyEnumQuestResetType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<MyEnumQuestResetType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumQuestResetType.values()));
                }
            }
        }
        return list;
    }

}