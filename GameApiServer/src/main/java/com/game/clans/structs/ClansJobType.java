package com.game.clans.structs;

/**
 * 公会职位
 * 
 * @author zhangzhen
 * @time 2020年4月9日
 */
public enum ClansJobType {

	/**
	 * 普通成员
	 */
	PUTONG(1),
	/**
	 * 副会长
	 */
	FUHUIZHANG(2),
	/**
	 * 会长
	 */
	HUIZHANG(3),

	;

	private int value;

	ClansJobType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
