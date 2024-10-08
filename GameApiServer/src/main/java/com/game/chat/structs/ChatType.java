package com.game.chat.structs;

/**
 * 聊天类型
 * 
 * @author zhangzhen
 * @time 2020年3月18日
 */
public enum ChatType {

	/**
	 * 0=私聊
	 */
	PRIVATE(0),
	/**
	 * 1=跨服
	 */
	WORLD(1),
	/**
	 * 2=语言
	 */
	LANGUAGE(2),
	/**
	 * 3=工会
	 */
	CLANS(3),
	/**
	 * 本服聊天
	 */
	LOCAL(4),

	;

	private int value;

	ChatType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
