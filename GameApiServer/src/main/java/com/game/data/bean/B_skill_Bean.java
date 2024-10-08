package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * skill Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "skill")
public class B_skill_Bean {
	/**
	 * 技能id
	 */
	@Id
	private Integer id;
	/**
	 * 技能组id
	 */
	private Integer groupId;
	/**
	 * 技能等级
	 */
	private Integer skillLevel;
	/**
	 * 名称id
	 */
	private Integer name_id;
	/**
	 * 描述id
	 */
	private Integer des_id;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 攻击cd
	 */
	private Integer skillCd;
	/**
	 * 产生效果组
	 */
	private List<Integer> effect = new ArrayList<>();
	/**
	 * 产生buff组
	 */
	private List<Integer> buffs = new ArrayList<>();
	/**
	 * 技能类型
	 */
	private Integer skillType;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getSkillLevel() {
		return this.skillLevel;
	}

	public void setSkillLevel(Integer skillLevel) {
		this.skillLevel = skillLevel;
	}

	public Integer getName_id() {
		return this.name_id;
	}

	public void setName_id(Integer name_id) {
		this.name_id = name_id;
	}

	public Integer getDes_id() {
		return this.des_id;
	}

	public void setDes_id(Integer des_id) {
		this.des_id = des_id;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSkillCd() {
		return this.skillCd;
	}

	public void setSkillCd(Integer skillCd) {
		this.skillCd = skillCd;
	}

	public List<Integer> getEffect() {
		return this.effect;
	}

	public void setEffect(List<Integer> effect) {
		this.effect = effect;
	}

	public List<Integer> getBuffs() {
		return this.buffs;
	}

	public void setBuffs(List<Integer> buffs) {
		this.buffs = buffs;
	}

	public Integer getSkillType() {
		return this.skillType;
	}

	public void setSkillType(Integer skillType) {
		this.skillType = skillType;
	}


}