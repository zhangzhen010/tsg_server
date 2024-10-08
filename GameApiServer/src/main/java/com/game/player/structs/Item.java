package com.game.player.structs;

/**
 * 物品
 *
 * @author zhangzhen
 * @time 2020年3月4日
 */
public class Item {

    /**
     * 唯一id
     */
    private long id;
    /**
     * 物品id
     */
    private Integer configId = 0;
    /**
     * 拥有数量
     */
    private int num;
    /**
     * 物品过期时间，到期后做删除处理0表示永不过期(时间戳毫秒)
     */
    private long delTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getDelTime() {
        return delTime;
    }

    public void setDelTime(long delTime) {
        this.delTime = delTime;
    }

}
