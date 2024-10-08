package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * quest_target_collect Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 01:00
 */
@Document(collection = "quest_target_collect")
public class B_quest_target_collect_Bean {
	/**
	 * 任务目标类型id
	 */
	@Id
	private Integer id;
	/**
	 * 任务目标采集类型（0=叠加 1=最大值 2=覆盖）
	 */
	private Integer type;

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


}