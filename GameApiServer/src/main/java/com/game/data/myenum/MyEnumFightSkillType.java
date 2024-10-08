package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumFightSkillType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumFightSkillType {
    /**
     * 主动技能
     */
    ACTIVE(1),
    /**
     * 被动技能
     */
    PASSIVE(2),

    ;

    private final Integer type;

    private static final Map<Integer, MyEnumFightSkillType> map = new HashMap<>();

    private static final List<MyEnumFightSkillType> list = new ArrayList<>();

    MyEnumFightSkillType(Integer type) {
        this.type = type;
    }

    public static MyEnumFightSkillType getMyEnumFightSkillType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumFightSkillType[] values = MyEnumFightSkillType.values();
                    for (MyEnumFightSkillType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<MyEnumFightSkillType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumFightSkillType.values()));
                }
            }
        }
        return list;
    }

}