package com.game.clans.structs;

/**
 * 公会申请者数据
 * 
 * @author zhangzhen
 * @time 2020年4月13日
 */
public class ClansApply {

	/**
	 * 玩家唯一id
	 */
	private long playerId;
	/**
	 * 玩家服务器id
	 */
	private int serverId;
	/**
	 * 玩家名字
	 */
	private String playerName;

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

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}
