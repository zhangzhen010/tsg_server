package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 一个精怪对象
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/5/22 下午2:03
 */
@Getter
@Setter
public class Monster {

	/**
	 * 精怪唯一id
	 */
	private long id;
	/**
	 * 精怪配置id
	 */
	private Integer configId = 0;
	/**
	 * 精怪等级
	 */
	private int lv;
	/**
	 * 战斗力
	 */
	private int force;

}
