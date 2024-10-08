package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * z_server_data Bean
 */
@Document(collection = "z_server_data")
public class B_z_server_data_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 数字
	 */
	private Integer value;
	/**
	 * 数字列表
	 */
	private List<Integer> valueList;
	/**
	 * 字符串
	 */
	private String values;

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