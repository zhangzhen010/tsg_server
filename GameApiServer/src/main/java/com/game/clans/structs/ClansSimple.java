package com.game.clans.structs;

public class ClansSimple {

	/**
	 * 公会id
	 */
	private long clansId;
	/**
	 * 公会图标
	 */
	private String icon;
	/**
	 * 公会名称
	 */
	private String clansName;
	/**
	 * 公会宣言
	 */
	private String notice = "";
	/**
	 * 公会等级
	 */
	private int lv;
	/**
	 * 公会当前经验
	 */
	private int exp;
	/**
	 * 会长的id
	 */
	private long masterId;
	/**
	 * 成员数量
	 */
	private int memberNum;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 公会活跃值
	 */
	private int active;
	/**
	 * 今日获取活跃（用于redis排行推荐公会列表）
	 */
	private int todayActive;

	public long getClansId() {
		return clansId;
	}

	public void setClansId(long clansId) {
		this.clansId = clansId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getClansName() {
		return clansName;
	}

	public void setClansName(String clansName) {
		this.clansName = clansName;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getTodayActive() {
		return todayActive;
	}

	public void setTodayActive(int todayActive) {
		this.todayActive = todayActive;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

}
