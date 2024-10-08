package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * smelter_auto Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "smelter_auto")
public class B_smelter_auto_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 战斗属性
	 */
	private List<Integer> specialAttribute = new ArrayList<>();
	/**
	 * 战斗抗性
	 */
	private List<Integer> specialDefAttribute = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getSpecialAttribute() {
		return this.specialAttribute;
	}

	public void setSpecialAttribute(List<Integer> specialAttribute) {
		this.specialAttribute = specialAttribute;
	}

	public List<Integer> getSpecialDefAttribute() {
		return this.specialDefAttribute;
	}

	public void setSpecialDefAttribute(List<Integer> specialDefAttribute) {
		this.specialDefAttribute = specialDefAttribute;
	}


}