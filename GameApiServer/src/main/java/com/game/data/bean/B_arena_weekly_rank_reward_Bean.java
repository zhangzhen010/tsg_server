package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * arena_weekly_rank_reward Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "arena_weekly_rank_reward")
public class B_arena_weekly_rank_reward_Bean {
	/**
	 * 每周奖励id
	 */
	@Id
	private Integer id;
	/**
	 * 排名区间数字显示
	 */
	private List<Integer> rankRangeShow = new ArrayList<>();
	/**
	 * 排名奖励道具
	 */
	private List<Integer> rewardCount = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getRankRangeShow() {
		return this.rankRangeShow;
	}

	public void setRankRangeShow(List<Integer> rankRangeShow) {
		this.rankRangeShow = rankRangeShow;
	}

	public List<Integer> getRewardCount() {
		return this.rewardCount;
	}

	public void setRewardCount(List<Integer> rewardCount) {
		this.rewardCount = rewardCount;
	}


}