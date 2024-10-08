package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * arena_match_range Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "arena_match_range")
public class B_arena_match_range_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 分数
	 */
	private List<Integer> arenaScore = new ArrayList<>();
	/**
	 * 范围1(1名)
	 */
	private List<Integer> range1 = new ArrayList<>();
	/**
	 * 范围2(1名)
	 */
	private List<Integer> range2 = new ArrayList<>();
	/**
	 * 范围3(1名)
	 */
	private List<Integer> range3 = new ArrayList<>();
	/**
	 * 范围4(1名)
	 */
	private List<Integer> range4 = new ArrayList<>();
	/**
	 * 范围3(1名)
	 */
	private List<Integer> range5 = new ArrayList<>();
	/**
	 * 保底范围
	 */
	private List<Integer> fixRange = new ArrayList<>();
	/**
	 * 战胜奖励
	 */
	private Integer reward;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getArenaScore() {
		return this.arenaScore;
	}

	public void setArenaScore(List<Integer> arenaScore) {
		this.arenaScore = arenaScore;
	}

	public List<Integer> getRange1() {
		return this.range1;
	}

	public void setRange1(List<Integer> range1) {
		this.range1 = range1;
	}

	public List<Integer> getRange2() {
		return this.range2;
	}

	public void setRange2(List<Integer> range2) {
		this.range2 = range2;
	}

	public List<Integer> getRange3() {
		return this.range3;
	}

	public void setRange3(List<Integer> range3) {
		this.range3 = range3;
	}

	public List<Integer> getRange4() {
		return this.range4;
	}

	public void setRange4(List<Integer> range4) {
		this.range4 = range4;
	}

	public List<Integer> getRange5() {
		return this.range5;
	}

	public void setRange5(List<Integer> range5) {
		this.range5 = range5;
	}

	public List<Integer> getFixRange() {
		return this.fixRange;
	}

	public void setFixRange(List<Integer> fixRange) {
		this.fixRange = fixRange;
	}

	public Integer getReward() {
		return this.reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}


}