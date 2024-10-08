package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * function_unlock Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "function_unlock")
public class B_function_unlock_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 功能名称
	 */
	private Integer name;
	/**
	 * 解锁类型
	 */
	private Integer unlockType;
	/**
	 * 解锁等级
	 */
	private Integer unlockLv;
	/**
	 * 解锁任务
	 */
	private Integer unlockTask;
	/**
	 * 开服第几天开
	 */
	private Integer unlockDay;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getName() {
		return this.name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public Integer getUnlockType() {
		return this.unlockType;
	}

	public void setUnlockType(Integer unlockType) {
		this.unlockType = unlockType;
	}

	public Integer getUnlockLv() {
		return this.unlockLv;
	}

	public void setUnlockLv(Integer unlockLv) {
		this.unlockLv = unlockLv;
	}

	public Integer getUnlockTask() {
		return this.unlockTask;
	}

	public void setUnlockTask(Integer unlockTask) {
		this.unlockTask = unlockTask;
	}

	public Integer getUnlockDay() {
		return this.unlockDay;
	}

	public void setUnlockDay(Integer unlockDay) {
		this.unlockDay = unlockDay;
	}


}