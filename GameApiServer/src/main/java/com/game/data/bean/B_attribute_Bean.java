package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * attribute Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "attribute")
public class B_attribute_Bean {
	/**
	 * 属性id
	 */
	@Id
	private Integer id;
	/**
	 * 属性名
	 */
	private String label;
	/**
	 * 战力计算系数
	 */
	private Integer combatPower;
	/**
	 * 属性描述
	 */
	private Integer name;
	/**
	 * 显示顺序
	 */
	private Integer showOrder;
	/**
	 * 详情描述
	 */
	private String des_id;
	/**
	 * 详情描述
	 */
	private String des;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getCombatPower() {
		return this.combatPower;
	}

	public void setCombatPower(Integer combatPower) {
		this.combatPower = combatPower;
	}

	public Integer getName() {
		return this.name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public Integer getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public String getDes_id() {
		return this.des_id;
	}

	public void setDes_id(String des_id) {
		this.des_id = des_id;
	}

	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}


}