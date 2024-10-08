package com.game.data.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * survivor_draw Bean
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
@Document(collection = "survivor_draw")
public class B_survivor_draw_Bean {
	/**
	 * 卡池id
	 */
	@Id
	private Integer id;
	/**
	 * 抽奖池
	 */
	private Integer pool;
	/**
	 * 保底cd
	 */
	private List<Integer> pityCd = new ArrayList<>();
	/**
	 * 消耗道具ID
	 */
	private List<Integer> costItemId = new ArrayList<>();
	/**
	 * 显示概率品质
	 */
	private List<Integer> drawShowQuality = new ArrayList<>();
	/**
	 * 整卡显示概率
	 */
	private List<Integer> drawWholeShow = new ArrayList<>();
	/**
	 * 碎片显示概率
	 */
	private List<Integer> drawChipShow = new ArrayList<>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPool() {
		return this.pool;
	}

	public void setPool(Integer pool) {
		this.pool = pool;
	}

	public List<Integer> getPityCd() {
		return this.pityCd;
	}

	public void setPityCd(List<Integer> pityCd) {
		this.pityCd = pityCd;
	}

	public List<Integer> getCostItemId() {
		return this.costItemId;
	}

	public void setCostItemId(List<Integer> costItemId) {
		this.costItemId = costItemId;
	}

	public List<Integer> getDrawShowQuality() {
		return this.drawShowQuality;
	}

	public void setDrawShowQuality(List<Integer> drawShowQuality) {
		this.drawShowQuality = drawShowQuality;
	}

	public List<Integer> getDrawWholeShow() {
		return this.drawWholeShow;
	}

	public void setDrawWholeShow(List<Integer> drawWholeShow) {
		this.drawWholeShow = drawWholeShow;
	}

	public List<Integer> getDrawChipShow() {
		return this.drawChipShow;
	}

	public void setDrawChipShow(List<Integer> drawChipShow) {
		this.drawChipShow = drawChipShow;
	}


}