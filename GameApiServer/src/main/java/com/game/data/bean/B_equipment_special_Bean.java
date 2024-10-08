package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * equipment_special Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "equipment_special")
public class B_equipment_special_Bean {
	/**
	 * 品质id
	 */
	@Id
	private Integer id;
	/**
	 * 属性id
	 */
	private List<Integer> special = new ArrayList<>();
	/**
	 * 特殊属性值
	 */
	private List<Integer> specialAttributeValue = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getSpecial() {
		return this.special;
	}

	public void setSpecial(List<Integer> special) {
		this.special = special;
	}

	public List<Integer> getSpecialAttributeValue() {
		return this.specialAttributeValue;
	}

	public void setSpecialAttributeValue(List<Integer> specialAttributeValue) {
		this.specialAttributeValue = specialAttributeValue;
	}


}