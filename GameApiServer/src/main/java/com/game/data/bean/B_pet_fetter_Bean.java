package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * pet_fetter Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "pet_fetter")
public class B_pet_fetter_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 宠物组
	 */
	private List<Integer> petGroup = new ArrayList<>();
	/**
	 * 加成属性
	 */
	private List<Integer> effects = new ArrayList<>();
	/**
	 * 加成属性值
	 */
	private List<Integer> effectValues = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getPetGroup() {
		return this.petGroup;
	}

	public void setPetGroup(List<Integer> petGroup) {
		this.petGroup = petGroup;
	}

	public List<Integer> getEffects() {
		return this.effects;
	}

	public void setEffects(List<Integer> effects) {
		this.effects = effects;
	}

	public List<Integer> getEffectValues() {
		return this.effectValues;
	}

	public void setEffectValues(List<Integer> effectValues) {
		this.effectValues = effectValues;
	}


}