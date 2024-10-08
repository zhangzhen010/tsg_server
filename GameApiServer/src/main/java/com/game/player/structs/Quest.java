package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个已接取的任务
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/14 15:22
 */
@Getter
@Setter
public class Quest {

	/**
	 * 任务配置id
	 */
	private Integer configId;
	/**
	 * 是否已跳转过链接（跳转过才能完成任务）
	 */
	private boolean skip;
	/**
	 * 目标任务 任务目标配置id，任务目标值，任务目标配置id...
	 */
	private List<Integer> targetList = new ArrayList<>();

}
