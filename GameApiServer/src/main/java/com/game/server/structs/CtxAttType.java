package com.game.server.structs;

import com.game.bean.proto.BeanProto;
import io.netty.util.AttributeKey;

import java.util.List;

/**
 * ctx属性类型
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/7 9:59
 */
public class CtxAttType {

	/******************************** gameServer ********************************/
	/**
	 * 是否断线重连
	 */
	public static AttributeKey<BeanProto.E_LOGIN_REQ_TYPE> SESSION_LOGIN_TYPE = AttributeKey.valueOf("session_login_type");
	/**
	 * 服务器Id
	 */
	public static AttributeKey<Integer> SESSION_LANGUAGE = AttributeKey.valueOf("session_language");
	/**
	 * 登录设备号
	 */
	public static AttributeKey<String> PLAYER_DEVICEID = AttributeKey.valueOf("player_deviceId");
	/**
	 * 客户端版本号
	 */
	public static AttributeKey<String> PLAYER_CLIENTVER = AttributeKey.valueOf("player_clientver");
	/**
	 * 客户端版本号
	 */
	public static AttributeKey<List<String>> PLAYER_LOG_PARASLIST = AttributeKey.valueOf("player_log_paraslist");
	/**
	 * 错误消息数量
	 */
	public static AttributeKey<Integer> MSG_ERROR_NUM = AttributeKey.valueOf("msg_error_num");
	/**
	 * 消息内容
	 */
	public static AttributeKey<Object> SESSION_MSG_CONTEXT = AttributeKey.valueOf("session_msg_context");
	/**
	 * 是否是网关session
	 */
	public static AttributeKey<Boolean> SESSION_GATE = AttributeKey.valueOf("session_gate");
	/**
	 * 是否是日志服务器session
	 */
	public static AttributeKey<Boolean> SESSION_LOGS = AttributeKey.valueOf("session_logs");
	/**
	 * 是否是公共服务器session
	 */
	public static AttributeKey<Boolean> SESSION_PUBLIC = AttributeKey.valueOf("session_public");
	/**
	 * 是否是竞技场服务器session
	 */
	public static AttributeKey<Boolean> SESSION_ARENA = AttributeKey.valueOf("session_arena");
	/**
	 * 是否是战斗服务器session
	 */
	public static AttributeKey<Boolean> SESSION_FIGHT = AttributeKey.valueOf("session_fight");
	/**
	 * 是否是聊天服务器session
	 */
	public static AttributeKey<Boolean> SESSION_CHAT = AttributeKey.valueOf("session_chat");
	/**
	 * 是否是充值服务器session
	 */
	public static AttributeKey<Boolean> SESSION_PAY = AttributeKey.valueOf("session_pay");
	/**
	 * 是否是公会服务器session
	 */
	public static AttributeKey<Boolean> SESSION_CLANS = AttributeKey.valueOf("session_clans");
	/**
	 * 是否是好友服务器session
	 */
	public static AttributeKey<Boolean> SESSION_FRIEND = AttributeKey.valueOf("session_friend");
	/**
	 * 是否是DB服务器session
	 */
	public static AttributeKey<Boolean> SESSION_DB = AttributeKey.valueOf("session_db");
	/**
	 * 是否是后台服务器session
	 */
	public static AttributeKey<Boolean> SESSION_WEB = AttributeKey.valueOf("session_web");
	/**
	 * 是否是邮件服务器session
	 */
	public static AttributeKey<Boolean> SESSION_MAIL = AttributeKey.valueOf("session_mail");
	/**
	 * 是否是中心服务器session
	 */
	public static AttributeKey<Boolean> SESSION_CENTER = AttributeKey.valueOf("session_center");
	/**
	 * 上一次的心跳时间
	 */
	public static AttributeKey<Long> SESSION_LAST_HEART = AttributeKey.valueOf("session_last_heart");
	/**
	 * 用户id
	 */
	public static AttributeKey<Long> SESSION_USER_ID = AttributeKey.valueOf("session_user_id");
	/**
	 * 角色id
	 */
	public static AttributeKey<Long> SESSION_PLAYER_ID = AttributeKey.valueOf("session_player_id");
	/**
	 * 角色所在房间服唯一id(在房间服务器的情况下，每一个绑定了玩家id的ctx都有一个房间唯一id，切换房间都会重新创建房间连接)
	 * 主要是为了解决一个问题，比如大厅心跳触发断开，但是玩家当前在竞技场房间，需要根据大厅ctx绑定的房间唯一id去处理退出房间逻辑，不能直接把竞技场房间影响了
	 */
	public static AttributeKey<Long> CTX_PLAYER_ROOM_ID = AttributeKey.valueOf("ctx_player_room_id");
	/**
	 * 此链接是否已经执行过断开离开房间逻辑处理，不重复出发(true=已经执行过退出房间逻辑)
	 * 主要是为了解决一个问题，玩家A服务器认为还在线，但是玩家A重新登录，分配房间会先移除一次并且断开连接，然后加入房间，然后ctx断开触发有移除一次，造成刚进入房间又被踢出房间
	 */
	public static AttributeKey<Boolean> CTX_PLAYER_ROOM_EXIT = AttributeKey.valueOf("ctx_player_room_exit");
	/**
	 * 登陆后首次验证心跳时间
	 */
	public static AttributeKey<Long> SESSION_INIT_HEART = AttributeKey.valueOf("session_init_heart");
	/**
	 * 用户名
	 */
	public static AttributeKey<String> SESSION_USER_NAME = AttributeKey.valueOf("session_user_name");
	/**
	 * 登陆ip
	 */
	public static AttributeKey<String> SESSION_SESSION_IP = AttributeKey.valueOf("session_session_ip");
	/**
	 * pfId
	 */
	public static AttributeKey<Integer> sESSION_PF_ID = AttributeKey.valueOf("session_pf_id");
	/**
	 * SdkId（服务器自定义sdkId）
	 */
	public static AttributeKey<Integer> sESSION_SDK_ID = AttributeKey.valueOf("session_sdk_id");
	/**
	 * 首次心跳时间
	 */
	public static AttributeKey<Long> SESSION_FIRST_HEART = AttributeKey.valueOf("session_first_heart");
	/**
	 * 累计心跳次数
	 */
	public static AttributeKey<Integer> SESSION_HEART_TIMES = AttributeKey.valueOf("session_heart_times");
	/**
	 * 上一秒收到消息时间
	 */
	public static AttributeKey<Long> SESSION_MESSAGE_LASTTIME = AttributeKey.valueOf("session_message_lasttime");
	/**
	 * 当前秒已收到消息数量
	 */
	public static AttributeKey<Integer> SESSION_MESSAGE_COUNT = AttributeKey.valueOf("session_message_count");
	/**
	 * session关闭信息
	 */
	public static AttributeKey<String> SESSION_CLOSE_INFO = AttributeKey.valueOf("session_close_info");
	/**
	 * session创建时间
	 */
	public static AttributeKey<Long> SESSION_CREATE_TIME = AttributeKey.valueOf("session_create_time");
	/**
	 * 游戏服务器作为客户端时和中心服务器之间的心跳
	 */
	public static AttributeKey<Long> SESSION_C2S_HEART_TIME = AttributeKey.valueOf("session_c2s_heart_time");
	/**
	 * 游戏服务器作为客户端时（-> 中心服）绑定游戏服务器本身的服务器id列表
	 */
	public static AttributeKey<Integer> SESSION_C2S_SERVERID = AttributeKey.valueOf("session_c2s_serverId");
	/**
	 * 游戏服务器作为客户端时（-> 中心服）绑定游戏服务器本身的ip
	 */
	public static AttributeKey<String> SESSION_C2S_SERVERIP = AttributeKey.valueOf("session_c2s_serverip");
	/**
	 * 游戏服务器作为客户端时（-> 中心服）绑定游戏服务器本身的port
	 */
	public static AttributeKey<Integer> SESSION_C2S_SERVERPORT = AttributeKey.valueOf("session_c2s_serverport");

	/******************************** gameServer ********************************/

	/******************************** otherServer ********************************/

	/**
	 * ctx创建时间
	 */
	public static AttributeKey<Long> CTX_CREATE_TIME = AttributeKey.valueOf("ctx_create_time");
	/**
	 * ctx客户端ip
	 */
	public static AttributeKey<String> CTX_CLIENT_IP = AttributeKey.valueOf("ctx_client_ip");
	/**
	 * ctx客户端设备号
	 */
	public static AttributeKey<String> CTX_DEVICE_ID = AttributeKey.valueOf("ctx_device_id");
	/**
	 * 游戏服务器连接其他中心服务器ID列表
	 */
	public static AttributeKey<List<Integer>> CTX_C2S_SERVERID_LIST = AttributeKey.valueOf("ctx_c2s_serverid_list");

	/******************************** otherServer ********************************/

}
