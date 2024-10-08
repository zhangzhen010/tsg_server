package com.game.db.utils;

import com.game.db.proto.DbProto.ChatDataBean;
import com.game.db.proto.DbProto.PlayerChatBean;
import com.game.player.structs.ChatData;
import com.game.player.structs.PlayerChat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换PlayerChat存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class PlayerChatDbUtils {


	private static Logger log = LogManager.getLogger(PlayerChatDbUtils.class);

	/**
	 * 构建一个可存储的PlayerChat数据
	* @param data
	* @return PlayerChatBean
	 */
	public static PlayerChatBean buildPlayerChatBean(PlayerChat data) {
		try {
			PlayerChatBean.Builder builder = PlayerChatBean.newBuilder();
			builder.setChatBannedTime(data.getChatBannedTime());
			for (int i = 0, len = data.getOffLineChatList().size(); i < len; i++) {
				ChatData value = data.getOffLineChatList().get(i);
				builder.addOffLineChatList(buildChatDataBean(value));
			}
			builder.setPlayerId(data.getPlayerId());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PlayerChat数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerChatBean数据
	* @param data
	* @return PlayerChat
	 */
	public static PlayerChat parseFromPlayerChat(byte[] data) {
		try {
			PlayerChatBean bean = PlayerChatBean.parseFrom(data);
			PlayerChat playerChat = new PlayerChat();
			playerChat.setChatBannedTime(bean.getChatBannedTime());
			for (int i = 0, len = bean.getOffLineChatListList().size(); i < len; i++) {
				ChatDataBean value = bean.getOffLineChatListList().get(i);
				playerChat.getOffLineChatList().add(parseFromChatData(value));
			}

			playerChat.setPlayerId(bean.getPlayerId());
			return playerChat;
		} catch (Exception e) {
			log.error("解析一个PlayerChatBean数据", e);
			return null;
		}
	}

	/**
	 * 构建一个可存储的ChatData数据
	 * 
	 * @param data
	 */
	private static ChatDataBean buildChatDataBean(ChatData data) {
		try {
			ChatDataBean.Builder builder = ChatDataBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setPlayerId(data.getPlayerId());
				builder.setChannelId(data.getChannelId());
				builder.setCid(data.getCid());
				builder.setChannelType(data.getChannelType());
				builder.setPlayerName(data.getPlayerName());
				builder.setServerId(data.getServerId());
				builder.setToServerId(data.getToServerId());
				builder.setHead(data.getHead());
				builder.setHeadBorder(data.getHeadBorder());
				builder.setContent(data.getContent());
				builder.setLv(data.getLv());
				builder.setTime(data.getTime());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的ChatData数据", e);
			return null;
		}
	}

	/**
	 * 解析一个ChatDataBean数据
	 * 
	 * @param bean
	 * @returnChatData
	 */
	private static ChatData parseFromChatData(ChatDataBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			ChatData chatData = new ChatData();
			chatData.setPlayerId(bean.getPlayerId());
			chatData.setChannelId(bean.getChannelId());
			chatData.setCid(bean.getCid());
			chatData.setChannelType(bean.getChannelType());
			chatData.setPlayerName(bean.getPlayerName());
			chatData.setServerId(bean.getServerId());
			chatData.setToServerId(bean.getToServerId());
			chatData.setHead(bean.getHead());
			chatData.setHeadBorder(bean.getHeadBorder());
			chatData.setContent(bean.getContent());
			chatData.setLv(bean.getLv());
			chatData.setTime(bean.getTime());
			return chatData;
		} catch (Exception e) {
			log.error("解析一个ChatDataBean数据", e);
			return null;
		}
	}
}