package com.game.clans.structs;

/**
 * 公会副本历史数据
 * 
 * @author zhangzhen
 * @time 2021年11月19日
 */
public class ClansMapLog {

	/**
	 * 公会id
	 */
	private long clansId;
	/**
	 * 是否保存
	 */
	private transient boolean save;

	public long getClansId() {
		return clansId;
	}

	public void setClansId(long clansId) {
		this.clansId = clansId;
	}

	public boolean isSave() {
		return save;
	}

	public void setSave(boolean save) {
		this.save = save;
	}

}
