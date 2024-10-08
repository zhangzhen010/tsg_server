package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * role_level Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "role_level")
public class B_role_level_Bean {
	/**
	 * 等级id
	 */
	@Id
	private Integer id;
	/**
	 * 升级所需经验
	 */
	private Integer exp;
	/**
	 * 该等级卖装备获得金币
	 */
	private Integer levelGold;
	/**
	 * 角色属性
	 */
	private List<Integer> roleAttributeType = new ArrayList<>();
	/**
	 * 角色属性值
	 */
	private List<Long> roleAttributeValue = new ArrayList<>();
	/**
	 * 升级至下一级需要境界等级
	 */
	private Integer mainRoleAdavancedID;

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

	public Integer getLevelGold() {
		return this.levelGold;
	}

	public void setLevelGold(Integer levelGold) {
		this.levelGold = levelGold;
	}

	public List<Integer> getRoleAttributeType() {
		return this.roleAttributeType;
	}

	public void setRoleAttributeType(List<Integer> roleAttributeType) {
		this.roleAttributeType = roleAttributeType;
	}

	public List<Long> getRoleAttributeValue() {
		return this.roleAttributeValue;
	}

	public void setRoleAttributeValue(List<Long> roleAttributeValue) {
		this.roleAttributeValue = roleAttributeValue;
	}

	public Integer getMainRoleAdavancedID() {
		return this.mainRoleAdavancedID;
	}

	public void setMainRoleAdavancedID(Integer mainRoleAdavancedID) {
		this.mainRoleAdavancedID = mainRoleAdavancedID;
	}


}