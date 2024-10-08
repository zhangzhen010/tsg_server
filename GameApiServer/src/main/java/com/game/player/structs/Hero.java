package com.game.player.structs;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个宠物对象(在这个版本游戏里面叫宠物Pet，以后其他版本改为Hero即可)
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/5/22 下午2:03
 */
public class Hero extends Item {

	/**
	 * 宠物等级
	 */
	private int lv;
	/**
	 * 宠物星级
	 */
	private int star;
	/**
	 * 主动技能id
	 */
	private int skillId;
	/**
	 * 被动技能等级<技能id，技能等级>
	 */
	private List<Integer> passiveSkillLvList = new ArrayList<>();
	/**
	 * 品质
	 */
	private int quality;
	/**
	 * 是否锁定
	 */
	private boolean locked;

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public List<Integer> getPassiveSkillLvList() {
		return passiveSkillLvList;
	}

	public void setPassiveSkillLvList(List<Integer> passiveSkillLvList) {
		this.passiveSkillLvList = passiveSkillLvList;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
