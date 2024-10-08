package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * aerocraft_base Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "aerocraft_base")
public class B_aerocraft_base_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 解锁消耗道具
	 */
	private List<Integer> shopId = new ArrayList<>();
	/**
	 * 专属属性类型
	 */
	private Integer exclusiveAttrType;
	/**
	 * 战斗抗性图标
	 */
	private String attributeIcon;
	/**
	 * 默认属性类型
	 */
	private List<Integer> defaultAttrType = new ArrayList<>();
	/**
	 * 等级属性类型
	 */
	private List<Integer> levelAttrType = new ArrayList<>();
	/**
	 * 飞行器spine资源
	 */
	private String aerocraftSpineRes;
	/**
	 * 飞行棋图标资源
	 */
	private String aerocraftIconRes;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getShopId() {
		return this.shopId;
	}

	public void setShopId(List<Integer> shopId) {
		this.shopId = shopId;
	}

	public Integer getExclusiveAttrType() {
		return this.exclusiveAttrType;
	}

	public void setExclusiveAttrType(Integer exclusiveAttrType) {
		this.exclusiveAttrType = exclusiveAttrType;
	}

	public String getAttributeIcon() {
		return this.attributeIcon;
	}

	public void setAttributeIcon(String attributeIcon) {
		this.attributeIcon = attributeIcon;
	}

	public List<Integer> getDefaultAttrType() {
		return this.defaultAttrType;
	}

	public void setDefaultAttrType(List<Integer> defaultAttrType) {
		this.defaultAttrType = defaultAttrType;
	}

	public List<Integer> getLevelAttrType() {
		return this.levelAttrType;
	}

	public void setLevelAttrType(List<Integer> levelAttrType) {
		this.levelAttrType = levelAttrType;
	}

	public String getAerocraftSpineRes() {
		return this.aerocraftSpineRes;
	}

	public void setAerocraftSpineRes(String aerocraftSpineRes) {
		this.aerocraftSpineRes = aerocraftSpineRes;
	}

	public String getAerocraftIconRes() {
		return this.aerocraftIconRes;
	}

	public void setAerocraftIconRes(String aerocraftIconRes) {
		this.aerocraftIconRes = aerocraftIconRes;
	}


}