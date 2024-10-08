package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * chapter_stage Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "chapter_stage")
public class B_chapter_stage_Bean {
	/**
	 * 关卡ID
	 */
	@Id
	private Integer id;
	/**
	 * 下一关ID
	 */
	private Integer nextId;
	/**
	 * 所属章节
	 */
	private Integer group;
	/**
	 * 在所属章节的第几关（前端用）
	 */
	private Integer pass;
	/**
	 * 关卡名
	 */
	private Integer name;
	/**
	 * 怪物id
	 */
	private Integer monsterId;
	/**
	 * 怪物等级
	 */
	private Integer monsterLevel;
	/**
	 * 奖励表id
	 */
	private Integer reward;
	/**
	 * 玩家加成属性id
	 */
	private List<Integer> attributeId = new ArrayList<>();
	/**
	 * 玩家加成属性值
	 */
	private List<Integer> attrbuteValue = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNextId() {
		return this.nextId;
	}

	public void setNextId(Integer nextId) {
		this.nextId = nextId;
	}

	public Integer getGroup() {
		return this.group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public Integer getPass() {
		return this.pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

	public Integer getName() {
		return this.name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public Integer getMonsterId() {
		return this.monsterId;
	}

	public void setMonsterId(Integer monsterId) {
		this.monsterId = monsterId;
	}

	public Integer getMonsterLevel() {
		return this.monsterLevel;
	}

	public void setMonsterLevel(Integer monsterLevel) {
		this.monsterLevel = monsterLevel;
	}

	public Integer getReward() {
		return this.reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}

	public List<Integer> getAttributeId() {
		return this.attributeId;
	}

	public void setAttributeId(List<Integer> attributeId) {
		this.attributeId = attributeId;
	}

	public List<Integer> getAttrbuteValue() {
		return this.attrbuteValue;
	}

	public void setAttrbuteValue(List<Integer> attrbuteValue) {
		this.attrbuteValue = attrbuteValue;
	}


}