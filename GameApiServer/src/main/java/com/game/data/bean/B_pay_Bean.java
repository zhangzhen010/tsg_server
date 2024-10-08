package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * pay Bean
 */
@Document(collection = "pay")
public class B_pay_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 充值对应金额(单位：分)
	 */
	private Integer money;
	/**
	 * 产品标识
	 */
	private String productId;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


}