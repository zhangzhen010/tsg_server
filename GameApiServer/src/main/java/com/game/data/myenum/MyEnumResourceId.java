package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumResourceId
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 01:00
 */
@Getter
public enum MyEnumResourceId {
    /**
     * 金币
     */
    GOLD(101, false),
    /**
     * 钻石
     */
    GEMS(102, false),
    /**
     * 糖果
     */
    CANDY(103, false),

    ;

    private final Integer id;

    private final boolean lock;

    private static final Map<Integer, MyEnumResourceId> map = new HashMap<>();

    private static final List<MyEnumResourceId> list = new ArrayList<>();

    MyEnumResourceId(Integer id, boolean lock) {
        this.id = id;
        this.lock = lock;
    }

    public static MyEnumResourceId getMyEnumResourceId(Integer id) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumResourceId[] values = MyEnumResourceId.values();
                    for (MyEnumResourceId value : values) {
                        map.put(value.getId(), value);
                    }
                }
            }
        }
        return map.get(id);
    }

    public static List<MyEnumResourceId> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumResourceId.values()));
                }
            }
        }
        return list;
    }

}