package com.game.friend.structs;

import java.util.ArrayList;
import java.util.List;

/**
 * 跨服好友数据
 * 
 * @author zhangzhen
 * @time 2023年4月27日
 */
public class FriendPublicData {

	/**
	 * 玩家唯一id
	 */
	private long playerId;
	/**
	 * 借来的佣兵数量
	 */
	private int helperNum;
	/**
	 * 上一次佣兵周刷新时间（按周刷新）
	 */
	private long weekLastResetTime;
	/**
	 * 好友列表
	 */
	private List<Friend> friendList = new ArrayList<>();
	/**
	 * 申请列表 value=playerId
	 */
	private List<Friend> applyList = new ArrayList<>();
	/**
	 * 黑名单 value=playerId
	 */
	private List<Friend> blackList = new ArrayList<>();

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getHelperNum() {
		return helperNum;
	}

	public void setHelperNum(int helperNum) {
		this.helperNum = helperNum;
	}

	public long getWeekLastResetTime() {
		return weekLastResetTime;
	}

	public void setWeekLastResetTime(long weekLastResetTime) {
		this.weekLastResetTime = weekLastResetTime;
	}

	public List<Friend> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<Friend> friendList) {
		this.friendList = friendList;
	}

	public List<Friend> getApplyList() {
		return applyList;
	}

	public void setApplyList(List<Friend> applyList) {
		this.applyList = applyList;
	}

	public List<Friend> getBlackList() {
		return blackList;
	}

	public void setBlackList(List<Friend> blackList) {
		this.blackList = blackList;
	}

}
