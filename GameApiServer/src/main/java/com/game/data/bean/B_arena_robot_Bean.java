package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * arena_robot Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "arena_robot")
public class B_arena_robot_Bean {
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * 等级
	 */
	private Integer lv;
	/**
	 * 怪物表id
	 */
	private Integer monsterId;
	/**
	 * 显示战力
	 */
	private Integer powerShow;
	/**
	 * 幻化里头像id
	 */
	private Integer avatar;
	/**
	 * 角色幻化id
	 */
	private Integer clothes;
	/**
	 * 翅膀id
	 */
	private Integer wingId;
	/**
	 * 该机器人在竞技场的积分
	 */
	private Integer arenaPoint;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLv() {
		return this.lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	public Integer getMonsterId() {
		return this.monsterId;
	}

	public void setMonsterId(Integer monsterId) {
		this.monsterId = monsterId;
	}

	public Integer getPowerShow() {
		return this.powerShow;
	}

	public void setPowerShow(Integer powerShow) {
		this.powerShow = powerShow;
	}

	public Integer getAvatar() {
		return this.avatar;
	}

	public void setAvatar(Integer avatar) {
		this.avatar = avatar;
	}

	public Integer getClothes() {
		return this.clothes;
	}

	public void setClothes(Integer clothes) {
		this.clothes = clothes;
	}

	public Integer getWingId() {
		return this.wingId;
	}

	public void setWingId(Integer wingId) {
		this.wingId = wingId;
	}

	public Integer getArenaPoint() {
		return this.arenaPoint;
	}

	public void setArenaPoint(Integer arenaPoint) {
		this.arenaPoint = arenaPoint;
	}


}