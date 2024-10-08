package com.game.data.structs;

import java.util.ArrayList;
import java.util.List;

/**
 * 道具表解析对象
 * 
 * @author zhangzhen
 * @time 2023年3月14日
 */
public class DataCachePropParamData {

	/**
	 * 类型5提供的经验
	 */
	private int exp;
	/**
	 * 类型9符咒数据
	 */
	private DataCachePropParamIncantData incantData;
	/**
	 * 类型4，10提升数据
	 */
	private DataCachePropParamUpData upData;
	/**
	 * 物品id，数量，物品id...
	 */
	private List<Integer> itemList = new ArrayList<>();
	/**
	 * 补充消耗钻石
	 */
	private List<Integer> diamondPriceList = new ArrayList<>();

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public DataCachePropParamIncantData getIncantData() {
		return incantData;
	}

	public void setIncantData(DataCachePropParamIncantData incantData) {
		this.incantData = incantData;
	}

	public DataCachePropParamUpData getUpData() {
		return upData;
	}

	public void setUpData(DataCachePropParamUpData upData) {
		this.upData = upData;
	}

	public List<Integer> getItemList() {
		return itemList;
	}

	public void setItemList(List<Integer> itemList) {
		this.itemList = itemList;
	}

	public List<Integer> getDiamondPriceList() {
		return diamondPriceList;
	}

	public void setDiamondPriceList(List<Integer> diamondPriceList) {
		this.diamondPriceList = diamondPriceList;
	}
}
