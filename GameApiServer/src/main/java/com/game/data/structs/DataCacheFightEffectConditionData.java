package com.game.data.structs;

/**
 * 效果触发条件配置data
 * 
 * @author zhangzhen
 * @time 2023年4月18日
 */
public class DataCacheFightEffectConditionData {

	/**
	 * 效果触发条件类型（FightEffectConditionType）
	 */
	private int effectConditionType;
	/**
	 * 效果目标类型（FightBuffTargetType）
	 */
	private int effectTargetType;
	/**
	 * 配置扩展参数1
	 */
	private int param1;
	/**
	 * 配置扩展参数2
	 */
	private int param2;

	public int getEffectConditionType() {
		return effectConditionType;
	}

	public void setEffectConditionType(int effectConditionType) {
		this.effectConditionType = effectConditionType;
	}

	public int getEffectTargetType() {
		return effectTargetType;
	}

	public void setEffectTargetType(int effectTargetType) {
		this.effectTargetType = effectTargetType;
	}

	public int getParam1() {
		return param1;
	}

	public void setParam1(int param1) {
		this.param1 = param1;
	}

	public int getParam2() {
		return param2;
	}

	public void setParam2(int param2) {
		this.param2 = param2;
	}

}
