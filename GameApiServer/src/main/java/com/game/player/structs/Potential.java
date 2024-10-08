package com.game.player.structs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcl
 * @desc ""
 * @data 2024/8/5
 */
public class Potential extends Item{
    /**
     * 等级
     */
    private int lv;
    /**
     * 品质
     */
    private int quality;
    /**
     * 位置
     */
    private int pos;
    /**
     * 属性
     */
    private List<Long> attrList = new ArrayList<>();

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public List<Long> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<Long> attrList) {
        this.attrList = attrList;
    }
}
