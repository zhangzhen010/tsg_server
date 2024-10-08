package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * quest_target Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 01:00
 */
@Document(collection = "quest_target")
public class B_quest_target_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 任务目标类型
	 */
	private Integer targetType;
	/**
	 * 任务目标条件1
	 */
	private Integer targetExt1;
	/**
	 * 任务目标条件2
	 */
	private Integer targetExt2;
	/**
	 * 任务目标值
	 */
	private Integer targetValue;
	/**
	 * 记录类型（false=不获取历史记录 true=获取历史记录）
	 */
	private Boolean record;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTargetType() {
		return this.targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public Integer getTargetExt1() {
		return this.targetExt1;
	}

	public void setTargetExt1(Integer targetExt1) {
		this.targetExt1 = targetExt1;
	}

	public Integer getTargetExt2() {
		return this.targetExt2;
	}

	public void setTargetExt2(Integer targetExt2) {
		this.targetExt2 = targetExt2;
	}

	public Integer getTargetValue() {
		return this.targetValue;
	}

	public void setTargetValue(Integer targetValue) {
		this.targetValue = targetValue;
	}

	public Boolean getRecord() {
		return this.record;
	}

	public void setRecord(Boolean record) {
		this.record = record;
	}


}