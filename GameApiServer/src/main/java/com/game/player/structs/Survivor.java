package com.game.player.structs;

/**
 * @author lcl
 * @desc "幸存者"
 * @data 2024/7/3
 */
public class Survivor {
    /**
     * 唯一id
     */
    private long id;
    /**
     * 配置id
     */
    private int configId;
    /**
     * 等级
     */
    private int lv;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

}
