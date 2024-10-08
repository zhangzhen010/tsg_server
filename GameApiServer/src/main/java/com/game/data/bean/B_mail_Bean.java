package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * mail Bean
 */
@Document(collection = "mail")
public class B_mail_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 邮件类型
	 */
	private Integer type;
	/**
	 * 发件人
	 */
	private String sender;
	/**
	 * 邮件标题
	 */
	private String title;
	/**
	 * 邮件内容
	 */
	private String content;
	/**
	 * 奖励库id
	 */
	private Integer awardId;
	/**
	 * 邮件过期时间（单位分钟，不填就是不会过期）
	 */
	private Integer outTime;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
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

	public Integer getAwardId() {
		return this.awardId;
	}

	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}

	public Integer getOutTime() {
		return this.outTime;
	}

	public void setOutTime(Integer outTime) {
		this.outTime = outTime;
	}


}