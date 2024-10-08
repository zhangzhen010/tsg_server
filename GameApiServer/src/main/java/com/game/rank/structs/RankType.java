package com.game.rank.structs;

/**
 * 排行榜类型
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/14 16:03
 */
public enum RankType {

    /**
     * 竞技场积分榜
     */
    ARENA_SCORE(1, "arena_score"),

    ;

    private int type;

    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    RankType(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
