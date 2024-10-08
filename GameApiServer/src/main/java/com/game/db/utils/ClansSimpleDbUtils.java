package com.game.db.utils;

import com.game.clans.structs.ClansSimple;
import com.game.db.proto.DbProto.ClansSimpleBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换ClansSimple存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class ClansSimpleDbUtils {


	private static Logger log = LogManager.getLogger(ClansSimpleDbUtils.class);

	/**
	 * 构建一个可存储的ClansSimple数据
	* @param data
	* @return ClansSimpleBean
	 */
	public static ClansSimpleBean buildClansSimpleBean(ClansSimple data) {
		try {
			ClansSimpleBean.Builder builder = ClansSimpleBean.newBuilder();
			builder.setClansId(data.getClansId());
			builder.setIcon(data.getIcon());
			builder.setClansName(data.getClansName());
			builder.setNotice(data.getNotice());
			builder.setLv(data.getLv());
			builder.setExp(data.getExp());
			builder.setMasterId(data.getMasterId());
			builder.setMemberNum(data.getMemberNum());
			builder.setCreateTime(data.getCreateTime());
			builder.setActive(data.getActive());
			builder.setTodayActive(data.getTodayActive());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的ClansSimple数据", e);
			return null;
		}
	}

	/**
	 * 解析一个ClansSimpleBean数据
	* @param data
	* @return ClansSimple
	 */
	public static ClansSimple parseFromClansSimple(byte[] data) {
		try {
			ClansSimpleBean bean = ClansSimpleBean.parseFrom(data);
			ClansSimple clansSimple = new ClansSimple();
			clansSimple.setClansId(bean.getClansId());
			clansSimple.setIcon(bean.getIcon());
			clansSimple.setClansName(bean.getClansName());
			clansSimple.setNotice(bean.getNotice());
			clansSimple.setLv(bean.getLv());
			clansSimple.setExp(bean.getExp());
			clansSimple.setMasterId(bean.getMasterId());
			clansSimple.setMemberNum(bean.getMemberNum());
			clansSimple.setCreateTime(bean.getCreateTime());
			clansSimple.setActive(bean.getActive());
			clansSimple.setTodayActive(bean.getTodayActive());
			return clansSimple;
		} catch (Exception e) {
			log.error("解析一个ClansSimpleBean数据", e);
			return null;
		}
	}

}