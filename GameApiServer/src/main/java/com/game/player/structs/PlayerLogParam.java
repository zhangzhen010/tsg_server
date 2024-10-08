package com.game.player.structs;

import org.springframework.data.annotation.Transient;

import java.util.List;

public class PlayerLogParam {
	/**
	 * 客户端传来的日志相关参数
	 */
	@Transient
	private transient List<String> playerLogParamsList;
	/**
	 * 客户端版本号
	 */
	@Transient
	private transient String clientVer = "";
	/**
	 * 登录时好友数量
	 */
	@Transient
	private transient int friendNum;
	/**
	 * 账号下角色数量
	 */
	@Transient
	private transient int userPlayerNum;
	/**
	 * 日志部分热点公共属性是否发生变化
	 */
	@Transient
	private transient boolean logPublicChange;

	public List<String> getPlayerLogParamsList() {
		return playerLogParamsList;
	}

	public void setPlayerLogParamsList(List<String> playerLogParamsList) {
		this.playerLogParamsList = playerLogParamsList;
	}

	public String getClientVer() {
		return clientVer;
	}

	public void setClientVer(String clientVer) {
		this.clientVer = clientVer;
	}

	public int getFriendNum() {
		return friendNum;
	}

	public void setFriendNum(int friendNum) {
		this.friendNum = friendNum;
	}

	public int getUserPlayerNum() {
		return userPlayerNum;
	}

	public void setUserPlayerNum(int userPlayerNum) {
		this.userPlayerNum = userPlayerNum;
	}

	public boolean isLogPublicChange() {
		return logPublicChange;
	}

	public void setLogPublicChange(boolean logPublicChange) {
		this.logPublicChange = logPublicChange;
	}

}
