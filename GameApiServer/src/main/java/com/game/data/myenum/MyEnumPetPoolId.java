package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumPetPoolId
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumPetPoolId {
    /**
     * 广告池
     */
    AD_POOL(1),
    /**
     * 道具池
     */
    ITEM_POOL(2),

    ;

    private final Integer id;

    private static final Map<Integer, MyEnumPetPoolId> map = new HashMap<>();

    private static final List<MyEnumPetPoolId> list = new ArrayList<>();

    MyEnumPetPoolId(Integer id) {
        this.id = id;
    }

    public static MyEnumPetPoolId getMyEnumPetPoolId(Integer id) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumPetPoolId[] values = MyEnumPetPoolId.values();
                    for (MyEnumPetPoolId value : values) {
                        map.put(value.getId(), value);
                    }
                }
            }
        }
        return map.get(id);
    }

    public static List<MyEnumPetPoolId> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumPetPoolId.values()));
                }
            }
        }
        return list;
    }

}