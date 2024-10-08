package com.game.data.structs;

/**
 * 解析战斗buff效果数据
 * 
 * @author zhangzhen
 * @time 2023年4月14日
 */
public class DataCacheFightBuffEffectData {

	/**
	 * 效果类型 BuffType值
	 */
	private int type;
	/**
	 * 效果值1
	 */
	private int value1;
	/**
	 * 效果值2
	 */
	private int value2;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getValue1() {
		return value1;
	}

	public void setValue1(int value1) {
		this.value1 = value1;
	}

	public int getValue2() {
		return value2;
	}

	public void setValue2(int value2) {
		this.value2 = value2;
	}

}
