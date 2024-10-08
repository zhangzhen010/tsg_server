package com.game.award.structs;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 一个奖励库所得到的奖励信息
 * 
 * @author zhangzhen
 * @time 2020年3月11日
 */
@Getter
@Setter
public class AwardList {

	/**
	 * 装备数量
	 */
	private int equipNum;
	/**
	 * 英雄数量
	 */
	private int heroNum;
	/**
	 * 奖励列表 奖励id，奖励数量，奖励id....
	 */
	private List<Integer> awardList = new ArrayList<>();
	/**
	 * 记录本次已参与计算的奖励库id，防止出现奖励库递归权重随机到自己，无限循环
	 */
	private Set<Integer> rewardIdSet = new HashSet<>();
}
