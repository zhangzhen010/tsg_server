package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * robot_name Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "robot_name")
public class B_robot_name_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 名称前缀
	 */
	private Integer namePrefix;
	/**
	 * 名称后缀
	 */
	private Integer nameSuffix;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNamePrefix() {
		return this.namePrefix;
	}

	public void setNamePrefix(Integer namePrefix) {
		this.namePrefix = namePrefix;
	}

	public Integer getNameSuffix() {
		return this.nameSuffix;
	}

	public void setNameSuffix(Integer nameSuffix) {
		this.nameSuffix = nameSuffix;
	}


}