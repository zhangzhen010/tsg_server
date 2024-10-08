package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumQuestType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 01:00
 */
@Getter
public enum MyEnumQuestType {
    /**
     * 每日任务
     */
    EVERY_DAY(1),
    /**
     * 主线任务
     */
    MAIN(2),

    ;

    private final Integer type;

    private static final Map<Integer, MyEnumQuestType> map = new HashMap<>();

    private static final List<MyEnumQuestType> list = new ArrayList<>();

    MyEnumQuestType(Integer type) {
        this.type = type;
    }

    public static MyEnumQuestType getMyEnumQuestType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumQuestType[] values = MyEnumQuestType.values();
                    for (MyEnumQuestType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<MyEnumQuestType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumQuestType.values()));
                }
            }
        }
        return list;
    }

}