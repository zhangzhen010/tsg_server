package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * aerocraft_stage Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "aerocraft_stage")
public class B_aerocraft_stage_Bean {
	/**
	 * 阶段
	 */
	@Id
	private Integer id;
	/**
	 * 进阶消耗道具
	 */
	private List<Integer> stageItemid = new ArrayList<>();
	/**
	 * 专属属性值
	 */
	private Integer exclusiveAttrValue;
	/**
	 * 默认属性值
	 */
	private Integer defaultAttrValue;
	/**
	 * 品质，用于显示底图
	 */
	private Integer quality;
	/**
	 * 初始属性值（攻血防）
	 */
	private List<Integer> startAttrValue = new ArrayList<>();
	/**
	 * 每阶最大等级
	 */
	private Integer maxLevel;
	/**
	 * 升级消耗道具
	 */
	private Integer levelItem;
	/**
	 * 每级所需经验
	 */
	private Integer levelExp;
	/**
	 * 每级增加属性
	 */
	private List<Integer> levelupAttrValue = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getStageItemid() {
		return this.stageItemid;
	}

	public void setStageItemid(List<Integer> stageItemid) {
		this.stageItemid = stageItemid;
	}

	public Integer getExclusiveAttrValue() {
		return this.exclusiveAttrValue;
	}

	public void setExclusiveAttrValue(Integer exclusiveAttrValue) {
		this.exclusiveAttrValue = exclusiveAttrValue;
	}

	public Integer getDefaultAttrValue() {
		return this.defaultAttrValue;
	}

	public void setDefaultAttrValue(Integer defaultAttrValue) {
		this.defaultAttrValue = defaultAttrValue;
	}

	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public List<Integer> getStartAttrValue() {
		return this.startAttrValue;
	}

	public void setStartAttrValue(List<Integer> startAttrValue) {
		this.startAttrValue = startAttrValue;
	}

	public Integer getMaxLevel() {
		return this.maxLevel;
	}

	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
	}

	public Integer getLevelItem() {
		return this.levelItem;
	}

	public void setLevelItem(Integer levelItem) {
		this.levelItem = levelItem;
	}

	public Integer getLevelExp() {
		return this.levelExp;
	}

	public void setLevelExp(Integer levelExp) {
		this.levelExp = levelExp;
	}

	public List<Integer> getLevelupAttrValue() {
		return this.levelupAttrValue;
	}

	public void setLevelupAttrValue(List<Integer> levelupAttrValue) {
		this.levelupAttrValue = levelupAttrValue;
	}


}