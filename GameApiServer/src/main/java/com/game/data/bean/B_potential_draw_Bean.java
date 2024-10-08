package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * potential_draw Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "potential_draw")
public class B_potential_draw_Bean {
	/**
	 * 区间id
	 */
	@Id
	private Integer id;
	/**
	 * 鉴定等级下限
	 */
	private Integer rangeMin;
	/**
	 * 鉴定等级上限
	 */
	private Integer rangeMax;
	/**
	 * 品质
	 */
	private List<Integer> quality = new ArrayList<>();
	/**
	 * 概率起始值（百万分比）
	 */
	private List<Integer> init = new ArrayList<>();
	/**
	 * 每一级提升的值（百万分比）
	 */
	private List<Integer> value = new ArrayList<>();
	/**
	 * 对应奖励表id
	 */
	private List<Integer> rewardId = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRangeMin() {
		return this.rangeMin;
	}

	public void setRangeMin(Integer rangeMin) {
		this.rangeMin = rangeMin;
	}

	public Integer getRangeMax() {
		return this.rangeMax;
	}

	public void setRangeMax(Integer rangeMax) {
		this.rangeMax = rangeMax;
	}

	public List<Integer> getQuality() {
		return this.quality;
	}

	public void setQuality(List<Integer> quality) {
		this.quality = quality;
	}

	public List<Integer> getInit() {
		return this.init;
	}

	public void setInit(List<Integer> init) {
		this.init = init;
	}

	public List<Integer> getValue() {
		return this.value;
	}

	public void setValue(List<Integer> value) {
		this.value = value;
	}

	public List<Integer> getRewardId() {
		return this.rewardId;
	}

	public void setRewardId(List<Integer> rewardId) {
		this.rewardId = rewardId;
	}


}