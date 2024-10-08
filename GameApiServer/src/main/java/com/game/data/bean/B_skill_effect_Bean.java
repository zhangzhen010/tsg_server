package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * skill_effect Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "skill_effect")
public class B_skill_effect_Bean {
	/**
	 * 效果id
	 */
	@Id
	private Integer id;
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
	 * 全局触发次数
	 */
	private Integer triggerTime;
	/**
	 * 全局cd
	 */
	private Integer triggerCd;
	/**
	 * 效果触发概率
	 */
	private Integer triggerRate;
	/**
	 * 效果目标
	 */
	private Integer target;
	/**
	 * 效果类型
	 */
	private Integer effectType;
	/**
	 * 效果参数
	 */
	private List<Integer> effectPram = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getTriggerTime() {
		return this.triggerTime;
	}

	public void setTriggerTime(Integer triggerTime) {
		this.triggerTime = triggerTime;
	}

	public Integer getTriggerCd() {
		return this.triggerCd;
	}

	public void setTriggerCd(Integer triggerCd) {
		this.triggerCd = triggerCd;
	}

	public Integer getTriggerRate() {
		return this.triggerRate;
	}

	public void setTriggerRate(Integer triggerRate) {
		this.triggerRate = triggerRate;
	}

	public Integer getTarget() {
		return this.target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public Integer getEffectType() {
		return this.effectType;
	}

	public void setEffectType(Integer effectType) {
		this.effectType = effectType;
	}

	public List<Integer> getEffectPram() {
		return this.effectPram;
	}

	public void setEffectPram(List<Integer> effectPram) {
		this.effectPram = effectPram;
	}


}