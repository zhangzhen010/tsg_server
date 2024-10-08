package com.game.db.utils;

import com.game.db.proto.DbProto.PlayerQuestBean;
import com.game.db.proto.DbProto.QuestBean;
import com.game.db.proto.DbProto.QuestTargetDataBean;
import com.game.db.proto.DbProto.QuestTargetDataListBean;
import com.game.player.structs.PlayerQuest;
import com.game.player.structs.Quest;
import com.game.player.structs.QuestTargetData;
import com.game.player.structs.QuestTargetDataList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map.Entry;

/**
 * 转换PlayerQuest存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class PlayerQuestDbUtils {


	private static Logger log = LogManager.getLogger(PlayerQuestDbUtils.class);

	/**
	 * 构建一个可存储的PlayerQuest数据
	* @param data
	* @return PlayerQuestBean
	 */
	public static PlayerQuestBean buildPlayerQuestBean(PlayerQuest data) {
		try {
			PlayerQuestBean.Builder builder = PlayerQuestBean.newBuilder();
			builder.setWeekLastResetTime(data.getWeekLastResetTime());
			builder.setMonthLastResetTime(data.getMonthLastResetTime());
			for (Entry<Integer, QuestTargetDataList> entry : data.getQuestTargetDataMap().entrySet()) {
				builder.putQuestTargetDataMap(entry.getKey(), buildQuestTargetDataListBean(entry.getValue()));
			}
			for (int i = 0, len = data.getQuestList().size(); i < len; i++) {
				Quest value = data.getQuestList().get(i);
				builder.addQuestList(buildQuestBean(value));
			}
			for (Integer value : data.getGetIdSet()) {
				builder.addGetIdSet(value);
			}
			builder.setPlayerId(data.getPlayerId());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PlayerQuest数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerQuestBean数据
	* @param data
	* @return PlayerQuest
	 */
	public static PlayerQuest parseFromPlayerQuest(byte[] data) {
		try {
			PlayerQuestBean bean = PlayerQuestBean.parseFrom(data);
			PlayerQuest playerQuest = new PlayerQuest();
			playerQuest.setWeekLastResetTime(bean.getWeekLastResetTime());
			playerQuest.setMonthLastResetTime(bean.getMonthLastResetTime());
			for (Entry<Integer, QuestTargetDataListBean> entry : bean.getQuestTargetDataMapMap().entrySet()) {
				playerQuest.getQuestTargetDataMap().put(entry.getKey(), parseFromQuestTargetDataList(entry.getValue()));
			}

			for (int i = 0, len = bean.getQuestListList().size(); i < len; i++) {
				QuestBean value = bean.getQuestListList().get(i);
				playerQuest.getQuestList().add(parseFromQuest(value));
			}

			for (int i = 0, len = bean.getGetIdSetList().size(); i < len; i++) {
				Integer value = bean.getGetIdSetList().get(i);
				playerQuest.getGetIdSet().add(value);
			}

			playerQuest.setPlayerId(bean.getPlayerId());
			return playerQuest;
		} catch (Exception e) {
			log.error("解析一个PlayerQuestBean数据", e);
			return null;
		}
	}

	/**
	 * 构建一个可存储的Quest数据
	 * 
	 * @param data
	 */
	private static QuestBean buildQuestBean(Quest data) {
		try {
			QuestBean.Builder builder = QuestBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setConfigId(data.getConfigId());
				builder.setSkip(data.isSkip());
				for (int i = 0, len = data.getTargetList().size(); i < len; i++) {
					Integer value = data.getTargetList().get(i);
					builder.addTargetList(value);
				}
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Quest数据", e);
			return null;
		}
	}

	/**
	 * 解析一个QuestBean数据
	 * 
	 * @param bean
	 * @returnQuest
	 */
	private static Quest parseFromQuest(QuestBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			Quest quest = new Quest();
			quest.setConfigId(bean.getConfigId());
			quest.setSkip(bean.getSkip());
			for (int i = 0, len = bean.getTargetListList().size(); i < len; i++) {
				Integer value = bean.getTargetListList().get(i);
				quest.getTargetList().add(value);
			}

			return quest;
		} catch (Exception e) {
			log.error("解析一个QuestBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的QuestTargetData数据
	 * 
	 * @param data
	 */
	private static QuestTargetDataBean buildQuestTargetDataBean(QuestTargetData data) {
		try {
			QuestTargetDataBean.Builder builder = QuestTargetDataBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setParam1(data.getParam1());
				builder.setParam2(data.getParam2());
				builder.setParam3(data.getParam3());
				builder.setParam4(data.getParam4());
				builder.setValue(data.getValue());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的QuestTargetData数据", e);
			return null;
		}
	}

	/**
	 * 解析一个QuestTargetDataBean数据
	 * 
	 * @param bean
	 * @returnQuestTargetData
	 */
	private static QuestTargetData parseFromQuestTargetData(QuestTargetDataBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			QuestTargetData questTargetData = new QuestTargetData();
			questTargetData.setParam1(bean.getParam1());
			questTargetData.setParam2(bean.getParam2());
			questTargetData.setParam3(bean.getParam3());
			questTargetData.setParam4(bean.getParam4());
			questTargetData.setValue(bean.getValue());
			return questTargetData;
		} catch (Exception e) {
			log.error("解析一个QuestTargetDataBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的QuestTargetDataList数据
	 * 
	 * @param data
	 */
	private static QuestTargetDataListBean buildQuestTargetDataListBean(QuestTargetDataList data) {
		try {
			QuestTargetDataListBean.Builder builder = QuestTargetDataListBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				for (int i = 0, len = data.getQuestTargetList().size(); i < len; i++) {
					QuestTargetData value = data.getQuestTargetList().get(i);
					builder.addQuestTargetList(buildQuestTargetDataBean(value));
				}
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的QuestTargetDataList数据", e);
			return null;
		}
	}

	/**
	 * 解析一个QuestTargetDataListBean数据
	 * 
	 * @param bean
	 * @returnQuestTargetDataList
	 */
	private static QuestTargetDataList parseFromQuestTargetDataList(QuestTargetDataListBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			QuestTargetDataList questTargetDataList = new QuestTargetDataList();
			for (int i = 0, len = bean.getQuestTargetListList().size(); i < len; i++) {
				QuestTargetDataBean value = bean.getQuestTargetListList().get(i);
				questTargetDataList.getQuestTargetList().add(parseFromQuestTargetData(value));
			}

			return questTargetDataList;
		} catch (Exception e) {
			log.error("解析一个QuestTargetDataListBean数据", e);
			return null;
		}
	}
}