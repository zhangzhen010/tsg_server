package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * smelter_level Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "smelter_level")
public class B_smelter_level_Bean {
	/**
	 * 等级id
	 */
	@Id
	private Integer id;
	/**
	 * 升级时间
	 */
	private Integer timeUp;
	/**
	 * 加速道具id
	 */
	private Integer accelerator;
	/**
	 * 自动模式解锁消耗次数
	 */
	private List<Integer> autoTimes = new ArrayList<>();
	/**
	 * 解锁自动拾取解锁1未解锁0
	 */
	private List<Integer> autoCollect = new ArrayList<>();
	/**
	 * 单个加速时间
	 */
	private Integer timeAccelerator;
	/**
	 * 开箱子对应随机表loot
	 */
	private Integer loot;
	/**
	 * 升级金币消耗
	 */
	private List<Integer> orderGoldCost = new ArrayList<>();
	/**
	 * 奖励道具
	 */
	private List<Integer> rewardItem = new ArrayList<>();
	/**
	 * 奖励数量
	 */
	private List<Integer> rewardCount = new ArrayList<>();
	/**
	 * 额外掉落展示
	 */
	private Integer extraDropShow;
	/**
	 * 是否有特效
	 */
	private Boolean effectShow;
	/**
	 * 公告ID
	 */
	private Integer noticeId;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTimeUp() {
		return this.timeUp;
	}

	public void setTimeUp(Integer timeUp) {
		this.timeUp = timeUp;
	}

	public Integer getAccelerator() {
		return this.accelerator;
	}

	public void setAccelerator(Integer accelerator) {
		this.accelerator = accelerator;
	}

	public List<Integer> getAutoTimes() {
		return this.autoTimes;
	}

	public void setAutoTimes(List<Integer> autoTimes) {
		this.autoTimes = autoTimes;
	}

	public List<Integer> getAutoCollect() {
		return this.autoCollect;
	}

	public void setAutoCollect(List<Integer> autoCollect) {
		this.autoCollect = autoCollect;
	}

	public Integer getTimeAccelerator() {
		return this.timeAccelerator;
	}

	public void setTimeAccelerator(Integer timeAccelerator) {
		this.timeAccelerator = timeAccelerator;
	}

	public Integer getLoot() {
		return this.loot;
	}

	public void setLoot(Integer loot) {
		this.loot = loot;
	}

	public List<Integer> getOrderGoldCost() {
		return this.orderGoldCost;
	}

	public void setOrderGoldCost(List<Integer> orderGoldCost) {
		this.orderGoldCost = orderGoldCost;
	}

	public List<Integer> getRewardItem() {
		return this.rewardItem;
	}

	public void setRewardItem(List<Integer> rewardItem) {
		this.rewardItem = rewardItem;
	}

	public List<Integer> getRewardCount() {
		return this.rewardCount;
	}

	public void setRewardCount(List<Integer> rewardCount) {
		this.rewardCount = rewardCount;
	}

	public Integer getExtraDropShow() {
		return this.extraDropShow;
	}

	public void setExtraDropShow(Integer extraDropShow) {
		this.extraDropShow = extraDropShow;
	}

	public Boolean getEffectShow() {
		return this.effectShow;
	}

	public void setEffectShow(Boolean effectShow) {
		this.effectShow = effectShow;
	}

	public Integer getNoticeId() {
		return this.noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}


}