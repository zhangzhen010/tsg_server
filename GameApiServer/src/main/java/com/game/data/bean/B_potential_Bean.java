package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * potential Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "potential")
public class B_potential_Bean {
	/**
	 * 潜能id
	 */
	@Id
	private Integer id;
	/**
	 * 套装技能组id
	 */
	private Integer skillId;
	/**
	 * 品质
	 */
	private Integer quality;
	/**
	 * 潜能位置
	 */
	private List<Integer> position = new ArrayList<>();
	/**
	 * 卖出获得的道具ID
	 */
	private List<Integer> sellItem = new ArrayList<>();
	/**
	 * 主词条属性
	 */
	private Integer attribute1;
	/**
	 * 属性值下限（万分比）
	 */
	private Integer attributeValue1Min;
	/**
	 * 属性值上限（万分比）
	 */
	private Integer attributeValue1Max;
	/**
	 * 副词条属性
	 */
	private List<Integer> attribute2 = new ArrayList<>();
	/**
	 * 属性值下限（万分比）
	 */
	private List<Integer> attributeValue2Min = new ArrayList<>();
	/**
	 * 属性值上限（万分比）
	 */
	private List<Integer> attributeValue2Max = new ArrayList<>();
	/**
	 * 副词条权重
	 */
	private List<Integer> attribute2Weight = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSkillId() {
		return this.skillId;
	}

	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}

	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public List<Integer> getPosition() {
		return this.position;
	}

	public void setPosition(List<Integer> position) {
		this.position = position;
	}

	public List<Integer> getSellItem() {
		return this.sellItem;
	}

	public void setSellItem(List<Integer> sellItem) {
		this.sellItem = sellItem;
	}

	public Integer getAttribute1() {
		return this.attribute1;
	}

	public void setAttribute1(Integer attribute1) {
		this.attribute1 = attribute1;
	}

	public Integer getAttributeValue1Min() {
		return this.attributeValue1Min;
	}

	public void setAttributeValue1Min(Integer attributeValue1Min) {
		this.attributeValue1Min = attributeValue1Min;
	}

	public Integer getAttributeValue1Max() {
		return this.attributeValue1Max;
	}

	public void setAttributeValue1Max(Integer attributeValue1Max) {
		this.attributeValue1Max = attributeValue1Max;
	}

	public List<Integer> getAttribute2() {
		return this.attribute2;
	}

	public void setAttribute2(List<Integer> attribute2) {
		this.attribute2 = attribute2;
	}

	public List<Integer> getAttributeValue2Min() {
		return this.attributeValue2Min;
	}

	public void setAttributeValue2Min(List<Integer> attributeValue2Min) {
		this.attributeValue2Min = attributeValue2Min;
	}

	public List<Integer> getAttributeValue2Max() {
		return this.attributeValue2Max;
	}

	public void setAttributeValue2Max(List<Integer> attributeValue2Max) {
		this.attributeValue2Max = attributeValue2Max;
	}

	public List<Integer> getAttribute2Weight() {
		return this.attribute2Weight;
	}

	public void setAttribute2Weight(List<Integer> attribute2Weight) {
		this.attribute2Weight = attribute2Weight;
	}


}