package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * z_server_ip_white Bean
 */
@Document(collection = "z_server_ip_white")
public class B_z_server_ip_white_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * ip白名单
	 */
	private String ip;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


}