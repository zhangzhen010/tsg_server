package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * quest Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 01:00
 */
@Document(collection = "quest")
public class B_quest_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 下一个任务
	 */
	private Integer nextId;
	/**
	 * 任务类型
	 */
	private Integer questType;
	/**
	 * 任务重置类型
	 */
	private Integer questResetType;
	/**
	 * 任务描述
	 */
	private String describe;
	/**
	 * 跳转链接
	 */
	private String skipUrl;
	/**
	 * 任务所属平台（前端验证是否绑定）
	 */
	private String platform;
	/**
	 * 任务目标
	 */
	private List<Integer> targetIdList = new ArrayList<>();
	/**
	 * 任务前置条件（需要discord身分组名称）
	 */
	private String discordRoleName;
	/**
	 * 奖励库id
	 */
	private Integer awardId;
	/**
	 * 关联活动配置id
	 */
	private Integer activityId;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNextId() {
		return this.nextId;
	}

	public void setNextId(Integer nextId) {
		this.nextId = nextId;
	}

	public Integer getQuestType() {
		return this.questType;
	}

	public void setQuestType(Integer questType) {
		this.questType = questType;
	}

	public Integer getQuestResetType() {
		return this.questResetType;
	}

	public void setQuestResetType(Integer questResetType) {
		this.questResetType = questResetType;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getSkipUrl() {
		return this.skipUrl;
	}

	public void setSkipUrl(String skipUrl) {
		this.skipUrl = skipUrl;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public List<Integer> getTargetIdList() {
		return this.targetIdList;
	}

	public void setTargetIdList(List<Integer> targetIdList) {
		this.targetIdList = targetIdList;
	}

	public String getDiscordRoleName() {
		return this.discordRoleName;
	}

	public void setDiscordRoleName(String discordRoleName) {
		this.discordRoleName = discordRoleName;
	}

	public Integer getAwardId() {
		return this.awardId;
	}

	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}

	public Integer getActivityId() {
		return this.activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}


}