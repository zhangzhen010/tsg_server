package com.game.clans.structs;

import java.util.ArrayList;
import java.util.List;

/**
 * 公会成员
 * 
 * @author zhangzhen
 * @time 2020年4月9日
 */
public class Member {
	/**
	 * 成员id
	 */
	private long playerId;
	/**
	 * 成员名字
	 */
	private String playerName = "";
	/**
	 * 成员服务器id
	 */
	private int serverId;
	/**
	 * 成员职位
	 */
	private int job;
	/**
	 * 7日活跃值
	 */
	private int active;
	/**
	 * 7日活跃值列表
	 */
	private List<MemberActiveData> activeList = new ArrayList<>();


	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getJob() {
		return job;
	}

	public void setJob(int job) {
		this.job = job;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public List<MemberActiveData> getActiveList() {
		return activeList;
	}

	public void setActiveList(List<MemberActiveData> activeList) {
		this.activeList = activeList;
	}

}
