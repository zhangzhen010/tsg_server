package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * z_server_serverlist Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/05/16 22:19
 */
@Document(collection = "z_server_serverlist")
public class B_z_server_serverlist_Bean {
	/**
	 * 服务器id
	 */
	@Id
	private Integer id;
	/**
	 * 服务器名字
	 */
	private String name;
	/**
	 * 所属大区
	 */
	private Integer areaId;
	/**
	 * 服务器类型（0=逻辑服Tcp，1=房间服Kcp）
	 */
	private Integer type;
	/**
	 * 服务器ip
	 */
	private String ip;
	/**
	 * 服务器端口
	 */
	private Integer port;
	/**
	 * 服务器开放时间
	 */
	private Long time;
	/**
	 * 服务器状态（0=维护 1=期待 2=流畅（推荐） 3=爆满）
	 */
	private Integer state;

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

	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Long getTime() {
		return this.time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


}