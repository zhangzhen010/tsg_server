package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * survivor Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "survivor")
public class B_survivor_Bean {
	/**
	 * 幸存者id
	 */
	@Id
	private Integer id;
	/**
	 * 幸存者名字id
	 */
	private String name;
	/**
	 * 技能名字id
	 */
	private String skill;
	/**
	 * 品质
	 */
	private Integer quality;
	/**
	 * 职业
	 */
	private Integer occupation;
	/**
	 * 合成所需道具
	 */
	private List<Integer> combinItem = new ArrayList<>();
	/**
	 * 立绘
	 */
	private String da;

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

	public String getSkill() {
		return this.skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public Integer getOccupation() {
		return this.occupation;
	}

	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}

	public List<Integer> getCombinItem() {
		return this.combinItem;
	}

	public void setCombinItem(List<Integer> combinItem) {
		this.combinItem = combinItem;
	}

	public String getDa() {
		return this.da;
	}

	public void setDa(String da) {
		this.da = da;
	}


}