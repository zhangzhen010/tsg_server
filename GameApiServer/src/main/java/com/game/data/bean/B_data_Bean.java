package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * data Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "data")
public class B_data_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 数字值
	 */
	private Integer value;
	/**
	 * 数字数组
	 */
	private List<Integer> valueList = new ArrayList<>();
	/**
	 * 字符串值
	 */
	private String values;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValue() {
		return this.value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public List<Integer> getValueList() {
		return this.valueList;
	}

	public void setValueList(List<Integer> valueList) {
		this.valueList = valueList;
	}

	public String getValues() {
		return this.values;
	}

	public void setValues(String values) {
		this.values = values;
	}


}