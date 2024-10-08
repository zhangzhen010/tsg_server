package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumQuestTargetType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 01:00
 */
@Getter
public enum MyEnumQuestTargetType {
    /**
     * discord聊天频道发送聊天1次
     */
    DISCORD_CHAT(1),
    /**
     * 去x分享1次
     */
    X_SHARE(2),
    /**
     * 去x关注我们的账号1次
     */
    X_FOLLOW(3),
    /**
     * 去x修改横幅
     */
    X_UPDATE_BANNER(4),
    /**
     * 修改X名字后缀增加TSG
     */
    X_UPDATE_NAME(5),
    /**
     * 去x发推文@TokyoStupidGame
     */
    X_POST_TAG_TSG(6),

    ;

    private final Integer type;

    private static final Map<Integer, MyEnumQuestTargetType> map = new HashMap<>();

    private static final List<MyEnumQuestTargetType> list = new ArrayList<>();

    MyEnumQuestTargetType(Integer type) {
        this.type = type;
    }

    public static MyEnumQuestTargetType getMyEnumQuestTargetType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumQuestTargetType[] values = MyEnumQuestTargetType.values();
                    for (MyEnumQuestTargetType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<MyEnumQuestTargetType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumQuestTargetType.values()));
                }
            }
        }
        return list;
    }

}