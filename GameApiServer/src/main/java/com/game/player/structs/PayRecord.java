package com.game.player.structs;

/**
 * 一条充值记录
 * 
 * @author zhangzhen
 * @time 2020年5月21日
 */
public class PayRecord {

	/**
	 * 充值金额（单位：分）
	 */
	private int money;
	/**
	 * 充值时间
	 */
	private long payTime;

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public long getPayTime() {
		return payTime;
	}

	public void setPayTime(long payTime) {
		this.payTime = payTime;
	}

}
