package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * skill_buff Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "skill_buff")
public class B_skill_buff_Bean {
	/**
	 * buffid
	 */
	@Id
	private Integer id;
	/**
	 * buff组id
	 */
	private Integer groupId;
	/**
	 * buff编号（同一种buff的编号应该是一样的）
	 */
	private Integer number;
	/**
	 * 触发类型
	 */
	private Integer triggerType;
	/**
	 * 触发类型参数
	 */
	private List<Integer> triggerPram = new ArrayList<>();
	/**
	 * 触发条件
	 */
	private Integer triggerConditionType;
	/**
	 * 触发条件参数
	 */
	private List<Integer> triggerConditionPram = new ArrayList<>();
	/**
	 * 
buff类型

	 */
	private Integer buffType;
	/**
	 * BUFF目标
	 */
	private Integer target;
	/**
	 * 参数
	 */
	private List<Integer> param = new ArrayList<>();
	/**
	 * 获得buff后buff生效回合数
	 */
	private Integer successRound;
	/**
	 * buff图标
	 */
	private String buffIcon;
	/**
	 * 是否隐藏buff图标
	 */
	private Integer hide;
	/**
	 * 增益还是减益
	 */
	private Integer buffOrDebuff;
	/**
	 * 持续回合数
	 */
	private Integer buffTime;
	/**
	 * 回合生效间隔
	 */
	private Integer buffPer;
	/**
	 * 可叠加的最大层数上限
	 */
	private Integer tierLimit;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getTriggerType() {
		return this.triggerType;
	}

	public void setTriggerType(Integer triggerType) {
		this.triggerType = triggerType;
	}

	public List<Integer> getTriggerPram() {
		return this.triggerPram;
	}

	public void setTriggerPram(List<Integer> triggerPram) {
		this.triggerPram = triggerPram;
	}

	public Integer getTriggerConditionType() {
		return this.triggerConditionType;
	}

	public void setTriggerConditionType(Integer triggerConditionType) {
		this.triggerConditionType = triggerConditionType;
	}

	public List<Integer> getTriggerConditionPram() {
		return this.triggerConditionPram;
	}

	public void setTriggerConditionPram(List<Integer> triggerConditionPram) {
		this.triggerConditionPram = triggerConditionPram;
	}

	public Integer getBuffType() {
		return this.buffType;
	}

	public void setBuffType(Integer buffType) {
		this.buffType = buffType;
	}

	public Integer getTarget() {
		return this.target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public List<Integer> getParam() {
		return this.param;
	}

	public void setParam(List<Integer> param) {
		this.param = param;
	}

	public Integer getSuccessRound() {
		return this.successRound;
	}

	public void setSuccessRound(Integer successRound) {
		this.successRound = successRound;
	}

	public String getBuffIcon() {
		return this.buffIcon;
	}

	public void setBuffIcon(String buffIcon) {
		this.buffIcon = buffIcon;
	}

	public Integer getHide() {
		return this.hide;
	}

	public void setHide(Integer hide) {
		this.hide = hide;
	}

	public Integer getBuffOrDebuff() {
		return this.buffOrDebuff;
	}

	public void setBuffOrDebuff(Integer buffOrDebuff) {
		this.buffOrDebuff = buffOrDebuff;
	}

	public Integer getBuffTime() {
		return this.buffTime;
	}

	public void setBuffTime(Integer buffTime) {
		this.buffTime = buffTime;
	}

	public Integer getBuffPer() {
		return this.buffPer;
	}

	public void setBuffPer(Integer buffPer) {
		this.buffPer = buffPer;
	}

	public Integer getTierLimit() {
		return this.tierLimit;
	}

	public void setTierLimit(Integer tierLimit) {
		this.tierLimit = tierLimit;
	}


}