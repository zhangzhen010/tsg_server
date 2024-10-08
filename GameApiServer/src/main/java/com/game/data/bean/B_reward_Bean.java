package com.game.data.bean;

import com.alibaba.fastjson2.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * reward Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "reward")
public class B_reward_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 固定奖励(物品id，物品数量)
	 */
	private JSONArray fixedAwardList;
	/**
	 * 权重奖励(奖励表id，物品数量，物品权重)
	 */
	private JSONArray weightAwardIdList;
	/**
	 * 权重奖励(物品id，物品数量，物品权重)
	 */
	private JSONArray weightAwardList;
	/**
	 * 权重数量随机奖励(物品id，物品最小数量，物品最大数量，物品权重)
	 */
	private JSONArray weightNumAwardList;
	/**
	 * 概率奖励(物品id，物品数量，物品获得概率)
	 */
	private JSONArray ratioAwardList;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public JSONArray getFixedAwardList() {
		return this.fixedAwardList;
	}

	public void setFixedAwardList(JSONArray fixedAwardList) {
		this.fixedAwardList = fixedAwardList;
	}

	public JSONArray getWeightAwardIdList() {
		return this.weightAwardIdList;
	}

	public void setWeightAwardIdList(JSONArray weightAwardIdList) {
		this.weightAwardIdList = weightAwardIdList;
	}

	public JSONArray getWeightAwardList() {
		return this.weightAwardList;
	}

	public void setWeightAwardList(JSONArray weightAwardList) {
		this.weightAwardList = weightAwardList;
	}

	public JSONArray getWeightNumAwardList() {
		return this.weightNumAwardList;
	}

	public void setWeightNumAwardList(JSONArray weightNumAwardList) {
		this.weightNumAwardList = weightNumAwardList;
	}

	public JSONArray getRatioAwardList() {
		return this.ratioAwardList;
	}

	public void setRatioAwardList(JSONArray ratioAwardList) {
		this.ratioAwardList = ratioAwardList;
	}


}