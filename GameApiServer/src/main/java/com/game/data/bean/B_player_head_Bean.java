package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * player_head Bean
 */
@Document(collection = "player_head")
public class B_player_head_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


}