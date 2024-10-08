package com.game.data.structs;

/**
 * 道具特殊参数提升配置
 * 
 * @author zhangzhen
 * @time 2023年3月14日
 */
public class DataCachePropParamUpData {

	/**
	 * 元素
	 */
	private int element;
	/**
	 * 升星经验
	 */
	private int starExp;
	/**
	 * 暴击万分比
	 */
	private int critHitRate;

	public int getElement() {
		return element;
	}

	public void setElement(int element) {
		this.element = element;
	}

	public int getStarExp() {
		return starExp;
	}

	public void setStarExp(int starExp) {
		this.starExp = starExp;
	}

	public int getCritHitRate() {
		return critHitRate;
	}

	public void setCritHitRate(int critHitRate) {
		this.critHitRate = critHitRate;
	}

}
