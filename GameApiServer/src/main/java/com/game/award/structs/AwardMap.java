package com.game.award.structs;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个奖励库所得到的奖励信息
 * 
 * @author zhangzhen
 * @time 2020年3月11日
 */
public class AwardMap {

	/**
	 * 装备数量
	 */
	private int equipNum;
	/**
	 * 英雄数量
	 */
	private int heroNum;
	/**
	 * 战纹数量
	 */
	private int warNum;
	/**
	 * 计算奖励 key=configId value=数量
	 */
	private Map<Integer, Integer> awardMap = new HashMap<>();

	public int getEquipNum() {
		return equipNum;
	}

	public void setEquipNum(int equipNum) {
		this.equipNum = equipNum;
	}

	public int getHeroNum() {
		return heroNum;
	}

	public void setHeroNum(int heroNum) {
		this.heroNum = heroNum;
	}

	public int getWarNum() {
		return warNum;
	}

	public void setWarNum(int warNum) {
		this.warNum = warNum;
	}

	public Map<Integer, Integer> getAwardMap() {
		return awardMap;
	}

	public void setAwardMap(Map<Integer, Integer> awardMap) {
		this.awardMap = awardMap;
	}

}
