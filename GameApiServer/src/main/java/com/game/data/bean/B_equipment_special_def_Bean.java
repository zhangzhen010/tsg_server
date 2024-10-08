package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * equipment_special_def Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "equipment_special_def")
public class B_equipment_special_def_Bean {
	/**
	 * 品质id
	 */
	@Id
	private Integer id;
	/**
	 * 属性id
	 */
	private List<Integer> specialDef = new ArrayList<>();
	/**
	 * 抗特殊属性值
	 */
	private List<Integer> specialDefAttributeValue = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getSpecialDef() {
		return this.specialDef;
	}

	public void setSpecialDef(List<Integer> specialDef) {
		this.specialDef = specialDef;
	}

	public List<Integer> getSpecialDefAttributeValue() {
		return this.specialDefAttributeValue;
	}

	public void setSpecialDefAttributeValue(List<Integer> specialDefAttributeValue) {
		this.specialDefAttributeValue = specialDefAttributeValue;
	}


}