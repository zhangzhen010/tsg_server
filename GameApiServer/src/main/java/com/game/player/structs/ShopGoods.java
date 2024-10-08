package com.game.player.structs;

/**
 * 商店一件商品
 * 
 * @author zhangzhen
 * @time 2020年3月17日
 */
public class ShopGoods {

	/**
	 * 商品配置id
	 */
	private int itemId;
	/**
	 * 可购买次数
	 */
	private int buyNum;
	/**
	 * 折扣 10000表示不打折
	 */
	private int rate;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

}
