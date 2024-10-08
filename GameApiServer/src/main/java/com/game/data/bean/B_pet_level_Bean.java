package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * pet_level Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "pet_level")
public class B_pet_level_Bean {
	/**
	 * 等级id
	 */
	@Id
	private Integer id;
	/**
	 * 等级
	 */
	private Integer level;
	/**
	 * 品质
	 */
	private Integer quality;
	/**
	 * 升级所需要的道具、数量
	 */
	private List<Integer> levelItem = new ArrayList<>();
	/**
	 * 该等级升级的总经验数量（用于放生重置）
	 */
	private Integer levelSumValue;
	/**
	 * 当前等级属性
	 */
	private List<Integer> attributeType = new ArrayList<>();
	/**
	 * 当前等级属性值
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

	public List<Integer> getLevelItem() {
		return this.levelItem;
	}

	public void setLevelItem(List<Integer> levelItem) {
		this.levelItem = levelItem;
	}

	public Integer getLevelSumValue() {
		return this.levelSumValue;
	}

	public void setLevelSumValue(Integer levelSumValue) {
		this.levelSumValue = levelSumValue;
	}

	public List<Integer> getAttributeType() {
		return this.attributeType;
	}

	public void setAttributeType(List<Integer> attributeType) {
		this.attributeType = attributeType;
	}

	public List<Integer> getAttributeValue() {
		return this.attributeValue;
	}

	public void setAttributeValue(List<Integer> attributeValue) {
		this.attributeValue = attributeValue;
	}


}