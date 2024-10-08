package com.game.clans.structs;

/**
 * 公会任务
 * 
 * @author zhangzhen
 * @time 2020年12月25日
 */
public class ClansQuest {

	/**
	 * 公会任务唯一id（因为同一个公会任务可同时存在）
	 */
	private long id;
	/**
	 * 派遣任务配置id
	 */
	private int configId;
	/**
	 * 派遣结束时间
	 */
	private long endTime;
	/**
	 * 任务装填 0=未接取 1=已接取 2=已领取
	 */
	private int state;
	/**
	 * 收到帮助次数
	 */
	private int helperNum;
	/**
	 * 任务所属玩家id
	 */
	private long playerId;
	/**
	 * 任务所属玩家服务器id
	 */
	private int serverId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getHelperNum() {
		return helperNum;
	}

	public void setHelperNum(int helperNum) {
		this.helperNum = helperNum;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

}
