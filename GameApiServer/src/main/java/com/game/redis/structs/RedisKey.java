package com.game.redis.structs;

import lombok.Getter;

/**
 * RedisKey
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/8 9:21
 */
@Getter
public enum RedisKey {

	/********* ↓用户相关↓ *********/

	/**
	 * 用户数据
	 */
	USER("user_"),

	/********* ↑用户相关↑ *********/

	/********* ↓玩家相关↓ *********/

	/**
	 * 全局玩家id当前最大值
	 */
	PLAYER_ID("playerId"),
	/**
	 * TODO 世界服 玩家上一次登录逻辑服务器id（这个用于世界服版本游戏，记录玩家最后一次登录的逻辑服，处理后台发来的命令由最后登录或在线的逻辑服来处理命令但是有一种情况，玩家再1服处理逻辑的过程中，在2服登录，导致数据同时修改，后面解决）
	 */
	PLAYER_LOGIC_SERVER_ID("p_logic_serverId_"),
	/**
	 * TODO 世界服 玩家上一次登录房间服务器id（这个用于世界服版本游戏，记录玩家最后一次登录的房间服）
	 */
	PLAYER_ROOM_SERVER_ID("p_room_serverId_"),
	/**
	 * 玩家上一次进入房间服所在房间唯一id
	 */
	PLAYER_ROOM_ID("p_roomId_"),
	/**
	 * 玩家 value=玩家数据
	 */
	PLAYER("p_"),
	/**
	 * 玩家副本数据
	 */
	PLAYER_MAP("p_map_"),
	/**
	 * 玩家英雄数据
	 */
	PLAYER_HERO("p_hero_"),
	/**
	 * 玩家背包数据
	 */
	PLAYER_PACK("p_pack_"),
	/**
	 * 玩家商店数据
	 */
	PLAYER_SHOP("p_shop_"),
	/**
	 * 玩家商店数据
	 */
	PLAYER_QUEST("p_quest_"),
	/**
	 * 玩家活动数据
	 */
	PLAYER_ACTIVITY("p_activity_"),
	/**
	 * 玩家好友数据
	 */
	PLAYER_FRIEND("p_friend_"),
	/**
	 * 玩家充值数据
	 */
	PLAYER_PAY("p_pay_"),
	/**
	 * 玩家聊天数据
	 */
	PLAYER_CHAT("p_chat_"),
	/**
	 * 玩家简易数据
	 */
	PLAYER_SIMPLE("sp_"),
	/**
	 * 玩家幸存者
	 */
	PLAYER_SURVIVOR("p_survivor_"),
	/**
	 * 玩家翅膀
	 */
	PLAYER_WING("p_wing_"),
	/**
	 * 所有玩家名字idmap
	 */
	ALL_PLAYER_NAME_ID_MAP("p_name_id_"),

	/********* ↑玩家相关↑ *********/

	/********* ↓公会相关↓ *********/

	/**
	 * 公会数据
	 */
	CLANS("clans_"),
	/**
	 * 玩家公会数据
	 */
	CLANS_PLAYER("p_clans_"),
	/**
	 * 所有公会id，用于scan查找公会
	 */
	CLANS_ID_SET("clans_id_set"),
	/**
	 * 公会活跃度排行（用于替换上面scan查找公会）
	 */
	CLANS_ACTIVE_RANK("clans_active_rank"),
	/**
	 * 公会副本数据
	 */
	CLANS_MAP("clans_map_"),
	/**
	 * 公会副本历史数据
	 */
	CLANS_MAP_LOG("clans_map_log_"),
	/**
	 * 公会简易信息
	 */
	CLANS_SIMPLE("clans_simple_"),
	/**
	 * 公会副本版本号
	 */
	CLANS_MAP_VERSION_MAP("clans_map_version_map"),
	/**
	 * 公会日志
	 */
	CLANS_LOG("clans_log_"),
	/**
	 * 公会boss挑战日志
	 */
	CLANS_CHALLENGE_LOG("clans_boss_log_"),
	/**
	 * 公会GVE排行
	 */
	CLANS_GVE_RANK("clans_gve_rank"),

	/********* ↑公会相关↑ *********/

	/********* ↓好友相关↓ *********/

	/**
	 * 角色好友数据
	 */
	FRIEND_PLAYER("p_friend_"),

	/********* ↑好友相关↑ *********/

	/********* ↓聊天相关↓ *********/

	/**
	 * 世界聊天
	 */
	CHAT_WORLD("chat_world_"),
	/**
	 * 语言聊天
	 */
	CHAT_LANGUAGE("chat_language_"),
	/**
	 * 公会聊天
	 */
	CHAT_CLANS("chat_clans_"),
	/**
	 * 本服聊天
	 */
	CHAT_LOCAL("_chat_local"),

	/********* ↑聊天相关↑ *********/

	/********* ↓排行榜相关↓ *********/

	/**
	 * 排行榜 前缀是服务器id，跟后缀：ranktype的name
	 */
	RANK("_rank_"),

	/********* ↑排行榜相关↑ *********/

	/********* ↓日志相关↓ *********/

	/**
	 * 米豆兔游戏日志
	 */
	MDT_LOG("mdt_log"),
	/**
	 * 日志公共属性
	 */
	LOG_PLAYER_PARAMS("log_player_params_"),
	/********* ↑日志相关↑ *********/

	/********* ↓服务器负载均衡相关↓ *********/

	/**
	 * 负载均衡->[逻辑服]人数排行
	 */
	LB_LOGIC_PLAYER_NUM_RANK("lb_logic_player_num_rank"),
	/**
	 * 负载均衡->[房间服]人数排行
	 */
	LB_ROOM_PLAYER_NUM_RANK("lb_room_player_num_rank"),
	/**
	 * 负载均衡->[战斗服]
	 */
	LB_SERVER_FIGHT_LIST("lb_server_fight_list"),
	/**
	 * 负载均衡->[登录服]
	 */
	LB_SERVER_LOGIN_LIST("lb_server_login_list"),
	/**
	 * 负载均衡->[竞技场服]
	 */
	LB_SERVER_ARENA_LIST("lb_server_arena_list"),

	/********* ↑服务器负载均衡相关↑ *********/

	/********* ↓服务器相关↓ *********/

	/**
	 * 服务器心跳排行，如果发现长时间没有发送心跳到redis，那么说明服务器出现了问题，需要做相应的处理
	 */
	SERVER_HEARTBEAT_RANK("server_heartbeat_rank"),
	/**
	 * 服务器grpc连接端口map(目前存放逻辑服与房间服的grpc的ip和端口，用于后台服与逻辑服通信，逻辑服与后台服不进行netty tcp连接)
	 */
	SERVER_GRPC_INFO_MAP("server_grpc_info_map"),
	/**
	 * 服务器关闭时间毫秒（在服务器关闭的时候会记录当前关闭时间，此Key有过期时间，运维在某些情况下检测不到关服日志，就可以获取此时间，如果此时间与发送关服命令时间相差不大，则认为服务器关闭成功）
	 */
	SERVER_CLOSE_TIME("server_close_time_"),

	/********* ↑服务器相关↑ *********/

	;

	private final String key;

	RedisKey(String key) {
		this.key = key;
	}

}
