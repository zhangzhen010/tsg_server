package com.game.clans.structs;

/**
 * 公会验证加入类型
 * 
 * @author zhangzhen
 * @time 2020年5月22日
 */
public enum ClansJoinType {

	/**
	 * 无限制
	 */
	FREE(1),
	/**
	 * 审核
	 */
	CHECK(2),
	/**
	 * 禁止加入
	 */
	CLOSE(3),

	;

	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private ClansJoinType(int type) {
		this.type = type;
	}

}
