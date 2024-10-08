package com.game.db.utils;

import com.game.db.proto.DbProto.LuckyDrawBean;
import com.game.db.proto.DbProto.PlayerActivityBean;
import com.game.db.proto.DbProto.PlayerActivityYueKaSuperBean;
import com.game.player.structs.LuckyDraw;
import com.game.player.structs.PlayerActivity;
import com.game.player.structs.PlayerActivityYueKaSuper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map.Entry;

/**
 * 转换PlayerActivity存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class PlayerActivityDbUtils {


	private static Logger log = LogManager.getLogger(PlayerActivityDbUtils.class);

	/**
	 * 构建一个可存储的PlayerActivity数据
	* @param data
	* @return PlayerActivityBean
	 */
	public static PlayerActivityBean buildPlayerActivityBean(PlayerActivity data) {
		try {
			PlayerActivityBean.Builder builder = PlayerActivityBean.newBuilder();
			for (Entry<Integer, Integer> entry : data.getActivityVerMap().entrySet()) {
				builder.putActivityVerMap(entry.getKey(), entry.getValue());
			}
			for (int i = 0, len = data.getYueKaSuperDataList().size(); i < len; i++) {
				PlayerActivityYueKaSuper value = data.getYueKaSuperDataList().get(i);
				builder.addYueKaSuperDataList(buildPlayerActivityYueKaSuperBean(value));
			}
			for (int i = 0, len = data.getLuckyDrawList().size(); i < len; i++) {
				LuckyDraw value = data.getLuckyDrawList().get(i);
				builder.addLuckyDrawList(buildLuckyDrawBean(value));
			}
			builder.setPlayerId(data.getPlayerId());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PlayerActivity数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerActivityBean数据
	* @param data
	* @return PlayerActivity
	 */
	public static PlayerActivity parseFromPlayerActivity(byte[] data) {
		try {
			PlayerActivityBean bean = PlayerActivityBean.parseFrom(data);
			PlayerActivity playerActivity = new PlayerActivity();
			for (Entry<Integer, Integer> entry : bean.getActivityVerMapMap().entrySet()) {
				playerActivity.getActivityVerMap().put(entry.getKey(), entry.getValue());
			}

			for (int i = 0, len = bean.getYueKaSuperDataListList().size(); i < len; i++) {
				PlayerActivityYueKaSuperBean value = bean.getYueKaSuperDataListList().get(i);
				playerActivity.getYueKaSuperDataList().add(parseFromPlayerActivityYueKaSuper(value));
			}

			for (int i = 0, len = bean.getLuckyDrawListList().size(); i < len; i++) {
				LuckyDrawBean value = bean.getLuckyDrawListList().get(i);
				playerActivity.getLuckyDrawList().add(parseFromLuckyDraw(value));
			}

			playerActivity.setPlayerId(bean.getPlayerId());
			return playerActivity;
		} catch (Exception e) {
			log.error("解析一个PlayerActivityBean数据", e);
			return null;
		}
	}

	/**
	 * 构建一个可存储的LuckyDraw数据
	 * 
	 * @param data
	 */
	private static LuckyDrawBean buildLuckyDrawBean(LuckyDraw data) {
		try {
			LuckyDrawBean.Builder builder = LuckyDrawBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setId(data.getId());
				builder.setCount(data.getCount());
				builder.setTenCount(data.getTenCount());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的LuckyDraw数据", e);
			return null;
		}
	}

	/**
	 * 解析一个LuckyDrawBean数据
	 * 
	 * @param bean
	 * @returnLuckyDraw
	 */
	private static LuckyDraw parseFromLuckyDraw(LuckyDrawBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			LuckyDraw luckyDraw = new LuckyDraw();
			luckyDraw.setId(bean.getId());
			luckyDraw.setCount(bean.getCount());
			luckyDraw.setTenCount(bean.getTenCount());
			return luckyDraw;
		} catch (Exception e) {
			log.error("解析一个LuckyDrawBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的PlayerActivityYueKaSuper数据
	 * 
	 * @param data
	 */
	private static PlayerActivityYueKaSuperBean buildPlayerActivityYueKaSuperBean(PlayerActivityYueKaSuper data) {
		try {
			PlayerActivityYueKaSuperBean.Builder builder = PlayerActivityYueKaSuperBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setId(data.getId());
				builder.setWeekExp(data.getWeekExp());
				for (int i = 0, len = data.getPayList().size(); i < len; i++) {
					Integer value = data.getPayList().get(i);
					builder.addPayList(value);
				}
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PlayerActivityYueKaSuper数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerActivityYueKaSuperBean数据
	 * 
	 * @param bean
	 * @returnPlayerActivityYueKaSuper
	 */
	private static PlayerActivityYueKaSuper parseFromPlayerActivityYueKaSuper(PlayerActivityYueKaSuperBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			PlayerActivityYueKaSuper playerActivityYueKaSuper = new PlayerActivityYueKaSuper();
			playerActivityYueKaSuper.setId(bean.getId());
			playerActivityYueKaSuper.setWeekExp(bean.getWeekExp());
			for (int i = 0, len = bean.getPayListList().size(); i < len; i++) {
				Integer value = bean.getPayListList().get(i);
				playerActivityYueKaSuper.getPayList().add(value);
			}

			return playerActivityYueKaSuper;
		} catch (Exception e) {
			log.error("解析一个PlayerActivityYueKaSuperBean数据", e);
			return null;
		}
	}
}