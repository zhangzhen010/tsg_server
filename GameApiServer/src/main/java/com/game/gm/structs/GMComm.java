package com.game.gm.structs;

/**
 * gm指令信息
 * 
 * @author zhangzhen
 * @time 2023年5月23日
 */
public enum GMComm {

	/***************************** 应用于工具GM命令 *****************************/

	/**
	 * 保存服务器数据
	 */
	T_SAVE("#saveServer"),
	/**
	 * 重新加载配置表
	 */
	T_RELOADDATA("#reData"),
	/**
	 * 刷新
	 */
	T_REFRESH("#refresh"),
	/**
	 * 充值
	 */
	T_PAY("#pay"),
	/**
	 * 充值补单（充值服务器处理）
	 */
	T_PAY_REPAIR("#payRepair"),
	/**
	 * 禁言
	 */
	T_SHUTUP("#shutup"),
	/**
	 * 解除禁言
	 **/
	T_RESHUTUP("#reShutup"),
	/**
	 * 封号
	 **/
	T_BANNED("#banned"),
	/**
	 * 解封
	 **/
	T_REBANNED("#reBanned"),
	/**
	 * 踢人(因为踢人下线会自动重连，所以这个gm的作用是让玩家重新走一次登录流程)
	 **/
	T_GOOUT("#goout"),
	/**
	 * 添加各种资源道具装备英雄
	 */
	T_GOODS_ADD("#goodsAdd"),
	/**
	 * 修改各种资源道具装备英雄
	 */
	T_GOODS_CHANGE("#goodsChange"),
	/**
	 * 后台服通知邮件服在缓存中删除后台邮件
	 */
	T_BACK_MAIL_REMOVE("#backMailRemove"),
	/**
	 * 后台服通知邮件服在缓存中删除邮件
	 */
	T_MAIL_REMOVE("#mailRemove"),
	/**
	 * 后台服通知邮件服撤消（删除）已发送的邮件的缓存（删除后台邮件并且删除玩家邮件）
	 */
	T_BACK_MAIL_REVERSE("#backMailReverse"),
	/**
	 * 后台修改玩家部分信息
	 */
	T_PLAYER_INFO_CHANGE("#playerInfoChange"),

	;

	private String value;

	GMComm(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
