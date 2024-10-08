package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * des Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "des")
public class B_des_Bean {
	/**
	 * 序号id
	 */
	@Id
	private Integer id;
	/**
	 * 中文
	 */
	private String zh_cn;
	/**
	 * 英文
	 */
	private String en_us;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getZh_cn() {
		return this.zh_cn;
	}

	public void setZh_cn(String zh_cn) {
		this.zh_cn = zh_cn;
	}

	public String getEn_us() {
		return this.en_us;
	}

	public void setEn_us(String en_us) {
		this.en_us = en_us;
	}


}