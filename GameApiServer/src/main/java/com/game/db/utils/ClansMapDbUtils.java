package com.game.db.utils;

import com.game.clans.structs.ClansMap;
import com.game.db.proto.DbProto.ClansMapBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换ClansMap存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class ClansMapDbUtils {


	private static Logger log = LogManager.getLogger(ClansMapDbUtils.class);

	/**
	 * 构建一个可存储的ClansMap数据
	* @param data
	* @return ClansMapBean
	 */
	public static ClansMapBean buildClansMapBean(ClansMap data) {
		try {
			ClansMapBean.Builder builder = ClansMapBean.newBuilder();
			builder.setClansId(data.getClansId());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的ClansMap数据", e);
			return null;
		}
	}

	/**
	 * 解析一个ClansMapBean数据
	* @param data
	* @return ClansMap
	 */
	public static ClansMap parseFromClansMap(byte[] data) {
		try {
			ClansMapBean bean = ClansMapBean.parseFrom(data);
			ClansMap clansMap = new ClansMap();
			clansMap.setClansId(bean.getClansId());
			return clansMap;
		} catch (Exception e) {
			log.error("解析一个ClansMapBean数据", e);
			return null;
		}
	}

}