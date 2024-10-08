package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * skill_buff_group Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "skill_buff_group")
public class B_skill_buff_group_Bean {
	/**
	 * buff组id
	 */
	@Id
	private Integer id;
	/**
	 * 添加前判断存在此组id的则不能添加新buff
	 */
	private List<Integer> suppressed = new ArrayList<>();
	/**
	 * 添加后移除已有buff的状态组id
	 */
	private List<Integer> dispel = new ArrayList<>();
	/**
	 * 添加新buff后，身上已有buff共享新buff层数的状态组
	 */
	private List<Integer> share = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getSuppressed() {
		return this.suppressed;
	}

	public void setSuppressed(List<Integer> suppressed) {
		this.suppressed = suppressed;
	}

	public List<Integer> getDispel() {
		return this.dispel;
	}

	public void setDispel(List<Integer> dispel) {
		this.dispel = dispel;
	}

	public List<Integer> getShare() {
		return this.share;
	}

	public void setShare(List<Integer> share) {
		this.share = share;
	}


}