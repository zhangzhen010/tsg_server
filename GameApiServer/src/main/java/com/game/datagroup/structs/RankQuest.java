package com.game.datagroup.structs;

/**
 * 排行榜完成玩家数据
 * 
 * @author zhangzhen
 * @time 2020年3月15日
 */
public class RankQuest {

	/**
	 * 对应任务配置id
	 */
	private int questId;
	/**
	 * 玩家唯一id
	 */
	private long playerId;
	/**
	 * 完成时间
	 */
	private long time;

	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
