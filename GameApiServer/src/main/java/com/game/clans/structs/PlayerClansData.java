package com.game.clans.structs;

/**
 * 玩家跨服公会数据
 * 
 * @author zhangzhen
 * @time 2020年4月10日
 */
public class PlayerClansData {

	/**
	 * 玩家唯一id
	 */
	private long playerId;
	/**
	 * 公会id，0=没有加入公会
	 */
	private long clansId;
	/**
	 * 公会名字
	 */
	private String clansName = "";
	/**
	 * 公会职位 0=未加入公会（ClansJobType）
	 */
	private int job;
	/**
	 * 公会等级
	 */
	private int lv;
	/**
	 * 角色上一次离开公会时间+1天毫秒数时间（可以加入公会时间）
	 */
	private long time;
	/**
	 * 角色加入公会时间
	 */
	private long joinTime;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public long getClansId() {
		return clansId;
	}

	public void setClansId(long clansId) {
		this.clansId = clansId;
	}

	public String getClansName() {
		return clansName;
	}

	public void setClansName(String clansName) {
		this.clansName = clansName;
	}

	public int getJob() {
		return job;
	}

	public void setJob(int job) {
		this.job = job;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

}
