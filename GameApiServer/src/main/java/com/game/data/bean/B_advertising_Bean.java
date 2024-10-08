package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * advertising Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "advertising")
public class B_advertising_Bean {
	/**
	 * 类型
	 */
	@Id
	private Integer id;
	/**
	 * 每日次数
	 */
	private Integer count;
	/**
	 * 值
	 */
	private Integer value;
	/**
	 * 冷却时间(秒）
	 */
	private Integer coldDownTime;
	/**
	 * 广告位ID
	 */
	private String key;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getValue() {
		return this.value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getColdDownTime() {
		return this.coldDownTime;
	}

	public void setColdDownTime(Integer coldDownTime) {
		this.coldDownTime = coldDownTime;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}


}