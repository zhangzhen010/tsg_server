package com.game.db.utils;

import com.game.db.proto.DbProto.PayRecordBean;
import com.game.db.proto.DbProto.PlayerPayBean;
import com.game.player.structs.PayRecord;
import com.game.player.structs.PlayerPay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map.Entry;

/**
 * 转换PlayerPay存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class PlayerPayDbUtils {


	private static Logger log = LogManager.getLogger(PlayerPayDbUtils.class);

	/**
	 * 构建一个可存储的PlayerPay数据
	* @param data
	* @return PlayerPayBean
	 */
	public static PlayerPayBean buildPlayerPayBean(PlayerPay data) {
		try {
			PlayerPayBean.Builder builder = PlayerPayBean.newBuilder();
			builder.setSumMoney(data.getSumMoney());
			builder.setPayCount(data.getPayCount());
			builder.setLastPayTime(data.getLastPayTime());
			builder.setYuekaEndTime(data.getYuekaEndTime());
			builder.setYuekaAwardTime(data.getYuekaAwardTime());
			for (int i = 0, len = data.getRecordList().size(); i < len; i++) {
				PayRecord value = data.getRecordList().get(i);
				builder.addRecordList(buildPayRecordBean(value));
			}
			for (Entry<Integer, Integer> entry : data.getPayNumMap().entrySet()) {
				builder.putPayNumMap(entry.getKey(), entry.getValue());
			}
			builder.setPlayerId(data.getPlayerId());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PlayerPay数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerPayBean数据
	* @param data
	* @return PlayerPay
	 */
	public static PlayerPay parseFromPlayerPay(byte[] data) {
		try {
			PlayerPayBean bean = PlayerPayBean.parseFrom(data);
			PlayerPay playerPay = new PlayerPay();
			playerPay.setSumMoney(bean.getSumMoney());
			playerPay.setPayCount(bean.getPayCount());
			playerPay.setLastPayTime(bean.getLastPayTime());
			playerPay.setYuekaEndTime(bean.getYuekaEndTime());
			playerPay.setYuekaAwardTime(bean.getYuekaAwardTime());
			for (int i = 0, len = bean.getRecordListList().size(); i < len; i++) {
				PayRecordBean value = bean.getRecordListList().get(i);
				playerPay.getRecordList().add(parseFromPayRecord(value));
			}

			for (Entry<Integer, Integer> entry : bean.getPayNumMapMap().entrySet()) {
				playerPay.getPayNumMap().put(entry.getKey(), entry.getValue());
			}

			playerPay.setPlayerId(bean.getPlayerId());
			return playerPay;
		} catch (Exception e) {
			log.error("解析一个PlayerPayBean数据", e);
			return null;
		}
	}

	/**
	 * 构建一个可存储的PayRecord数据
	 * 
	 * @param data
	 */
	private static PayRecordBean buildPayRecordBean(PayRecord data) {
		try {
			PayRecordBean.Builder builder = PayRecordBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setMoney(data.getMoney());
				builder.setPayTime(data.getPayTime());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PayRecord数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PayRecordBean数据
	 * 
	 * @param bean
	 * @returnPayRecord
	 */
	private static PayRecord parseFromPayRecord(PayRecordBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			PayRecord payRecord = new PayRecord();
			payRecord.setMoney(bean.getMoney());
			payRecord.setPayTime(bean.getPayTime());
			return payRecord;
		} catch (Exception e) {
			log.error("解析一个PayRecordBean数据", e);
			return null;
		}
	}
}