package com.game.data.structs;

import com.alibaba.fastjson2.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 特殊配置数据缓存数据
 * 
 * @author zhangzhen
 * @time 2020年8月27日
 */
public class DataCacheActivityData {

	/**
	 * 配置格式int
	 */
	private Integer intValue;
	/**
	 * 配置格式JsonArray
	 */
	private JSONArray datajsonArray;
	/**
	 * 配置格式map
	 */
	private Map<Integer, Integer> dataMap = new HashMap<>();
	/**
	 * 配置个数maplist
	 */
	private Map<Integer, List<Integer>> dataListMap = new HashMap<Integer, List<Integer>>();
	/**
	 * 配置格式list
	 */
	private List<Integer> dataList = new ArrayList<>();
	/**
	 * 配置格式listlist
	 */
	private List<List<Integer>> dataListList = new ArrayList<>();

	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}

	public JSONArray getDatajsonArray() {
		return datajsonArray;
	}

	public void setDatajsonArray(JSONArray datajsonArray) {
		this.datajsonArray = datajsonArray;
	}

	public Map<Integer, Integer> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<Integer, Integer> dataMap) {
		this.dataMap = dataMap;
	}

	public Map<Integer, List<Integer>> getDataListMap() {
		return dataListMap;
	}

	public void setDataListMap(Map<Integer, List<Integer>> dataListMap) {
		this.dataListMap = dataListMap;
	}

	public List<Integer> getDataList() {
		return dataList;
	}

	public void setDataList(List<Integer> dataList) {
		this.dataList = dataList;
	}

	public List<List<Integer>> getDataListList() {
		return dataListList;
	}

	public void setDataListList(List<List<Integer>> dataListList) {
		this.dataListList = dataListList;
	}

}
