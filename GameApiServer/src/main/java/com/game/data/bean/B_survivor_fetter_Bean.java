package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * survivor_fetter Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "survivor_fetter")
public class B_survivor_fetter_Bean {
	/**
	 * 共鸣id
	 */
	@Id
	private Integer id;
	/**
	 * 共鸣组id
	 */
	private Integer groupId;
	/**
	 * 共鸣包含的幸存者
	 */
	private List<Integer> fetterGroupSkill = new ArrayList<>();
	/**
	 * 等级
	 */
	private Integer level;
	/**
	 * 升级需求
	 */
	private Integer levelLimit;
	/**
	 * 共鸣属性类型
	 */
	private Integer fetterAttributeType;
	/**
	 * 共鸣属性值
	 */
	private Integer fetterAttributeValue;

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

	public List<Integer> getFetterGroupSkill() {
		return this.fetterGroupSkill;
	}

	public void setFetterGroupSkill(List<Integer> fetterGroupSkill) {
		this.fetterGroupSkill = fetterGroupSkill;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getLevelLimit() {
		return this.levelLimit;
	}

	public void setLevelLimit(Integer levelLimit) {
		this.levelLimit = levelLimit;
	}

	public Integer getFetterAttributeType() {
		return this.fetterAttributeType;
	}

	public void setFetterAttributeType(Integer fetterAttributeType) {
		this.fetterAttributeType = fetterAttributeType;
	}

	public Integer getFetterAttributeValue() {
		return this.fetterAttributeValue;
	}

	public void setFetterAttributeValue(Integer fetterAttributeValue) {
		this.fetterAttributeValue = fetterAttributeValue;
	}


}