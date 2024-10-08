package com.game.data.structs;

/**
 * 效果状态生效时机配置
 * 
 * @author zhangzhen
 * @time 2023年4月18日
 */
public class DataCacheFightEffectTimeData {

	/**
	 * 触发类型（FightEffectTriggerType）
	 */
	private int type;
	/**
	 * 触发概率（万分比）
	 */
	private int prop;
	/**
	 * 触发次数
	 */
	private int num;
	/**
	 * 扩展参数1
	 */
	private int param1;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getProp() {
		return prop;
	}

	public void setProp(int prop) {
		this.prop = prop;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getParam1() {
		return param1;
	}

	public void setParam1(int param1) {
		this.param1 = param1;
	}

}
