package com.game.mail.structs;

/**
 * 邮件状态
 * 
 * @author zhangzhen
 * @time 2020年3月11日
 */
public enum MailStateType {

	/**
	 * 未读
	 */
	UNREAD(0),
	/**
	 * 已读
	 */
	READ(1),
	/**
	 * 未领取
	 */
	UNGET(0),
	/**
	 * 已领取
	 */
	GET(1),;

	private int type;

	private MailStateType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
