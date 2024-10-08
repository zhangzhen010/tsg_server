package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * pet_passive Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "pet_passive")
public class B_pet_passive_Bean {
	/**
	 * 被动技能id
	 */
	@Id
	private Integer id;
	/**
	 * 技能名称
	 */
	private String name;
	/**
	 * 品质
	 */
	private Integer quality;
	/**
	 * 排序
	 */
	private Integer order;
	/**
	 * 描述
	 */
	private String des;
	/**
	 * 加成属性类型
	 */
	private Integer effects;
	/**
	 * 加成属性值（每一级加成数值）
	 */
	private Integer effectValues;
	/**
	 * 最大等级
	 */
	private Integer levelmax;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Integer getEffects() {
		return this.effects;
	}

	public void setEffects(Integer effects) {
		this.effects = effects;
	}

	public Integer getEffectValues() {
		return this.effectValues;
	}

	public void setEffectValues(Integer effectValues) {
		this.effectValues = effectValues;
	}

	public Integer getLevelmax() {
		return this.levelmax;
	}

	public void setLevelmax(Integer levelmax) {
		this.levelmax = levelmax;
	}


}