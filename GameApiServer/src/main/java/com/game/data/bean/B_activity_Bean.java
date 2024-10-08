package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * activity Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/05/14 16:38
 */
@Document(collection = "activity")
public class B_activity_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 活动类型
	 */
	private Integer activityType;
	/**
	 * 服务器ID起始(包括)
	 */
	private Integer serverIdBegin;
	/**
	 * 服务器ID结尾(包括)
	 */
	private Integer serverIdEnd;
	/**
	 * 活动启用方式（0=永远开启 1=指定时间戳，2=开服天数）
	 */
	private Integer openType;
	/**
	 * 活动预加载开始时间（时间戳）
	 */
	private Long loadBeginTime;
	/**
	 * 活动开启时间（时间戳）
	 */
	private Long beginTime;
	/**
	 * 活动结束时间（时间戳）
	 */
	private Long endTime;
	/**
	 * 活动预加载结束时间（时间戳）
	 */
	private Long loadEndTime;
	/**
	 * 活动预加载开始时间（开服天数：0,0,0,0=表示开服第1天上午0点0分0秒加载活动）
	 */
	private List<Integer> loadBeginDayList = new ArrayList<>();
	/**
	 * 活动开启时间（开服天数，规则与前面一致）
	 */
	private List<Integer> beginDayList = new ArrayList<>();
	/**
	 * 活动结束时间（开服天数,规则与前面一致）
	 */
	private List<Integer> endDayList = new ArrayList<>();
	/**
	 * 活动预加载结束时间（开服天数，规则与前面一致）
	 */
	private List<Integer> loadEndDayList = new ArrayList<>();
	/**
	 * 活动版本号：此版本号从1开始,当活动内容需要发生变化,活动版本号+1,此操作会清除所有玩家在此活动的所有记录数据。相当于重新开启新一轮的活动。
	 */
	private Integer version;
	/**
	 * 是否每日重置(false=否 true=是)
	 */
	private Boolean everyDayReset;
	/**
	 * 数字列表扩展参数1（填写对应字段的用法说明）
	 */
	private String valueList1Note;
	/**
	 * 数字列表扩展参数1
	 */
	private List<Integer> valueList1 = new ArrayList<>();
	/**
	 * 数字列表扩展参数2（填写对应字段的用法说明）
	 */
	private String valueList2Note;
	/**
	 * 数字列表扩展参数2
	 */
	private List<Integer> valueList2 = new ArrayList<>();
	/**
	 * 数字列表扩展参数3（填写对应字段的用法说明）
	 */
	private String valueList3Note;
	/**
	 * 数字列表扩展参数3
	 */
	private List<Integer> valueList3 = new ArrayList<>();
	/**
	 * 数字列表扩展参数4（填写对应字段的用法说明）
	 */
	private String valueList4Note;
	/**
	 * 数字列表扩展参数4
	 */
	private List<Integer> valueList4 = new ArrayList<>();
	/**
	 * 数字列表扩展参数5（填写对应字段的用法说明）
	 */
	private String valueList5Note;
	/**
	 * 数字列表扩展参数5
	 */
	private List<Integer> valueList5 = new ArrayList<>();
	/**
	 * 数字参数1（填写对应字段的用法说明）
	 */
	private String value1Note;
	/**
	 * 数字参数1
	 */
	private Integer value1;
	/**
	 * 数字参数2（填写对应字段的用法说明）
	 */
	private String value2Note;
	/**
	 * 数字参数2
	 */
	private Integer value2;
	/**
	 * 数字参数3（填写对应字段的用法说明）
	 */
	private String value3Note;
	/**
	 * 数字参数3
	 */
	private Integer value3;
	/**
	 * 数字参数4（填写对应字段的用法说明）
	 */
	private String value4Note;
	/**
	 * 数字参数4
	 */
	private Integer value4;
	/**
	 * 数字参数5（填写对应字段的用法说明）
	 */
	private String value5Note;
	/**
	 * 数字参数5
	 */
	private Integer value5;
	/**
	 * 字符串参数（填写对应字段的用法说明）
	 */
	private String valuesNote;
	/**
	 * 字符串参数
	 */
	private String values;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getActivityType() {
		return this.activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public Integer getServerIdBegin() {
		return this.serverIdBegin;
	}

	public void setServerIdBegin(Integer serverIdBegin) {
		this.serverIdBegin = serverIdBegin;
	}

	public Integer getServerIdEnd() {
		return this.serverIdEnd;
	}

	public void setServerIdEnd(Integer serverIdEnd) {
		this.serverIdEnd = serverIdEnd;
	}

	public Integer getOpenType() {
		return this.openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
	}

	public Long getLoadBeginTime() {
		return this.loadBeginTime;
	}

	public void setLoadBeginTime(Long loadBeginTime) {
		this.loadBeginTime = loadBeginTime;
	}

	public Long getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Long getLoadEndTime() {
		return this.loadEndTime;
	}

	public void setLoadEndTime(Long loadEndTime) {
		this.loadEndTime = loadEndTime;
	}

	public List<Integer> getLoadBeginDayList() {
		return this.loadBeginDayList;
	}

	public void setLoadBeginDayList(List<Integer> loadBeginDayList) {
		this.loadBeginDayList = loadBeginDayList;
	}

	public List<Integer> getBeginDayList() {
		return this.beginDayList;
	}

	public void setBeginDayList(List<Integer> beginDayList) {
		this.beginDayList = beginDayList;
	}

	public List<Integer> getEndDayList() {
		return this.endDayList;
	}

	public void setEndDayList(List<Integer> endDayList) {
		this.endDayList = endDayList;
	}

	public List<Integer> getLoadEndDayList() {
		return this.loadEndDayList;
	}

	public void setLoadEndDayList(List<Integer> loadEndDayList) {
		this.loadEndDayList = loadEndDayList;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Boolean getEveryDayReset() {
		return this.everyDayReset;
	}

	public void setEveryDayReset(Boolean everyDayReset) {
		this.everyDayReset = everyDayReset;
	}

	public String getValueList1Note() {
		return this.valueList1Note;
	}

	public void setValueList1Note(String valueList1Note) {
		this.valueList1Note = valueList1Note;
	}

	public List<Integer> getValueList1() {
		return this.valueList1;
	}

	public void setValueList1(List<Integer> valueList1) {
		this.valueList1 = valueList1;
	}

	public String getValueList2Note() {
		return this.valueList2Note;
	}

	public void setValueList2Note(String valueList2Note) {
		this.valueList2Note = valueList2Note;
	}

	public List<Integer> getValueList2() {
		return this.valueList2;
	}

	public void setValueList2(List<Integer> valueList2) {
		this.valueList2 = valueList2;
	}

	public String getValueList3Note() {
		return this.valueList3Note;
	}

	public void setValueList3Note(String valueList3Note) {
		this.valueList3Note = valueList3Note;
	}

	public List<Integer> getValueList3() {
		return this.valueList3;
	}

	public void setValueList3(List<Integer> valueList3) {
		this.valueList3 = valueList3;
	}

	public String getValueList4Note() {
		return this.valueList4Note;
	}

	public void setValueList4Note(String valueList4Note) {
		this.valueList4Note = valueList4Note;
	}

	public List<Integer> getValueList4() {
		return this.valueList4;
	}

	public void setValueList4(List<Integer> valueList4) {
		this.valueList4 = valueList4;
	}

	public String getValueList5Note() {
		return this.valueList5Note;
	}

	public void setValueList5Note(String valueList5Note) {
		this.valueList5Note = valueList5Note;
	}

	public List<Integer> getValueList5() {
		return this.valueList5;
	}

	public void setValueList5(List<Integer> valueList5) {
		this.valueList5 = valueList5;
	}

	public String getValue1Note() {
		return this.value1Note;
	}

	public void setValue1Note(String value1Note) {
		this.value1Note = value1Note;
	}

	public Integer getValue1() {
		return this.value1;
	}

	public void setValue1(Integer value1) {
		this.value1 = value1;
	}

	public String getValue2Note() {
		return this.value2Note;
	}

	public void setValue2Note(String value2Note) {
		this.value2Note = value2Note;
	}

	public Integer getValue2() {
		return this.value2;
	}

	public void setValue2(Integer value2) {
		this.value2 = value2;
	}

	public String getValue3Note() {
		return this.value3Note;
	}

	public void setValue3Note(String value3Note) {
		this.value3Note = value3Note;
	}

	public Integer getValue3() {
		return this.value3;
	}

	public void setValue3(Integer value3) {
		this.value3 = value3;
	}

	public String getValue4Note() {
		return this.value4Note;
	}

	public void setValue4Note(String value4Note) {
		this.value4Note = value4Note;
	}

	public Integer getValue4() {
		return this.value4;
	}

	public void setValue4(Integer value4) {
		this.value4 = value4;
	}

	public String getValue5Note() {
		return this.value5Note;
	}

	public void setValue5Note(String value5Note) {
		this.value5Note = value5Note;
	}

	public Integer getValue5() {
		return this.value5;
	}

	public void setValue5(Integer value5) {
		this.value5 = value5;
	}

	public String getValuesNote() {
		return this.valuesNote;
	}

	public void setValuesNote(String valuesNote) {
		this.valuesNote = valuesNote;
	}

	public String getValues() {
		return this.values;
	}

	public void setValues(String values) {
		this.values = values;
	}


}