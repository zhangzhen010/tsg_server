package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * pet Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "pet")
public class B_pet_Bean {
	/**
	 * 宠物id
	 */
	@Id
	private Integer id;
	/**
	 * 宠物名字
	 */
	private String nameId;
	/**
	 * 品质
	 */
	private Integer quality;
	/**
	 * 技能描述id
	 */
	private List<Integer> skillDes = new ArrayList<>();
	/**
	 * 技能id
	 */
	private List<Integer> skillId = new ArrayList<>();
	/**
	 * 技能升级等级
	 */
	private List<Integer> petSkillUnlock = new ArrayList<>();
	/**
	 * 重置与放生基础获得
	 */
	private List<Integer> recycleItem = new ArrayList<>();
	/**
	 * 重置消耗
	 */
	private List<Integer> resetItem = new ArrayList<>();
	/**
	 * 购买消耗
	 */
	private List<Integer> buyPriceId = new ArrayList<>();
	/**
	 * 初始被动个数
	 */
	private Integer initialPetPassiveSkillvalue;
	/**
	 * 被动技能池
	 */
	private List<Integer> petPassiveSkill = new ArrayList<>();
	/**
	 * 被动技能抽取权重
	 */
	private List<Integer> petPassiveSkillWeight = new ArrayList<>();
	/**
	 * 协同攻击触发概率万分比
	 */
	private Integer coordinateAttackRate;
	/**
	 * 属性继承比例
	 */
	private Integer attributeInheritanceRate;
	/**
	 * 宠物简介
	 */
	private String story;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameId() {
		return this.nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public List<Integer> getSkillDes() {
		return this.skillDes;
	}

	public void setSkillDes(List<Integer> skillDes) {
		this.skillDes = skillDes;
	}

	public List<Integer> getSkillId() {
		return this.skillId;
	}

	public void setSkillId(List<Integer> skillId) {
		this.skillId = skillId;
	}

	public List<Integer> getPetSkillUnlock() {
		return this.petSkillUnlock;
	}

	public void setPetSkillUnlock(List<Integer> petSkillUnlock) {
		this.petSkillUnlock = petSkillUnlock;
	}

	public List<Integer> getRecycleItem() {
		return this.recycleItem;
	}

	public void setRecycleItem(List<Integer> recycleItem) {
		this.recycleItem = recycleItem;
	}

	public List<Integer> getResetItem() {
		return this.resetItem;
	}

	public void setResetItem(List<Integer> resetItem) {
		this.resetItem = resetItem;
	}

	public List<Integer> getBuyPriceId() {
		return this.buyPriceId;
	}

	public void setBuyPriceId(List<Integer> buyPriceId) {
		this.buyPriceId = buyPriceId;
	}

	public Integer getInitialPetPassiveSkillvalue() {
		return this.initialPetPassiveSkillvalue;
	}

	public void setInitialPetPassiveSkillvalue(Integer initialPetPassiveSkillvalue) {
		this.initialPetPassiveSkillvalue = initialPetPassiveSkillvalue;
	}

	public List<Integer> getPetPassiveSkill() {
		return this.petPassiveSkill;
	}

	public void setPetPassiveSkill(List<Integer> petPassiveSkill) {
		this.petPassiveSkill = petPassiveSkill;
	}

	public List<Integer> getPetPassiveSkillWeight() {
		return this.petPassiveSkillWeight;
	}

	public void setPetPassiveSkillWeight(List<Integer> petPassiveSkillWeight) {
		this.petPassiveSkillWeight = petPassiveSkillWeight;
	}

	public Integer getCoordinateAttackRate() {
		return this.coordinateAttackRate;
	}

	public void setCoordinateAttackRate(Integer coordinateAttackRate) {
		this.coordinateAttackRate = coordinateAttackRate;
	}

	public Integer getAttributeInheritanceRate() {
		return this.attributeInheritanceRate;
	}

	public void setAttributeInheritanceRate(Integer attributeInheritanceRate) {
		this.attributeInheritanceRate = attributeInheritanceRate;
	}

	public String getStory() {
		return this.story;
	}

	public void setStory(String story) {
		this.story = story;
	}


}