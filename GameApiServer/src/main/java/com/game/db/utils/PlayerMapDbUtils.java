package com.game.db.utils;

import com.game.db.proto.DbProto.PlayerMapBean;
import com.game.player.structs.PlayerMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换PlayerMap存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class PlayerMapDbUtils {


	private static Logger log = LogManager.getLogger(PlayerMapDbUtils.class);

	/**
	 * 构建一个可存储的PlayerMap数据
	* @param data
	* @return PlayerMapBean
	 */
	public static PlayerMapBean buildPlayerMapBean(PlayerMap data) {
		try {
			PlayerMapBean.Builder builder = PlayerMapBean.newBuilder();
			builder.setStageId(data.getStageId());
			builder.setPlayerId(data.getPlayerId());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PlayerMap数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerMapBean数据
	* @param data
	* @return PlayerMap
	 */
	public static PlayerMap parseFromPlayerMap(byte[] data) {
		try {
			PlayerMapBean bean = PlayerMapBean.parseFrom(data);
			PlayerMap playerMap = new PlayerMap();
			playerMap.setStageId(bean.getStageId());
			playerMap.setPlayerId(bean.getPlayerId());
			return playerMap;
		} catch (Exception e) {
			log.error("解析一个PlayerMapBean数据", e);
			return null;
		}
	}

}