package com.game.db.utils;

import com.game.db.proto.DbProto.PlayerFriendBean;
import com.game.player.structs.PlayerFriend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换PlayerFriend存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class PlayerFriendDbUtils {


	private static Logger log = LogManager.getLogger(PlayerFriendDbUtils.class);

	/**
	 * 构建一个可存储的PlayerFriend数据
	* @param data
	* @return PlayerFriendBean
	 */
	public static PlayerFriendBean buildPlayerFriendBean(PlayerFriend data) {
		try {
			PlayerFriendBean.Builder builder = PlayerFriendBean.newBuilder();
			builder.setLastTime(data.getLastTime());
			for (int i = 0, len = data.getSendList().size(); i < len; i++) {
				Long value = data.getSendList().get(i);
				builder.addSendList(value);
			}
			for (int i = 0, len = data.getReceiveList().size(); i < len; i++) {
				Long value = data.getReceiveList().get(i);
				builder.addReceiveList(value);
			}
			for (int i = 0, len = data.getGetList().size(); i < len; i++) {
				Long value = data.getGetList().get(i);
				builder.addGetList(value);
			}
			builder.setPlayerId(data.getPlayerId());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PlayerFriend数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerFriendBean数据
	* @param data
	* @return PlayerFriend
	 */
	public static PlayerFriend parseFromPlayerFriend(byte[] data) {
		try {
			PlayerFriendBean bean = PlayerFriendBean.parseFrom(data);
			PlayerFriend playerFriend = new PlayerFriend();
			playerFriend.setLastTime(bean.getLastTime());
			for (int i = 0, len = bean.getSendListList().size(); i < len; i++) {
				Long value = bean.getSendListList().get(i);
				playerFriend.getSendList().add(value);
			}

			for (int i = 0, len = bean.getReceiveListList().size(); i < len; i++) {
				Long value = bean.getReceiveListList().get(i);
				playerFriend.getReceiveList().add(value);
			}

			for (int i = 0, len = bean.getGetListList().size(); i < len; i++) {
				Long value = bean.getGetListList().get(i);
				playerFriend.getGetList().add(value);
			}

			playerFriend.setPlayerId(bean.getPlayerId());
			return playerFriend;
		} catch (Exception e) {
			log.error("解析一个PlayerFriendBean数据", e);
			return null;
		}
	}

}