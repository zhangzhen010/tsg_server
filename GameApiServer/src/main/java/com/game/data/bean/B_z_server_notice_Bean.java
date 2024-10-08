package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * z_server_notice Bean
 */
@Document(collection = "z_server_notice")
public class B_z_server_notice_Bean {
	/**
	 * Id
	 */
	@Id
	private Integer id;
	/**
	 * agent配置表id
	 */
	private Integer agentId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 开始时间
	 */
	private Long stime;
	/**
	 * 结束时间
	 */
	private Long etime;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAgentId() {
		return this.agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getStime() {
		return this.stime;
	}

	public void setStime(Long stime) {
		this.stime = stime;
	}

	public Long getEtime() {
		return this.etime;
	}

	public void setEtime(Long etime) {
		this.etime = etime;
	}


}