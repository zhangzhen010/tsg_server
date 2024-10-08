package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * survivor_job Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "survivor_job")
public class B_survivor_job_Bean {
	/**
	 * 职业id
	 */
	@Id
	private Integer id;
	/**
	 * 职业名称id
	 */
	private Integer name;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 建筑id
	 */
	private List<String> building = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getName() {
		return this.name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<String> getBuilding() {
		return this.building;
	}

	public void setBuilding(List<String> building) {
		this.building = building;
	}


}