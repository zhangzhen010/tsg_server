package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * pet_pack Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "pet_pack")
public class B_pet_pack_Bean {
	/**
	 * 格子id
	 */
	@Id
	private Integer id;
	/**
	 * 解锁消耗钻石与数量
	 */
	private List<Integer> buyItem = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getBuyItem() {
		return this.buyItem;
	}

	public void setBuyItem(List<Integer> buyItem) {
		this.buyItem = buyItem;
	}


}