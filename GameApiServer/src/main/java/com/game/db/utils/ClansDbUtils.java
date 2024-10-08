package com.game.db.utils;

import com.game.clans.structs.*;
import com.game.db.proto.DbProto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换Clans存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class ClansDbUtils {


	private static Logger log = LogManager.getLogger(ClansDbUtils.class);

	/**
	 * 构建一个可存储的Clans数据
	* @param data
	* @return ClansBean
	 */
	public static ClansBean buildClansBean(Clans data) {
		try {
			ClansBean.Builder builder = ClansBean.newBuilder();
			builder.setClansId(data.getClansId());
			builder.setIcon(data.getIcon());
			builder.setClansName(data.getClansName());
			builder.setNotice(data.getNotice());
			builder.setLv(data.getLv());
			builder.setExp(data.getExp());
			builder.setMasterId(data.getMasterId());
			builder.setMasterTime(data.getMasterTime());
			builder.setMailTime(data.getMailTime());
			builder.setCreateTime(data.getCreateTime());
			builder.setJoinType(data.getJoinType());
			builder.setCheckLv(data.getCheckLv());
			builder.setActive(data.getActive());
			builder.setTodayActive(data.getTodayActive());
			builder.setTodayExp(data.getTodayExp());
			for (int i = 0, len = data.getApplyList().size(); i < len; i++) {
				ClansApply value = data.getApplyList().get(i);
				builder.addApplyList(buildClansApplyBean(value));
			}
			for (int i = 0, len = data.getMemberList().size(); i < len; i++) {
				Member value = data.getMemberList().get(i);
				builder.addMemberList(buildMemberBean(value));
			}
			for (int i = 0, len = data.getQuestList().size(); i < len; i++) {
				ClansQuest value = data.getQuestList().get(i);
				builder.addQuestList(buildClansQuestBean(value));
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Clans数据", e);
			return null;
		}
	}

	/**
	 * 解析一个ClansBean数据
	* @param data
	* @return Clans
	 */
	public static Clans parseFromClans(byte[] data) {
		try {
			ClansBean bean = ClansBean.parseFrom(data);
			Clans clans = new Clans();
			clans.setClansId(bean.getClansId());
			clans.setIcon(bean.getIcon());
			clans.setClansName(bean.getClansName());
			clans.setNotice(bean.getNotice());
			clans.setLv(bean.getLv());
			clans.setExp(bean.getExp());
			clans.setMasterId(bean.getMasterId());
			clans.setMasterTime(bean.getMasterTime());
			clans.setMailTime(bean.getMailTime());
			clans.setCreateTime(bean.getCreateTime());
			clans.setJoinType(bean.getJoinType());
			clans.setCheckLv(bean.getCheckLv());
			clans.setActive(bean.getActive());
			clans.setTodayActive(bean.getTodayActive());
			clans.setTodayExp(bean.getTodayExp());
			for (int i = 0, len = bean.getApplyListList().size(); i < len; i++) {
				ClansApplyBean value = bean.getApplyListList().get(i);
				clans.getApplyList().add(parseFromClansApply(value));
			}

			for (int i = 0, len = bean.getMemberListList().size(); i < len; i++) {
				MemberBean value = bean.getMemberListList().get(i);
				clans.getMemberList().add(parseFromMember(value));
			}

			for (int i = 0, len = bean.getQuestListList().size(); i < len; i++) {
				ClansQuestBean value = bean.getQuestListList().get(i);
				clans.getQuestList().add(parseFromClansQuest(value));
			}

			return clans;
		} catch (Exception e) {
			log.error("解析一个ClansBean数据", e);
			return null;
		}
	}

	/**
	 * 构建一个可存储的ClansApply数据
	 * 
	 * @param data
	 */
	private static ClansApplyBean buildClansApplyBean(ClansApply data) {
		try {
			ClansApplyBean.Builder builder = ClansApplyBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setPlayerId(data.getPlayerId());
				builder.setServerId(data.getServerId());
				builder.setPlayerName(data.getPlayerName());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的ClansApply数据", e);
			return null;
		}
	}

	/**
	 * 解析一个ClansApplyBean数据
	 * 
	 * @param bean
	 * @returnClansApply
	 */
	private static ClansApply parseFromClansApply(ClansApplyBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			ClansApply clansApply = new ClansApply();
			clansApply.setPlayerId(bean.getPlayerId());
			clansApply.setServerId(bean.getServerId());
			clansApply.setPlayerName(bean.getPlayerName());
			return clansApply;
		} catch (Exception e) {
			log.error("解析一个ClansApplyBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的ClansQuest数据
	 * 
	 * @param data
	 */
	private static ClansQuestBean buildClansQuestBean(ClansQuest data) {
		try {
			ClansQuestBean.Builder builder = ClansQuestBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setId(data.getId());
				builder.setConfigId(data.getConfigId());
				builder.setEndTime(data.getEndTime());
				builder.setState(data.getState());
				builder.setHelperNum(data.getHelperNum());
				builder.setPlayerId(data.getPlayerId());
				builder.setServerId(data.getServerId());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的ClansQuest数据", e);
			return null;
		}
	}

	/**
	 * 解析一个ClansQuestBean数据
	 * 
	 * @param bean
	 * @returnClansQuest
	 */
	private static ClansQuest parseFromClansQuest(ClansQuestBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			ClansQuest clansQuest = new ClansQuest();
			clansQuest.setId(bean.getId());
			clansQuest.setConfigId(bean.getConfigId());
			clansQuest.setEndTime(bean.getEndTime());
			clansQuest.setState(bean.getState());
			clansQuest.setHelperNum(bean.getHelperNum());
			clansQuest.setPlayerId(bean.getPlayerId());
			clansQuest.setServerId(bean.getServerId());
			return clansQuest;
		} catch (Exception e) {
			log.error("解析一个ClansQuestBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的Member数据
	 * 
	 * @param data
	 */
	private static MemberBean buildMemberBean(Member data) {
		try {
			MemberBean.Builder builder = MemberBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setPlayerId(data.getPlayerId());
				builder.setPlayerName(data.getPlayerName());
				builder.setServerId(data.getServerId());
				builder.setJob(data.getJob());
				builder.setActive(data.getActive());
				for (int i = 0, len = data.getActiveList().size(); i < len; i++) {
					MemberActiveData value = data.getActiveList().get(i);
					builder.addActiveList(buildMemberActiveDataBean(value));
				}
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Member数据", e);
			return null;
		}
	}

	/**
	 * 解析一个MemberBean数据
	 * 
	 * @param bean
	 * @returnMember
	 */
	private static Member parseFromMember(MemberBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			Member member = new Member();
			member.setPlayerId(bean.getPlayerId());
			member.setPlayerName(bean.getPlayerName());
			member.setServerId(bean.getServerId());
			member.setJob(bean.getJob());
			member.setActive(bean.getActive());
			for (int i = 0, len = bean.getActiveListList().size(); i < len; i++) {
				MemberActiveDataBean value = bean.getActiveListList().get(i);
				member.getActiveList().add(parseFromMemberActiveData(value));
			}

			return member;
		} catch (Exception e) {
			log.error("解析一个MemberBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的MemberActiveData数据
	 * 
	 * @param data
	 */
	private static MemberActiveDataBean buildMemberActiveDataBean(MemberActiveData data) {
		try {
			MemberActiveDataBean.Builder builder = MemberActiveDataBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setNum(data.getNum());
				builder.setTime(data.getTime());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的MemberActiveData数据", e);
			return null;
		}
	}

	/**
	 * 解析一个MemberActiveDataBean数据
	 * 
	 * @param bean
	 * @returnMemberActiveData
	 */
	private static MemberActiveData parseFromMemberActiveData(MemberActiveDataBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			MemberActiveData memberActiveData = new MemberActiveData();
			memberActiveData.setNum(bean.getNum());
			memberActiveData.setTime(bean.getTime());
			return memberActiveData;
		} catch (Exception e) {
			log.error("解析一个MemberActiveDataBean数据", e);
			return null;
		}
	}
}