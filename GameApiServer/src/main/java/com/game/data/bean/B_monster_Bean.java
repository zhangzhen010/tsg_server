package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * monster Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "monster")
public class B_monster_Bean {
	/**
	 * 怪物id
	 */
	@Id
	private Integer id;
	/**
	 * 怪物名字
	 */
	private Integer name;
	/**
	 * 携带幸存者id
	 */
	private List<Integer> survivorId = new ArrayList<>();
	/**
	 * 携带幸存者等级
	 */
	private List<Integer> survivorLevel = new ArrayList<>();
	/**
	 * 携带宠物id
	 */
	private List<Integer> petId = new ArrayList<>();
	/**
	 * 携带宠物等级
	 */
	private List<Integer> petLevel = new ArrayList<>();
	/**
	 * 怪物携带技能id
	 */
	private Integer skillId;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getName() {
		return this.name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public List<Integer> getSurvivorId() {
		return this.survivorId;
	}

	public void setSurvivorId(List<Integer> survivorId) {
		this.survivorId = survivorId;
	}

	public List<Integer> getSurvivorLevel() {
		return this.survivorLevel;
	}

	public void setSurvivorLevel(List<Integer> survivorLevel) {
		this.survivorLevel = survivorLevel;
	}

	public List<Integer> getPetId() {
		return this.petId;
	}

	public void setPetId(List<Integer> petId) {
		this.petId = petId;
	}

	public List<Integer> getPetLevel() {
		return this.petLevel;
	}

	public void setPetLevel(List<Integer> petLevel) {
		this.petLevel = petLevel;
	}

	public Integer getSkillId() {
		return this.skillId;
	}

	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}


}