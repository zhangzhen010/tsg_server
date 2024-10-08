package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * pet_pool Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "pet_pool")
public class B_pet_pool_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 免费时间：秒
	 */
	private Integer refreshFreeTime;
	/**
	 * 宠物抽奖池
	 */
	private Integer pool;
	/**
	 * 品质概率显示池
	 */
	private List<Integer> lootShowQuality = new ArrayList<>();
	/**
	 * 概率显示
	 */
	private List<Integer> lootShowValue = new ArrayList<>();
	/**
	 * 刷新消耗
	 */
	private List<Integer> refreshCostItem = new ArrayList<>();
	/**
	 * 每次保底必出
	 */
	private Integer poolPity;
	/**
	 * 保底次数（共享）
	 */
	private Integer poolPityTime;
	/**
	 * 神话保底次数（共享）
	 */
	private Integer poolBdTime;
	/**
	 * 每次随机出几个
	 */
	private Integer lootValue;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRefreshFreeTime() {
		return this.refreshFreeTime;
	}

	public void setRefreshFreeTime(Integer refreshFreeTime) {
		this.refreshFreeTime = refreshFreeTime;
	}

	public Integer getPool() {
		return this.pool;
	}

	public void setPool(Integer pool) {
		this.pool = pool;
	}

	public List<Integer> getLootShowQuality() {
		return this.lootShowQuality;
	}

	public void setLootShowQuality(List<Integer> lootShowQuality) {
		this.lootShowQuality = lootShowQuality;
	}

	public List<Integer> getLootShowValue() {
		return this.lootShowValue;
	}

	public void setLootShowValue(List<Integer> lootShowValue) {
		this.lootShowValue = lootShowValue;
	}

	public List<Integer> getRefreshCostItem() {
		return this.refreshCostItem;
	}

	public void setRefreshCostItem(List<Integer> refreshCostItem) {
		this.refreshCostItem = refreshCostItem;
	}

	public Integer getPoolPity() {
		return this.poolPity;
	}

	public void setPoolPity(Integer poolPity) {
		this.poolPity = poolPity;
	}

	public Integer getPoolPityTime() {
		return this.poolPityTime;
	}

	public void setPoolPityTime(Integer poolPityTime) {
		this.poolPityTime = poolPityTime;
	}

	public Integer getPoolBdTime() {
		return this.poolBdTime;
	}

	public void setPoolBdTime(Integer poolBdTime) {
		this.poolBdTime = poolBdTime;
	}

	public Integer getLootValue() {
		return this.lootValue;
	}

	public void setLootValue(Integer lootValue) {
		this.lootValue = lootValue;
	}


}