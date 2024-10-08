package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * skill_act Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/05/22 17:09
 */
@Document(collection = "skill_act")
public class B_skill_act_Bean {
	/**
	 * 技能id
	 */
	@Id
	private Integer id;
	/**
	 * 表现ID
	 */
	private String showId;
	/**
	 * 动作资源
	 */
	private String spn_act;
	/**
	 * 远程攻击
0 - 否
1 - 是
	 */
	private Integer remote;
	/**
	 * 移动距离
	 */
	private Integer move;
	/**
	 * 移动耗时
（毫秒）
	 */
	private Integer speed;
	/**
	 * 启动时间
（毫秒）
	 */
	private Integer skillTime;
	/**
	 * 技能特效
	 */
	private String skilleffect;
	/**
	 * 技能特效缩放
（百分比）
	 */
	private Integer skilleffectSize;
	/**
	 * 打击点延后
（毫秒）
	 */
	private Integer delayEffect;
	/**
	 * 击中特效
	 */
	private String hitEffect;
	/**
	 * 击中特效缩放
（百分比）
	 */
	private Integer hitEffectSize;
	/**
	 * 飘字（后摇）
（毫秒）
	 */
	private Integer delayTime;
	/**
	 * 音效ID
	 */
	private String skillEffectAudio;
	/**
	 * 音效ID
	 */
	private String hitEffectAudio;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShowId() {
		return this.showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}

	public String getSpn_act() {
		return this.spn_act;
	}

	public void setSpn_act(String spn_act) {
		this.spn_act = spn_act;
	}

	public Integer getRemote() {
		return this.remote;
	}

	public void setRemote(Integer remote) {
		this.remote = remote;
	}

	public Integer getMove() {
		return this.move;
	}

	public void setMove(Integer move) {
		this.move = move;
	}

	public Integer getSpeed() {
		return this.speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getSkillTime() {
		return this.skillTime;
	}

	public void setSkillTime(Integer skillTime) {
		this.skillTime = skillTime;
	}

	public String getSkilleffect() {
		return this.skilleffect;
	}

	public void setSkilleffect(String skilleffect) {
		this.skilleffect = skilleffect;
	}

	public Integer getSkilleffectSize() {
		return this.skilleffectSize;
	}

	public void setSkilleffectSize(Integer skilleffectSize) {
		this.skilleffectSize = skilleffectSize;
	}

	public Integer getDelayEffect() {
		return this.delayEffect;
	}

	public void setDelayEffect(Integer delayEffect) {
		this.delayEffect = delayEffect;
	}

	public String getHitEffect() {
		return this.hitEffect;
	}

	public void setHitEffect(String hitEffect) {
		this.hitEffect = hitEffect;
	}

	public Integer getHitEffectSize() {
		return this.hitEffectSize;
	}

	public void setHitEffectSize(Integer hitEffectSize) {
		this.hitEffectSize = hitEffectSize;
	}

	public Integer getDelayTime() {
		return this.delayTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	public String getSkillEffectAudio() {
		return this.skillEffectAudio;
	}

	public void setSkillEffectAudio(String skillEffectAudio) {
		this.skillEffectAudio = skillEffectAudio;
	}

	public String getHitEffectAudio() {
		return this.hitEffectAudio;
	}

	public void setHitEffectAudio(String hitEffectAudio) {
		this.hitEffectAudio = hitEffectAudio;
	}


}