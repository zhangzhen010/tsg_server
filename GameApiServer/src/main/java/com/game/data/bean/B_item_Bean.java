package com.game.data.bean;

import com.alibaba.fastjson2.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * item Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 01:00
 */
@Document(collection = "item")
public class B_item_Bean {
	/**
	 * 道具id
	 */
	@Id
	private Integer id;
	/**
	 * 道具名
	 */
	private Integer nameId;
	/**
	 * 描述
	 */
	private Integer desId;
	/**
	 * 品质
	 */
	private Integer quality;
	/**
	 * 图标大
	 */
	private String iconBig;
	/**
	 * 图标小
	 */
	private String iconlittle;
	/**
	 * 图标样式
	 */
	private Integer iconType;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * 小类
	 */
	private Integer subType;
	/**
	 * 物品效果参数值
	 */
	private Integer subTypeValue;
	/**
	 * 是否可堆叠
	 */
	private Boolean merge;
	/**
	 * 堆叠上限
	 */
	private Long mergeMax;
	/**
	 * 获得之后过期时间（单位秒）
	 */
	private Integer delTimeSec;
	/**
	 * 获得之后过期时间（绑定活动配置id）
	 */
	private Integer delTimeActivity;
	/**
	 * 合成所需物品
	 */
	private JSONArray mergeList;
	/**
	 * 回收价格
	 */
	private JSONArray backPrice;
	/**
	 * 是否在背包内显示
	 */
	private Boolean showInBag;
	/**
	 * 在背包内是否显示红点
	 */
	private Boolean redDotInBag;
	/**
	 * 背包内排序
	 */
	private Integer orderInBag;
	/**
	 * 对应奖励表
	 */
	private Integer reward;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNameId() {
		return this.nameId;
	}

	public void setNameId(Integer nameId) {
		this.nameId = nameId;
	}

	public Integer getDesId() {
		return this.desId;
	}

	public void setDesId(Integer desId) {
		this.desId = desId;
	}

	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public String getIconBig() {
		return this.iconBig;
	}

	public void setIconBig(String iconBig) {
		this.iconBig = iconBig;
	}

	public String getIconlittle() {
		return this.iconlittle;
	}

	public void setIconlittle(String iconlittle) {
		this.iconlittle = iconlittle;
	}

	public Integer getIconType() {
		return this.iconType;
	}

	public void setIconType(Integer iconType) {
		this.iconType = iconType;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSubType() {
		return this.subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
	}

	public Integer getSubTypeValue() {
		return this.subTypeValue;
	}

	public void setSubTypeValue(Integer subTypeValue) {
		this.subTypeValue = subTypeValue;
	}

	public Boolean getMerge() {
		return this.merge;
	}

	public void setMerge(Boolean merge) {
		this.merge = merge;
	}

	public Long getMergeMax() {
		return this.mergeMax;
	}

	public void setMergeMax(Long mergeMax) {
		this.mergeMax = mergeMax;
	}

	public Integer getDelTimeSec() {
		return this.delTimeSec;
	}

	public void setDelTimeSec(Integer delTimeSec) {
		this.delTimeSec = delTimeSec;
	}

	public Integer getDelTimeActivity() {
		return this.delTimeActivity;
	}

	public void setDelTimeActivity(Integer delTimeActivity) {
		this.delTimeActivity = delTimeActivity;
	}

	public JSONArray getMergeList() {
		return this.mergeList;
	}

	public void setMergeList(JSONArray mergeList) {
		this.mergeList = mergeList;
	}

	public JSONArray getBackPrice() {
		return this.backPrice;
	}

	public void setBackPrice(JSONArray backPrice) {
		this.backPrice = backPrice;
	}

	public Boolean getShowInBag() {
		return this.showInBag;
	}

	public void setShowInBag(Boolean showInBag) {
		this.showInBag = showInBag;
	}

	public Boolean getRedDotInBag() {
		return this.redDotInBag;
	}

	public void setRedDotInBag(Boolean redDotInBag) {
		this.redDotInBag = redDotInBag;
	}

	public Integer getOrderInBag() {
		return this.orderInBag;
	}

	public void setOrderInBag(Integer orderInBag) {
		this.orderInBag = orderInBag;
	}

	public Integer getReward() {
		return this.reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}


}