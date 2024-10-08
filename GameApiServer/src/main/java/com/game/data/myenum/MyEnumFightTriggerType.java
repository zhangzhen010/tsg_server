package com.game.data.myenum;

import lombok.Getter;

import java.util.*;

/**
 * MyEnumFightTriggerType
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Getter
public enum MyEnumFightTriggerType {
    /**
     * 允许任何触发类型
     */
    NONE(0),
    /**
     * 普攻触发
     */
    NORMAL_ATK(1),
    /**
     * 怒攻触发
     */
    MP_ATK(2),
    /**
     * 宠物攻击触发
     */
    PET_ATK(3),
    /**
     * 受到人物攻击时触发
     */
    ATTACKED(4),
    /**
     * 受到宠物攻击时触发
     */
    PET_ATTACKED(5),
    /**
     * 连击时触发
     */
    BATTER(6),
    /**
     * 被连击时触发
     */
    BATTERED(7),
    /**
     * 闪避后触发
     */
    DODGE(8),
    /**
     * 被闪避后触发
     */
    DODGED(9),
    /**
     * 反击后触发
     */
    COUNTERATTACK(10),
    /**
     * 被反击后触发
     */
    COUNTERATTACKED(11),
    /**
     * 暴击后触发
     */
    CRITICAL(12),
    /**
     * 被暴击后触发
     */
    CRITICALED(13),
    /**
     * 击晕后触发
     */
    STUN(14),
    /**
     * 被击晕后触发
     */
    STUNED(15),
    /**
     * 战斗开始时触发
     */
    START(16),
    /**
     * 回合开始时触发
     */
    ROUNDBEGIN(17),
    /**
     * 死亡时触发
     */
    DEAD(18),
    /**
     * 回合结束时触发
     */
    ROUNDEND(19),
    /**
     * 添加触发
     */
    INSTANTLY(20),
    /**
     * 造成伤害触发
     */
    MAKE_DAMAGE(21),
    /**
     * 受到伤害触发
     */
    GET_DAMAGE(22),
    /**
     * 普攻命中触发
     */
    PG_NOTMISS(23),
    /**
     * 反击命中触发
     */
    FJ_NOTMISS(24),

    ;

    private final Integer type;

    private static final Map<Integer, MyEnumFightTriggerType> map = new HashMap<>();

    private static final List<MyEnumFightTriggerType> list = new ArrayList<>();

    MyEnumFightTriggerType(Integer type) {
        this.type = type;
    }

    public static MyEnumFightTriggerType getMyEnumFightTriggerType(Integer type) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    MyEnumFightTriggerType[] values = MyEnumFightTriggerType.values();
                    for (MyEnumFightTriggerType value : values) {
                        map.put(value.getType(), value);
                    }
                }
            }
        }
        return map.get(type);
    }

    public static List<MyEnumFightTriggerType> getList() {
        if (list.isEmpty()) {
            synchronized (list) {
                if (list.isEmpty()) {
                    list.addAll(Arrays.asList(MyEnumFightTriggerType.values()));
                }
            }
        }
        return list;
    }

}