package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * skin Bean
 */
@Document(collection = "skin")
public class B_skin_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 皮肤名字
	 */
	private String name;
	/**
	 * 品阶
	 */
	private Integer quality;

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

	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}


}