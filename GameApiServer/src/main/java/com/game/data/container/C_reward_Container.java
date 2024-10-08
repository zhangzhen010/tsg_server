package com.game.data.container;

import com.alibaba.fastjson2.JSONArray;
import com.game.data.bean.B_reward_Bean;
import com.game.data.structs.IContainer;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzhen
 *
 * @version 1.0.0
 * B_reward_Bean数据容器
 */
@Component
@Log4j2
@Getter
@Setter
public class C_reward_Container implements IContainer {

	private List<B_reward_Bean> list;

	private HashMap<Integer, B_reward_Bean> map = new HashMap<>();
	/**
	 * 奖励库权重之和map key=奖励id，value=奖励内容（B_reward_Bean表）
	 */
	private Map<Integer, Integer> rewardWeightSumWeightMap;
	/**
	 * 奖励库随机数量权重之和map key=奖励id，value=奖励内容（B_reward_Bean表）
	 */
	private Map<Integer, Integer> rewardWeightNumSumWeightMap;
	/**
	 * 奖励库id权重之和map key=奖励id，value=奖励内容（B_reward_Bean表）
	 */
	private Map<Integer, Integer> rewardIdWeightSumWeightMap;

	private @Resource @Qualifier("dataMongo") MongoTemplate mongoTemplate;

	public void load(){
		list = mongoTemplate.findAll(B_reward_Bean.class);
		for (B_reward_Bean bean : list) {
			map.put(bean.getId(), bean);
		}

		init();
	}

	/**
	 * 配置是否为空
	 */
	public boolean isEmpty(){
		return list.isEmpty();
	}

	/**
	 * 初始化(实现加载完成后数据处理)
	 */
	public void init() {
		updateRewardConfig();
	}

	/**
	 * 更新奖励库配置
	 */
	public void updateRewardConfig() {
		int id = 0;
		try {
			// 奖励库权重之和map key=奖励id，value=奖励内容（B_reward_Bean表）
			Map<Integer, Integer> tempRewardWeightSumWeightMap = new HashMap<>();
			// 奖励库随机数量权重之和map key=奖励id，value=奖励内容（B_reward_Bean表）
			Map<Integer, Integer> tempRewardWeightNumSumWeightMap = new HashMap<>();
			// 奖励库id权重之和map key=奖励id，value=奖励内容（B_reward_Bean表）
			Map<Integer, Integer> tempRewardIdWeightSumWeightMap = new HashMap<>();
			// 奖励库id随机数量权重之和map key=奖励id，value=奖励内容（B_reward_Bean表）
			Map<Integer, Integer> tempRewardIdWeightNumSumWeightMap = new HashMap<>();
			for (B_reward_Bean bean : list) {
				id = bean.getId();
				// 权重奖励
				if (!bean.getWeightAwardList().isEmpty()) {
					// 计算权重之和
					int weightSum = 0;
					for (int i = 0, len = bean.getWeightAwardList().size(); i < len; i++) {
						JSONArray rewardJsonArray = bean.getWeightAwardList().getJSONArray(i);
						// 根据权重之和获取总权重
						weightSum += rewardJsonArray.getIntValue(2);
					}
					tempRewardWeightSumWeightMap.put(bean.getId(), weightSum);
				}
				// 权重数量奖励
				if (!bean.getWeightNumAwardList().isEmpty()) {
					// 计算权重之和
					int weightSum = 0;
					for (int i = 0, len = bean.getWeightNumAwardList().size(); i < len; i++) {
						JSONArray rewardJsonArray = bean.getWeightNumAwardList().getJSONArray(i);
						// 根据权重之和获取总权重
						weightSum += rewardJsonArray.getIntValue(3);
					}
					tempRewardWeightNumSumWeightMap.put(bean.getId(), weightSum);
				}
				// 权重id奖励
				if (!bean.getWeightAwardIdList().isEmpty()) {
					// 计算权重之和
					int weightSum = 0;
					for (int i = 0, len = bean.getWeightAwardIdList().size(); i < len; i++) {
						JSONArray rewardJsonArray = bean.getWeightAwardIdList().getJSONArray(i);
						// 根据权重之和获取总权重
						weightSum += rewardJsonArray.getIntValue(2);
					}
					tempRewardIdWeightSumWeightMap.put(bean.getId(), weightSum);
				}
			}
			rewardWeightSumWeightMap = tempRewardWeightSumWeightMap;
			rewardWeightNumSumWeightMap = tempRewardWeightNumSumWeightMap;
			rewardIdWeightSumWeightMap = tempRewardIdWeightSumWeightMap;
			log.info("更新奖励库配置权重奖励size=" + tempRewardWeightSumWeightMap.size());
			log.info("更新奖励库配置权重数量奖励size=" + tempRewardWeightNumSumWeightMap.size());
			log.info("更新奖励库配置权重奖励库id数量奖励size=" + tempRewardIdWeightSumWeightMap.size());
		} catch (Exception e) {
			log.error("更新奖励库配置,配置id=" + id, e);
		}
	}

}