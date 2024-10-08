package com.game.prop.structs;

import java.util.HashMap;
import java.util.Map;

/**
 * 道具配置id
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/5 16:35
 */
public enum PropId {
    /**
     * 占位需要加锁的道具
     */
    LOCKITEM(-1, false),
    /**
     * NFT卡片
     */
    NFT_CARD(10001, false),

    ;

    /**
     * 道具id
     */
    private int id;
    /**
     * 是否使用道具锁(true=会在不同线程中被修改，false=只会在玩家线程修改)
     */
    private boolean lock;
    /**
     * 道具map
     */
    private static final Map<Integer, PropId> map = new HashMap<>();

    PropId(Integer id, boolean lock) {
        this.id = id;
        this.lock = lock;
    }

    public Integer getId() {
        return id;
    }

    public boolean isLock() {
        return lock;
    }

    public static PropId getPropId(Integer id) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    PropId[] values = PropId.values();
                    for (int i = 0; i < values.length; i++) {
                        PropId propId = values[i];
                        map.put(propId.getId(), propId);
                    }
                }
            }
        }
        return map.get(id);
    }

}
