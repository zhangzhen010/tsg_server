package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * equipment_base Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "equipment_base")
public class B_equipment_base_Bean {
	/**
	 * 等级id
	 */
	@Id
	private Integer id;
	/**
	 * 属性id
	 */
	private List<Integer> base = new ArrayList<>();
	/**
	 * 基础属性值
	 */
	private List<Long> baseAttributeValue = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getBase() {
		return this.base;
	}

	public void setBase(List<Integer> base) {
		this.base = base;
	}

	public List<Long> getBaseAttributeValue() {
		return this.baseAttributeValue;
	}

	public void setBaseAttributeValue(List<Long> baseAttributeValue) {
		this.baseAttributeValue = baseAttributeValue;
	}


}