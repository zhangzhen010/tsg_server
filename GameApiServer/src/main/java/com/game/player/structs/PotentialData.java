package com.game.player.structs;

import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcl
 * @desc "玩家潜能"
 * @data 2024/8/5
 */
public class PotentialData {
    /**
     * 装备的潜能
     */
    private List<Potential> potentialList = new ArrayList<>();
    /**
     * 潜能等级
     */
    private int potentialLv;
    /**
     * 潜能经验
     */
    private int potentialExp;
    /**
     * 鉴定值
     */
    private int identifyExp;
    /**
     * 激发出的潜能
     */
    private List<Potential> stimulationPotentialList = new ArrayList<>();
    /**
     * 技能
     */
    private List<Integer> skillList = new ArrayList<>();
    /**
     * 临时潜能
     */
    @Transient
    private transient Potential tempPotential;

    public List<Potential> getPotentialList() {
        return potentialList;
    }

    public void setPotentialList(List<Potential> potentialList) {
        this.potentialList = potentialList;
    }

    public int getPotentialLv() {
        return potentialLv;
    }

    public void setPotentialLv(int potentialLv) {
        this.potentialLv = potentialLv;
    }

    public int getPotentialExp() {
        return potentialExp;
    }

    public void setPotentialExp(int potentialExp) {
        this.potentialExp = potentialExp;
    }

    public int getIdentifyExp() {
        return identifyExp;
    }

    public void setIdentifyExp(int identifyExp) {
        this.identifyExp = identifyExp;
    }

    public List<Potential> getStimulationPotentialList() {
        return stimulationPotentialList;
    }

    public void setStimulationPotentialList(List<Potential> stimulationPotentialList) {
        this.stimulationPotentialList = stimulationPotentialList;
    }

    public Potential getTempPotential() {
        return tempPotential;
    }

    public void setTempPotential(Potential tempPotential) {
        this.tempPotential = tempPotential;
    }

    public List<Integer> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Integer> skillList) {
        this.skillList = skillList;
    }
}
