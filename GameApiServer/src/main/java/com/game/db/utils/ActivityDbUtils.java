package com.game.db.utils;

import com.game.activity.structs.Activity;
import com.game.db.proto.DbProto.ActivityBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换Activity存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class ActivityDbUtils {


	private static Logger log = LogManager.getLogger(ActivityDbUtils.class);

	/**
	 * 构建一个可存储的Activity数据
	* @param data
	* @return ActivityBean
	 */
	public static ActivityBean buildActivityBean(Activity data) {
		try {
			ActivityBean.Builder builder = ActivityBean.newBuilder();
			builder.setId(data.getId());
			builder.setActivityType(data.getActivityType());
			builder.setClose(data.isClose());
			builder.setVersion(data.getVersion());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Activity数据", e);
			return null;
		}
	}

	/**
	 * 解析一个ActivityBean数据
	* @param data
	* @return Activity
	 */
	public static Activity parseFromActivity(byte[] data) {
		try {
			ActivityBean bean = ActivityBean.parseFrom(data);
			Activity activity = new Activity();
			activity.setId(bean.getId());
			activity.setActivityType(bean.getActivityType());
			activity.setClose(bean.getClose());
			activity.setVersion(bean.getVersion());
			return activity;
		} catch (Exception e) {
			log.error("解析一个ActivityBean数据", e);
			return null;
		}
	}

}