package com.game.data.structs;

import java.util.ArrayList;
import java.util.List;

/**
 * 道具特殊参数符咒配置
 * 
 * @author zhangzhen
 * @time 2023年3月14日
 */
public class DataCachePropParamIncantData {

	/**
	 * 配置id
	 */
	private Integer incantId;
	/**
	 * 等级
	 */
	private int lv;
	/**
	 * 星级
	 */
	private int star;
	/**
	 * 属性类型
	 */
	private List<Integer> attList = new ArrayList<>();
	/**
	 * 技能列表
	 */
	private List<Integer> skillIdList = new ArrayList<>();

	public Integer getIncantId() {
		return incantId;
	}

	public void setIncantId(Integer incantId) {
		this.incantId = incantId;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public List<Integer> getAttList() {
		return attList;
	}

	public void setAttList(List<Integer> attList) {
		this.attList = attList;
	}

	public List<Integer> getSkillIdList() {
		return skillIdList;
	}

	public void setSkillIdList(List<Integer> skillIdList) {
		this.skillIdList = skillIdList;
	}

}
