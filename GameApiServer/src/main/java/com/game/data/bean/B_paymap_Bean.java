package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * paymap Bean
 */
@Document(collection = "paymap")
public class B_paymap_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 名字
	 */
	private Integer name;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * 限购次数
	 */
	private Integer limitNum;
	/**
	 * 对应充值金额档位配置
	 */
	private Integer payId;
	/**
	 * 奖励库id
	 */
	private Integer rewardId;

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

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getLimitNum() {
		return this.limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

	public Integer getPayId() {
		return this.payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public Integer getRewardId() {
		return this.rewardId;
	}

	public void setRewardId(Integer rewardId) {
		this.rewardId = rewardId;
	}


}