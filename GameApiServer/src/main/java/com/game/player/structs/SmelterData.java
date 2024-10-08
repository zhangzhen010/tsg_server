package com.game.player.structs;

import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcl
 * @desc "熔炉数据"
 * @data 2024/6/3
 */
public class SmelterData {

    /**
     * 熔炉等级
     */
    private int level = 1;
    /**
     * 升级结束时间
     */
    private long lvUpEndTime = -1;
    /**
     * 熔炉产出的未拾取的装备
     */
    private List<Equip> smelterEquip = new ArrayList<>();
    /**
     * 临时计算战力装备
     */
    @Transient
    private transient Equip tempEquip;

    public int getLevel() {
        return level;
    }


    public void setLevel(int level) {
        this.level = level;
    }

    public long getLvUpEndTime() {
        return lvUpEndTime;
    }

    public void setLvUpEndTime(long lvUpEndTime) {
        this.lvUpEndTime = lvUpEndTime;
    }

    public List<Equip> getSmelterEquip() {
        return smelterEquip;
    }

    public void setSmelterEquip(List<Equip> smelterEquip) {
        this.smelterEquip = smelterEquip;
    }

    public Equip getTempEquip() {
        return tempEquip;
    }

    public void setTempEquip(Equip tempEquip) {
        this.tempEquip = tempEquip;
    }
}
