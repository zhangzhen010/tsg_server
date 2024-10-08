package com.game.db.utils;

import com.game.clans.structs.PlayerClansData;
import com.game.db.proto.DbProto.PlayerClansDataBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换PlayerClansData存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class PlayerClansDataDbUtils {


	private static Logger log = LogManager.getLogger(PlayerClansDataDbUtils.class);

	/**
	 * 构建一个可存储的PlayerClansData数据
	* @param data
	* @return PlayerClansDataBean
	 */
	public static PlayerClansDataBean buildPlayerClansDataBean(PlayerClansData data) {
		try {
			PlayerClansDataBean.Builder builder = PlayerClansDataBean.newBuilder();
			builder.setPlayerId(data.getPlayerId());
			builder.setClansId(data.getClansId());
			builder.setClansName(data.getClansName());
			builder.setJob(data.getJob());
			builder.setLv(data.getLv());
			builder.setTime(data.getTime());
			builder.setJoinTime(data.getJoinTime());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PlayerClansData数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerClansDataBean数据
	* @param data
	* @return PlayerClansData
	 */
	public static PlayerClansData parseFromPlayerClansData(byte[] data) {
		try {
			PlayerClansDataBean bean = PlayerClansDataBean.parseFrom(data);
			PlayerClansData playerClansData = new PlayerClansData();
			playerClansData.setPlayerId(bean.getPlayerId());
			playerClansData.setClansId(bean.getClansId());
			playerClansData.setClansName(bean.getClansName());
			playerClansData.setJob(bean.getJob());
			playerClansData.setLv(bean.getLv());
			playerClansData.setTime(bean.getTime());
			playerClansData.setJoinTime(bean.getJoinTime());
			return playerClansData;
		} catch (Exception e) {
			log.error("解析一个PlayerClansDataBean数据", e);
			return null;
		}
	}

}