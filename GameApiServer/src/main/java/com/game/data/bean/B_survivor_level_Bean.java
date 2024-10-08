package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * survivor_level Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "survivor_level")
public class B_survivor_level_Bean {
	/**
	 * 序列id
	 */
	@Id
	private Integer id;
	/**
	 * 幸存者组id
	 */
	private Integer groupId;
	/**
	 * 等级
	 */
	private Integer level;
	/**
	 * 升级所需道具
	 */
	private List<Integer> needItem = new ArrayList<>();
	/**
	 * 非专精加成效率
	 */
	private Integer bonusEfficiency;
	/**
	 * 专精加成效率
	 */
	private Integer specialBonusEfficiency;
	/**
	 * 幸存者属性
	 */
	private Integer attribute;
	/**
	 * 属性值
	 */
	private Integer attributeValue;
	/**
	 * 技能id
	 */
	private Integer skillId;
	/**
	 * 技能等级
	 */
	private Integer skillLv;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<Integer> getNeedItem() {
		return this.needItem;
	}

	public void setNeedItem(List<Integer> needItem) {
		this.needItem = needItem;
	}

	public Integer getBonusEfficiency() {
		return this.bonusEfficiency;
	}

	public void setBonusEfficiency(Integer bonusEfficiency) {
		this.bonusEfficiency = bonusEfficiency;
	}

	public Integer getSpecialBonusEfficiency() {
		return this.specialBonusEfficiency;
	}

	public void setSpecialBonusEfficiency(Integer specialBonusEfficiency) {
		this.specialBonusEfficiency = specialBonusEfficiency;
	}

	public Integer getAttribute() {
		return this.attribute;
	}

	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public Integer getAttributeValue() {
		return this.attributeValue;
	}

	public void setAttributeValue(Integer attributeValue) {
		this.attributeValue = attributeValue;
	}

	public Integer getSkillId() {
		return this.skillId;
	}

	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}

	public Integer getSkillLv() {
		return this.skillLv;
	}

	public void setSkillLv(Integer skillLv) {
		this.skillLv = skillLv;
	}


}