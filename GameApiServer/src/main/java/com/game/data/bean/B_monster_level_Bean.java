package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * monster_level Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "monster_level")
public class B_monster_level_Bean {
	/**
	 * 等级
	 */
	@Id
	private Integer id;
	/**
	 * 属性类型
	 */
	private List<Integer> baseAttributeType = new ArrayList<>();
	/**
	 * 属性值
	 */
	private List<Long> baseAttributeValue = new ArrayList<>();
	/**
	 * 携带幸存者最大数量
	 */
	private Integer suivivorCount;
	/**
	 * 携带宠物最大数量
	 */
	private Integer petCount;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getBaseAttributeType() {
		return this.baseAttributeType;
	}

	public void setBaseAttributeType(List<Integer> baseAttributeType) {
		this.baseAttributeType = baseAttributeType;
	}

	public List<Long> getBaseAttributeValue() {
		return this.baseAttributeValue;
	}

	public void setBaseAttributeValue(List<Long> baseAttributeValue) {
		this.baseAttributeValue = baseAttributeValue;
	}

	public Integer getSuivivorCount() {
		return this.suivivorCount;
	}

	public void setSuivivorCount(Integer suivivorCount) {
		this.suivivorCount = suivivorCount;
	}

	public Integer getPetCount() {
		return this.petCount;
	}

	public void setPetCount(Integer petCount) {
		this.petCount = petCount;
	}


}