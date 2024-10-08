package com.game.db.utils;

import com.game.db.proto.DbProto.FriendBean;
import com.game.db.proto.DbProto.FriendPublicDataBean;
import com.game.friend.structs.Friend;
import com.game.friend.structs.FriendPublicData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换FriendPublicData存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class FriendPublicDataDbUtils {


	private static Logger log = LogManager.getLogger(FriendPublicDataDbUtils.class);

	/**
	 * 构建一个可存储的FriendPublicData数据
	* @param data
	* @return FriendPublicDataBean
	 */
	public static FriendPublicDataBean buildFriendPublicDataBean(FriendPublicData data) {
		try {
			FriendPublicDataBean.Builder builder = FriendPublicDataBean.newBuilder();
			builder.setPlayerId(data.getPlayerId());
			builder.setHelperNum(data.getHelperNum());
			builder.setWeekLastResetTime(data.getWeekLastResetTime());
			for (int i = 0, len = data.getFriendList().size(); i < len; i++) {
				Friend value = data.getFriendList().get(i);
				builder.addFriendList(buildFriendBean(value));
			}
			for (int i = 0, len = data.getApplyList().size(); i < len; i++) {
				Friend value = data.getApplyList().get(i);
				builder.addApplyList(buildFriendBean(value));
			}
			for (int i = 0, len = data.getBlackList().size(); i < len; i++) {
				Friend value = data.getBlackList().get(i);
				builder.addBlackList(buildFriendBean(value));
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的FriendPublicData数据", e);
			return null;
		}
	}

	/**
	 * 解析一个FriendPublicDataBean数据
	* @param data
	* @return FriendPublicData
	 */
	public static FriendPublicData parseFromFriendPublicData(byte[] data) {
		try {
			FriendPublicDataBean bean = FriendPublicDataBean.parseFrom(data);
			FriendPublicData friendPublicData = new FriendPublicData();
			friendPublicData.setPlayerId(bean.getPlayerId());
			friendPublicData.setHelperNum(bean.getHelperNum());
			friendPublicData.setWeekLastResetTime(bean.getWeekLastResetTime());
			for (int i = 0, len = bean.getFriendListList().size(); i < len; i++) {
				FriendBean value = bean.getFriendListList().get(i);
				friendPublicData.getFriendList().add(parseFromFriend(value));
			}

			for (int i = 0, len = bean.getApplyListList().size(); i < len; i++) {
				FriendBean value = bean.getApplyListList().get(i);
				friendPublicData.getApplyList().add(parseFromFriend(value));
			}

			for (int i = 0, len = bean.getBlackListList().size(); i < len; i++) {
				FriendBean value = bean.getBlackListList().get(i);
				friendPublicData.getBlackList().add(parseFromFriend(value));
			}

			return friendPublicData;
		} catch (Exception e) {
			log.error("解析一个FriendPublicDataBean数据", e);
			return null;
		}
	}

	/**
	 * 构建一个可存储的Friend数据
	 * 
	 * @param data
	 */
	private static FriendBean buildFriendBean(Friend data) {
		try {
			FriendBean.Builder builder = FriendBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setPlayerId(data.getPlayerId());
				builder.setServerId(data.getServerId());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Friend数据", e);
			return null;
		}
	}

	/**
	 * 解析一个FriendBean数据
	 * 
	 * @param bean
	 * @returnFriend
	 */
	private static Friend parseFromFriend(FriendBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			Friend friend = new Friend();
			friend.setPlayerId(bean.getPlayerId());
			friend.setServerId(bean.getServerId());
			return friend;
		} catch (Exception e) {
			log.error("解析一个FriendBean数据", e);
			return null;
		}
	}
}