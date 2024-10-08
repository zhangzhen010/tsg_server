package com.game.data.define;

/**
 * MyDefineLanguage *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/06/01 00:00
 */
public class MyDefineLanguage {

	/**
	 * 玩家设置数据超出范围 Id=1
	 */
	public static final Integer PLAYER_SETTING_ERR = 1;
	/**
	 * 资源不足 Id=2
	 */
	public static final Integer RESOURCE = 2;
	/**
	 * 不满足领取条件 Id=3
	 */
	public static final Integer GET_NO = 3;
	/**
	 * 任务已领取 Id=4
	 */
	public static final Integer QUEST_GET = 4;
	/**
	 * 任务未完成 Id=5
	 */
	public static final Integer QUEST_NO = 5;
	/**
	 * 请求参数错误 Id=6
	 */
	public static final Integer PARAMS_ERR = 6;
	/**
	 * 玩家名字长度错误 Id=7
	 */
	public static final Integer PLAYER_NAME_LENGTH = 7;
	/**
	 * 玩家名字已存在 Id=8
	 */
	public static final Integer PLAYER_NAME_EXISTS = 8;
	/**
	 * 玩家名字包含敏感字符 Id=9
	 */
	public static final Integer PLAYER_NAME_ERROR = 9;
	/**
	 * 连接战斗服务器失败！ Id=10
	 */
	public static final Integer FIGHT_CLOSE = 10;
	/**
	 * 连接登录服务器失败！ Id=11
	 */
	public static final Integer LOGIN_CLOSE = 11;
	/**
	 * 关卡已通关 Id=12
	 */
	public static final Integer STAGE_MAX = 12;
	/**
	 * 登录服登录成功 Id=1000
	 */
	public static final Integer LOGIN_SUCCESS = 1000;
	/**
	 * 目前启用ip白名单登录 Id=1001
	 */
	public static final Integer LOGIN_IP_WHITE = 1001;
	/**
	 * 该账号已被封号，禁止登陆 Id=1002
	 */
	public static final Integer LOGIN_BAN = 1002;
	/**
	 * ip黑名单中，无法登录 Id=1003
	 */
	public static final Integer LOGIN_IP_BLACK = 1003;
	/**
	 * md5验证不通过 Id=1004
	 */
	public static final Integer LOGIN_MD5 = 1004;
	/**
	 * 账号不存在 Id=1005
	 */
	public static final Integer LOGIN_USER = 1005;
	/**
	 * 没有可进入的逻辑服 Id=1006
	 */
	public static final Integer SERVER_LOGIC_NO = 1006;

}