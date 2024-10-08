package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumFightTargetType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumFightTargetType {
    /**
     * 自己
     */
    SOURCE(1),
    /**
     * 敌人
     */
    TARGET(2),

    ;

    private final Integer type;

    private static final Map<Integer, MyEnumFightTargetType> map = new HashMap<>();

    private static final List<MyEnumFightTargetType> list = new ArrayList<>();

    MyEnumFightTargetType(Integer type) {
        this.type = type;
    }

    public static MyEnumFightTargetType getMyEnumFightTargetType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumFightTargetType[] values = MyEnumFightTargetType.values();
                    for (MyEnumFightTargetType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<MyEnumFightTargetType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumFightTargetType.values()));
                }
            }
        }
        return list;
    }

}