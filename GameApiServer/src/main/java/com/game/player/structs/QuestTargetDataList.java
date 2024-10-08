package com.game.player.structs;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务目标历史数据列表
 * 
 * @author zhangzhen
 * @time 2022年6月23日
 */
public class QuestTargetDataList {

	/**
	 * 任务参数值列表
	 */
	private List<QuestTargetData> questTargetList = new ArrayList<>();

	public List<QuestTargetData> getQuestTargetList() {
		return questTargetList;
	}

	public void setQuestTargetList(List<QuestTargetData> questTargetList) {
		this.questTargetList = questTargetList;
	}

}
