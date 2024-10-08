package com.game.clans.structs;

import java.util.ArrayList;
import java.util.List;

/**
 * 公会数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/6 11:56
 */
public class Clans {
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
	 * 会长上次登录时间
	 */
	private long masterTime;
	/**
	 * 下一次可发送邮件时间
	 */
	private long mailTime;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 公会审核成员类型 ClansJoinType
	 */
	private int joinType;
	/**
	 * 审核等级
	 */
	private int checkLv;
	/**
	 * 公会活跃值
	 */
	private int active;
	/**
	 * 今日获取活跃（用于redis排行推荐公会列表）
	 */
	private int todayActive;
	/**
	 * 今日已获得经验值
	 */
	private int todayExp;
	/**
	 * 是否保存
	 */
	private transient boolean save;
	/**
	 * 申请加入公会列表 value=请求者id
	 */
	private List<ClansApply> applyList = new ArrayList<>();
	/**
	 * 成员列表 key=角色id value=成员
	 */
	private List<Member> memberList = new ArrayList<>();
	/**
	 * 公会任务援助列表
	 */
	private List<ClansQuest> questList = new ArrayList<>();

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

	public long getMasterTime() {
		return masterTime;
	}

	public void setMasterTime(long masterTime) {
		this.masterTime = masterTime;
	}

	public long getMailTime() {
		return mailTime;
	}

	public void setMailTime(long mailTime) {
		this.mailTime = mailTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getJoinType() {
		return joinType;
	}

	public void setJoinType(int joinType) {
		this.joinType = joinType;
	}

	public int getCheckLv() {
		return checkLv;
	}

	public void setCheckLv(int checkLv) {
		this.checkLv = checkLv;
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

	public boolean isSave() {
		return save;
	}

	public void setSave(boolean save) {
		this.save = save;
	}

	public List<ClansApply> getApplyList() {
		return applyList;
	}

	public void setApplyList(List<ClansApply> applyList) {
		this.applyList = applyList;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}

	public List<ClansQuest> getQuestList() {
		return questList;
	}

	public void setQuestList(List<ClansQuest> questList) {
		this.questList = questList;
	}

	public int getTodayExp() {
		return todayExp;
	}

	public void setTodayExp(int todayExp) {
		this.todayExp = todayExp;
	}

}
