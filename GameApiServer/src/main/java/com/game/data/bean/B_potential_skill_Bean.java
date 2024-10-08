package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * potential_skill Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "potential_skill")
public class B_potential_skill_Bean {
	/**
	 * 套装技能id
	 */
	@Id
	private Integer id;
	/**
	 * 技能组id
	 */
	private Integer groupId;
	/**
	 * 技能等级
	 */
	private Integer level;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}


}