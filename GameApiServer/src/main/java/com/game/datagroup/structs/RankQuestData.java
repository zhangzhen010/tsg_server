package com.game.datagroup.structs;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 排行榜任务数据
 * 
 * @author zhangzhen
 * @time 2021年1月7日
 */
public class RankQuestData {

	/**
	 * key=任务配置id，value=达成玩家数据
	 */
	private ConcurrentHashMap<Integer, RankQuestList> questDataMap = new ConcurrentHashMap<>();

	public ConcurrentHashMap<Integer, RankQuestList> getQuestDataMap() {
		return questDataMap;
	}

	public void setQuestDataMap(ConcurrentHashMap<Integer, RankQuestList> questDataMap) {
		this.questDataMap = questDataMap;
	}

}
