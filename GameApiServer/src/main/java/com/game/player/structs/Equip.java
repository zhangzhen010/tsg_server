package com.game.player.structs;

import com.game.utils.ID;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcl
 * @desc ""
 * @data 2024/6/3
 */
public class Equip extends Item {

    /**
     * 装备属性<id,value,id,value>
     */
    private List<Long> attributeList = new ArrayList<>();
    /**
     * 等级
     */
    private int level;
    /**
     * 品质
     */
    private int quality;

    public Equip() {
        setId(ID.getId());
    }

    public List<Long> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Long> attributeList) {
        this.attributeList = attributeList;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
