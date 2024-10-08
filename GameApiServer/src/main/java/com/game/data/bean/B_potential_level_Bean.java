package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * potential_level Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "potential_level")
public class B_potential_level_Bean {
	/**
	 * 等级id
	 */
	@Id
	private Integer id;
	/**
	 * 升到下一级需要的经验值
	 */
	private Integer exp;
	/**
	 * 基础属性
	 */
	private List<Integer> attribute1 = new ArrayList<>();
	/**
	 * 基础属性值
	 */
	private Integer attribute1Value;
	/**
	 * 战斗属性
	 */
	private List<Integer> attribute2 = new ArrayList<>();
	/**
	 * 战斗属性值
	 */
	private Integer attribute2Value;
	/**
	 * 战斗抗性
	 */
	private List<Integer> attribute3 = new ArrayList<>();
	/**
	 * 战斗抗性值
	 */
	private Integer attribute3Value;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getExp() {
		return this.exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public List<Integer> getAttribute1() {
		return this.attribute1;
	}

	public void setAttribute1(List<Integer> attribute1) {
		this.attribute1 = attribute1;
	}

	public Integer getAttribute1Value() {
		return this.attribute1Value;
	}

	public void setAttribute1Value(Integer attribute1Value) {
		this.attribute1Value = attribute1Value;
	}

	public List<Integer> getAttribute2() {
		return this.attribute2;
	}

	public void setAttribute2(List<Integer> attribute2) {
		this.attribute2 = attribute2;
	}

	public Integer getAttribute2Value() {
		return this.attribute2Value;
	}

	public void setAttribute2Value(Integer attribute2Value) {
		this.attribute2Value = attribute2Value;
	}

	public List<Integer> getAttribute3() {
		return this.attribute3;
	}

	public void setAttribute3(List<Integer> attribute3) {
		this.attribute3 = attribute3;
	}

	public Integer getAttribute3Value() {
		return this.attribute3Value;
	}

	public void setAttribute3Value(Integer attribute3Value) {
		this.attribute3Value = attribute3Value;
	}


}