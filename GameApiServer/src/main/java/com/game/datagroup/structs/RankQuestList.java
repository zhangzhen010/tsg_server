package com.game.datagroup.structs;

import java.util.ArrayList;
import java.util.List;

/**
 * 排行榜任务完成列表
 * 
 * @author zhangzhen
 * @time 2021年1月7日
 */
public class RankQuestList {

	/**
	 * 完成任务记录数据
	 */
	private List<RankQuest> questList = new ArrayList<>();

	public List<RankQuest> getQuestList() {
		return questList;
	}

	public void setQuestList(List<RankQuest> questList) {
		this.questList = questList;
	}

}
