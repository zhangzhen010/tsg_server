package com.game.player.structs;

/**
 * 一条聊天数据
 * 
 * @author zhangzhen
 * @time 2020年3月18日
 */
public class ChatData {

	/**
	 * 角色id
	 */
	private long playerId;
	/**
	 * 聊天频道唯一id，私聊=对方角色id
	 */
	private long channelId;
	/**
	 * 聊天频道id，本来应该使用上面的channelId，但是客户端没时间修改，这里服务器临时使用这个来处理
	 */
	private int cid;
	/**
	 * 聊天频道类型（0=私聊 1=世界聊天 2=本地聊天 3=公会聊天）
	 */
	private int channelType;
	/**
	 * 角色名字
	 */
	private String playerName;
	/**
	 * 服务器id
	 */
	private int serverId;
	/**
	 * to服务器id
	 */
	private int toServerId;
	/**
	 * 角色头像
	 */
	private String head;
	/**
	 * 头像边框
	 */
	private String headBorder;
	/**
	 * 发言内容
	 */
	private String content;
	/**
	 * 发言人等级
	 */
	private int lv;
	/**
	 * 时间戳
	 */
	private long time;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
		this.channelType = channelType;
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

	public int getToServerId() {
		return toServerId;
	}

	public void setToServerId(int toServerId) {
		this.toServerId = toServerId;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getHeadBorder() {
		return headBorder;
	}

	public void setHeadBorder(String headBorder) {
		this.headBorder = headBorder;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

}
