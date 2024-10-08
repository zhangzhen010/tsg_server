package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * pet_star Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "pet_star")
public class B_pet_star_Bean {
	/**
	 * 星级id
	 */
	@Id
	private Integer id;
	/**
	 * 星级
	 */
	private Integer level;
	/**
	 * 品质
	 */
	private Integer quality;
	/**
	 * 当前星级增加的属性
	 */
	private List<Integer> attributeType = new ArrayList<>();
	/**
	 * 升星时，增加的被动
	 */
	private Integer addType;
	/**
	 * 当前星级增加的属性值
	 */
	private List<Integer> attributeValue = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public List<Integer> getAttributeType() {
		return this.attributeType;
	}

	public void setAttributeType(List<Integer> attributeType) {
		this.attributeType = attributeType;
	}

	public Integer getAddType() {
		return this.addType;
	}

	public void setAddType(Integer addType) {
		this.addType = addType;
	}

	public List<Integer> getAttributeValue() {
		return this.attributeValue;
	}

	public void setAttributeValue(List<Integer> attributeValue) {
		this.attributeValue = attributeValue;
	}


}