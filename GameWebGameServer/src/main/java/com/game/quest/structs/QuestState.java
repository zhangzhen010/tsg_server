package com.game.quest.structs;

/**
 * 任务目标进度类型
 * 
 * @author zhangzhen
 * @time 2023年3月27日
 */
public class QuestState {

	/**
	 * 进行中未完成
	 */
	public static final int DOING = 0;
	/**
	 * 已完成
	 */
	public static final int COMPLETE = 1;
	/**
	 * 未解锁
	 */
	public static final int NO_UNLOCK = 2;
	/**
	 * 已领奖
	 */
	public static final int GET = 3;

}
