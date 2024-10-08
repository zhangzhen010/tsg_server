package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * equipment Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "equipment")
public class B_equipment_Bean {
	/**
	 * 道具id
	 */
	@Id
	private Integer id;
	/**
	 * 装备类型
	 */
	private Integer type;
	/**
	 * 基础属性类型
	 */
	private List<Integer> baseAttributeType = new ArrayList<>();
	/**
	 * 系数下限
	 */
	private List<Integer> attributeMin = new ArrayList<>();
	/**
	 * 系数上限
	 */
	private List<Integer> attributeMax = new ArrayList<>();
	/**
	 * 特殊属性类型
	 */
	private List<Integer> specialAttributeType = new ArrayList<>();
	/**
	 * 系数下限
	 */
	private List<Integer> specialAttributeMin = new ArrayList<>();
	/**
	 * 系数上限
	 */
	private List<Integer> specialAttributeMax = new ArrayList<>();
	/**
	 * 特殊对抗属性类型
	 */
	private List<Integer> specialDefAttributeType = new ArrayList<>();
	/**
	 * 系数下限
	 */
	private List<Integer> specialDefAttributeMin = new ArrayList<>();
	/**
	 * 系数上限
	 */
	private List<Integer> specialDefAttributeMax = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Integer> getBaseAttributeType() {
		return this.baseAttributeType;
	}

	public void setBaseAttributeType(List<Integer> baseAttributeType) {
		this.baseAttributeType = baseAttributeType;
	}

	public List<Integer> getAttributeMin() {
		return this.attributeMin;
	}

	public void setAttributeMin(List<Integer> attributeMin) {
		this.attributeMin = attributeMin;
	}

	public List<Integer> getAttributeMax() {
		return this.attributeMax;
	}

	public void setAttributeMax(List<Integer> attributeMax) {
		this.attributeMax = attributeMax;
	}

	public List<Integer> getSpecialAttributeType() {
		return this.specialAttributeType;
	}

	public void setSpecialAttributeType(List<Integer> specialAttributeType) {
		this.specialAttributeType = specialAttributeType;
	}

	public List<Integer> getSpecialAttributeMin() {
		return this.specialAttributeMin;
	}

	public void setSpecialAttributeMin(List<Integer> specialAttributeMin) {
		this.specialAttributeMin = specialAttributeMin;
	}

	public List<Integer> getSpecialAttributeMax() {
		return this.specialAttributeMax;
	}

	public void setSpecialAttributeMax(List<Integer> specialAttributeMax) {
		this.specialAttributeMax = specialAttributeMax;
	}

	public List<Integer> getSpecialDefAttributeType() {
		return this.specialDefAttributeType;
	}

	public void setSpecialDefAttributeType(List<Integer> specialDefAttributeType) {
		this.specialDefAttributeType = specialDefAttributeType;
	}

	public List<Integer> getSpecialDefAttributeMin() {
		return this.specialDefAttributeMin;
	}

	public void setSpecialDefAttributeMin(List<Integer> specialDefAttributeMin) {
		this.specialDefAttributeMin = specialDefAttributeMin;
	}

	public List<Integer> getSpecialDefAttributeMax() {
		return this.specialDefAttributeMax;
	}

	public void setSpecialDefAttributeMax(List<Integer> specialDefAttributeMax) {
		this.specialDefAttributeMax = specialDefAttributeMax;
	}


}