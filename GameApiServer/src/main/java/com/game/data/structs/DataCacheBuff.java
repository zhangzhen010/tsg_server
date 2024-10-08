package com.game.data.structs;

/**
 * 提前拆分配置buff效果
 * 
 * @author zhangzhen
 * @time 2023年4月17日
 */
public class DataCacheBuff {

	/**
	 * buff配置表id
	 */
	private int configId;
	/**
	 * buff目标类型(FightBuffTargetType)
	 */
	private int targetType;
	/**
	 * 添加概率
	 */
	private int prob;
	/**
	 * 添加层数
	 */
	private int num;

	public DataCacheBuff() {
	}

	public DataCacheBuff(int configId, int targetType, int prob, int num) {
		this.configId = configId;
		this.targetType = targetType;
		this.prob = prob;
		this.num = num;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public int getTargetType() {
		return targetType;
	}

	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}

	public int getProb() {
		return prob;
	}

	public void setProb(int prob) {
		this.prob = prob;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
