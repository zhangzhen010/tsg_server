package com.game.mail.structs;

/**
 * 邮件id
 * 
 * @author zhangzhen
 * @time 2023年3月22日
 */
public enum MailId {

	/**
	 * 月卡1邮件
	 */
	YUEKA9999(9999),
	/**
	 * 月卡2邮件
	 */
	YUEKA9998(9998),

	/**
	 * 创角邮件
	 */
	ID1(1),
	/**
	 * 背包已满邮件
	 */
	ID2(2),

	;

	private Integer id;

	private MailId(Integer id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
