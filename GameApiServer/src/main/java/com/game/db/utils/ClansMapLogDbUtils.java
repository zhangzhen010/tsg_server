package com.game.db.utils;

import com.game.clans.structs.ClansMapLog;
import com.game.db.proto.DbProto.ClansMapLogBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换ClansMapLog存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class ClansMapLogDbUtils {


	private static Logger log = LogManager.getLogger(ClansMapLogDbUtils.class);

	/**
	 * 构建一个可存储的ClansMapLog数据
	* @param data
	* @return ClansMapLogBean
	 */
	public static ClansMapLogBean buildClansMapLogBean(ClansMapLog data) {
		try {
			ClansMapLogBean.Builder builder = ClansMapLogBean.newBuilder();
			builder.setClansId(data.getClansId());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的ClansMapLog数据", e);
			return null;
		}
	}

	/**
	 * 解析一个ClansMapLogBean数据
	* @param data
	* @return ClansMapLog
	 */
	public static ClansMapLog parseFromClansMapLog(byte[] data) {
		try {
			ClansMapLogBean bean = ClansMapLogBean.parseFrom(data);
			ClansMapLog clansMapLog = new ClansMapLog();
			clansMapLog.setClansId(bean.getClansId());
			return clansMapLog;
		} catch (Exception e) {
			log.error("解析一个ClansMapLogBean数据", e);
			return null;
		}
	}

}