package com.game.user.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户玩家数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/5/15 22:54
 */
@Getter
@Setter
public class UserPlayerData {

	/**
	 * 玩家唯一id
	 */
	private long playerId;
	/**
	 * 玩家名字
	 */
	private String playerName = "";
	/**
	 * 服务器id
	 */
	private int serverId;
	/**
	 * 头像
	 */
	private String head = "";
	/**
	 * 等级
	 */
	private int lv;

}
