package com.game.pay.structs;

/**
 * 订单状态
 * @author zhangzhen
 * @date 2017年8月14日
 * @version 1.0
 */
public enum PayOrderState {

	/**
	 * 生成订单
	 */
	CREATE(1),
	/**
	 * 失败
	 */
	FAIL(2),
	/**
	 * 成功
	 */
	SUCCESS(3),
	/**
	 * 补单
	 */
	REPAIR(4),
	;
	private int value;
	PayOrderState(int value){
		this.setValue(value);
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}
