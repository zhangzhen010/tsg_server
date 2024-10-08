package com.game.db.utils;

import com.game.db.proto.DbProto.SimplePlayerBean;
import com.game.player.structs.SimplePlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换SimplePlayer存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class SimplePlayerDbUtils {


	private static Logger log = LogManager.getLogger(SimplePlayerDbUtils.class);

	/**
	 * 构建一个可存储的SimplePlayer数据
	* @param data
	* @return SimplePlayerBean
	 */
	public static SimplePlayerBean buildSimplePlayerBean(SimplePlayer data) {
		try {
			SimplePlayerBean.Builder builder = SimplePlayerBean.newBuilder();
			builder.setPlayerId(data.getPlayerId());
			builder.setUserId(data.getUserId());
			builder.setServerId(data.getServerId());
			builder.setCurrentServerId(data.getCurrentServerId());
			builder.setPlayerName(data.getPlayerName());
			builder.setHead(data.getHead());
			builder.setSkinId(data.getSkinId());
			builder.setLv(data.getLv());
			builder.setVipLv(data.getVipLv());
			builder.setSumMoney(data.getSumMoney());
			builder.setForce(data.getForce());
			builder.setPfId(data.getPfId());
			builder.setSdkId(data.getSdkId());
			builder.setLanguage(data.getLanguage());
			builder.setCreateTime(data.getCreateTime());
			builder.setLoginTime(data.getLoginTime());
			builder.setLoginOutTime(data.getLoginOutTime());
			builder.setLoginBannedTime(data.getLoginBannedTime());
			for (int i = 0, len = data.getAttBaseList().size(); i < len; i++) {
				Long value = data.getAttBaseList().get(i);
				builder.addAttBaseList(value);
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的SimplePlayer数据", e);
			return null;
		}
	}

	/**
	 * 解析一个SimplePlayerBean数据
	* @param data
	* @return SimplePlayer
	 */
	public static SimplePlayer parseFromSimplePlayer(byte[] data) {
		try {
			SimplePlayerBean bean = SimplePlayerBean.parseFrom(data);
			SimplePlayer simplePlayer = new SimplePlayer();
			simplePlayer.setPlayerId(bean.getPlayerId());
			simplePlayer.setUserId(bean.getUserId());
			simplePlayer.setServerId(bean.getServerId());
			simplePlayer.setCurrentServerId(bean.getCurrentServerId());
			simplePlayer.setPlayerName(bean.getPlayerName());
			simplePlayer.setHead(bean.getHead());
			simplePlayer.setSkinId(bean.getSkinId());
			simplePlayer.setLv(bean.getLv());
			simplePlayer.setVipLv(bean.getVipLv());
			simplePlayer.setSumMoney(bean.getSumMoney());
			simplePlayer.setForce(bean.getForce());
			simplePlayer.setPfId(bean.getPfId());
			simplePlayer.setSdkId(bean.getSdkId());
			simplePlayer.setLanguage(bean.getLanguage());
			simplePlayer.setCreateTime(bean.getCreateTime());
			simplePlayer.setLoginTime(bean.getLoginTime());
			simplePlayer.setLoginOutTime(bean.getLoginOutTime());
			simplePlayer.setLoginBannedTime(bean.getLoginBannedTime());
			for (int i = 0, len = bean.getAttBaseListList().size(); i < len; i++) {
				Long value = bean.getAttBaseListList().get(i);
				simplePlayer.getAttBaseList().add(value);
			}

			return simplePlayer;
		} catch (Exception e) {
			log.error("解析一个SimplePlayerBean数据", e);
			return null;
		}
	}

}