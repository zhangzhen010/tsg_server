package com.game.pay.structs;

/**
 * 回调游戏信息
 * 
 * @author zhangzhen
 * @date 2017年8月14日
 * @version 1.0
 */
public class CallBackInfo {

	/**
	 * 订单id
	 */
	private long orderId;
	/**
	 * 服务器id
	 */
	private int serverId;
	/**
	 * 充值金额
	 */
	private int money;
	/**
	 * 扩展参数（备用）
	 */
	private String args;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}
}
