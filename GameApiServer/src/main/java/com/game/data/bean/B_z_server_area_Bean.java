package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * z_server_area Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/05/16 16:49
 */
@Document(collection = "z_server_area")
public class B_z_server_area_Bean {
	/**
	 * 大区Id
	 */
	@Id
	private Integer id;
	/**
	 * 大区名字
	 */
	private String name;

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


}