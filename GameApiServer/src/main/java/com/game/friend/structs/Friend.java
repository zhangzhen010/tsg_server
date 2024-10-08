package com.game.friend.structs;

/**
 * 好友
 * 
 * @author zhangzhen
 * @time 2023年4月27日
 */
public class Friend {

	/**
	 * 角色唯一id
	 */
	private long playerId;
	/**
	 * 服务器id
	 */
	private int serverId;

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
