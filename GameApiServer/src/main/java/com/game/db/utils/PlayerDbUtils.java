package com.game.db.utils;

import com.game.db.proto.DbProto.*;
import com.game.player.structs.*;
import com.google.protobuf.ByteString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换Player存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class PlayerDbUtils {


	private static Logger log = LogManager.getLogger(PlayerDbUtils.class);

	/**
	 * 构建一个可存储的Player数据
	* @param data
	* @return PlayerBean
	 */
	public static PlayerBean buildPlayerBean(Player data) {
		try {
			PlayerBean.Builder builder = PlayerBean.newBuilder();
			builder.setPlayerId(data.getPlayerId());
			builder.setUserId(data.getUserId());
			builder.setUserName(data.getUserName());
			builder.setAccount(data.getAccount());
			builder.setPlayerName(data.getPlayerName());
			builder.setHead(data.getHead());
			builder.setSkinId(data.getSkinId());
			builder.setGender(data.getGender());
			builder.setLv(data.getLv());
			builder.setVipLv(data.getVipLv());
			builder.setExp(data.getExp());
			builder.setSdkId(data.getSdkId());
			builder.setPfId(data.getPfId());
			builder.setCreateServerId(data.getCreateServerId());
			builder.setCurrentServerId(data.getCurrentServerId());
			builder.setCurrentRoomServerId(data.getCurrentRoomServerId());
			builder.setLoginDay(data.getLoginDay());
			builder.setLoginDayCon(data.getLoginDayCon());
			builder.setVer(data.getVer());
			builder.setGuideId(data.getGuideId());
			builder.setGuideNodeId(data.getGuideNodeId());
			builder.setBannedTime(data.getBannedTime());
			builder.setCreateTime(data.getCreateTime());
			builder.setLoginTime(data.getLoginTime());
			builder.setLoginOutTime(data.getLoginOutTime());
			builder.setOnlineTime(data.getOnlineTime());
			builder.setEverydayRefreshTime(data.getEverydayRefreshTime());
			builder.setEverydaySiDianRefreshTime(data.getEverydaySiDianRefreshTime());
			builder.setForce(data.getForce());
			builder.setSettingData(buildPlayerSettingDataBean(data.getSettingData()));
			builder.setSmelterData(buildSmelterDataBean(data.getSmelterData()));
			builder.setPotentialData(buildPotentialDataBean(data.getPotentialData()));
			for (int i = 0, len = data.getFunctionOpenList().size(); i < len; i++) {
				Integer value = data.getFunctionOpenList().get(i);
				builder.addFunctionOpenList(value);
			}
			for (int i = 0, len = data.getBookIdList().size(); i < len; i++) {
				Integer value = data.getBookIdList().get(i);
				builder.addBookIdList(value);
			}
			for (int i = 0, len = data.getExchangeList().size(); i < len; i++) {
				Integer value = data.getExchangeList().get(i);
				builder.addExchangeList(value);
			}
			for (int i = 0, len = data.getAttBaseList().size(); i < len; i++) {
				Long value = data.getAttBaseList().get(i);
				builder.addAttBaseList(value);
			}
			for (int i = 0, len = data.getEquipList().size(); i < len; i++) {
				Equip value = data.getEquipList().get(i);
				builder.addEquipList(buildEquipBean(value));
			}
			for (int i = 0, len = data.getHeroList().size(); i < len; i++) {
				Hero value = data.getHeroList().get(i);
				builder.addHeroList(buildHeroBean(value));
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Player数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerBean数据
	* @param data
	* @return Player
	 */
	public static Player parseFromPlayer(byte[] data) {
		try {
			PlayerBean bean = PlayerBean.parseFrom(data);
			Player player = new Player();
			player.setPlayerId(bean.getPlayerId());
			player.setUserId(bean.getUserId());
			player.setUserName(bean.getUserName());
			player.setAccount(bean.getAccount());
			player.setPlayerName(bean.getPlayerName());
			player.setHead(bean.getHead());
			player.setSkinId(bean.getSkinId());
			player.setGender(bean.getGender());
			player.setLv(bean.getLv());
			player.setVipLv(bean.getVipLv());
			player.setExp(bean.getExp());
			player.setSdkId(bean.getSdkId());
			player.setPfId(bean.getPfId());
			player.setCreateServerId(bean.getCreateServerId());
			player.setCurrentServerId(bean.getCurrentServerId());
			player.setCurrentRoomServerId(bean.getCurrentRoomServerId());
			player.setLoginDay(bean.getLoginDay());
			player.setLoginDayCon(bean.getLoginDayCon());
			player.setVer(bean.getVer());
			player.setGuideId(bean.getGuideId());
			player.setGuideNodeId(bean.getGuideNodeId());
			player.setBannedTime(bean.getBannedTime());
			player.setCreateTime(bean.getCreateTime());
			player.setLoginTime(bean.getLoginTime());
			player.setLoginOutTime(bean.getLoginOutTime());
			player.setOnlineTime(bean.getOnlineTime());
			player.setEverydayRefreshTime(bean.getEverydayRefreshTime());
			player.setEverydaySiDianRefreshTime(bean.getEverydaySiDianRefreshTime());
			player.setForce(bean.getForce());
			player.setSettingData(parseFromPlayerSettingData(bean.getSettingData()));
			player.setSmelterData(parseFromSmelterData(bean.getSmelterData()));
			player.setPotentialData(parseFromPotentialData(bean.getPotentialData()));
			for (int i = 0, len = bean.getFunctionOpenListList().size(); i < len; i++) {
				Integer value = bean.getFunctionOpenListList().get(i);
				player.getFunctionOpenList().add(value);
			}

			for (int i = 0, len = bean.getBookIdListList().size(); i < len; i++) {
				Integer value = bean.getBookIdListList().get(i);
				player.getBookIdList().add(value);
			}

			for (int i = 0, len = bean.getExchangeListList().size(); i < len; i++) {
				Integer value = bean.getExchangeListList().get(i);
				player.getExchangeList().add(value);
			}

			for (int i = 0, len = bean.getAttBaseListList().size(); i < len; i++) {
				Long value = bean.getAttBaseListList().get(i);
				player.getAttBaseList().add(value);
			}

			for (int i = 0, len = bean.getEquipListList().size(); i < len; i++) {
				EquipBean value = bean.getEquipListList().get(i);
				player.getEquipList().add(parseFromEquip(value));
			}

			for (int i = 0, len = bean.getHeroListList().size(); i < len; i++) {
				HeroBean value = bean.getHeroListList().get(i);
				player.getHeroList().add(parseFromHero(value));
			}

			return player;
		} catch (Exception e) {
			log.error("解析一个PlayerBean数据", e);
			return null;
		}
	}

	/**
	 * 构建一个可存储的Equip数据
	 * 
	 * @param data
	 */
	private static EquipBean buildEquipBean(Equip data) {
		try {
			EquipBean.Builder builder = EquipBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				for (int i = 0, len = data.getAttributeList().size(); i < len; i++) {
					Long value = data.getAttributeList().get(i);
					builder.addAttributeList(value);
				}
				builder.setLevel(data.getLevel());
				builder.setQuality(data.getQuality());
				builder.setId(data.getId());
				builder.setConfigId(data.getConfigId());
				builder.setNum(data.getNum());
				builder.setDelTime(data.getDelTime());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Equip数据", e);
			return null;
		}
	}

	/**
	 * 解析一个EquipBean数据
	 * 
	 * @param bean
	 * @returnEquip
	 */
	private static Equip parseFromEquip(EquipBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			Equip equip = new Equip();
			for (int i = 0, len = bean.getAttributeListList().size(); i < len; i++) {
				Long value = bean.getAttributeListList().get(i);
				equip.getAttributeList().add(value);
			}

			equip.setLevel(bean.getLevel());
			equip.setQuality(bean.getQuality());
			equip.setId(bean.getId());
			equip.setConfigId(bean.getConfigId());
			equip.setNum(bean.getNum());
			equip.setDelTime(bean.getDelTime());
			return equip;
		} catch (Exception e) {
			log.error("解析一个EquipBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的Hero数据
	 * 
	 * @param data
	 */
	private static HeroBean buildHeroBean(Hero data) {
		try {
			HeroBean.Builder builder = HeroBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setLv(data.getLv());
				builder.setStar(data.getStar());
				builder.setSkillId(data.getSkillId());
				for (int i = 0, len = data.getPassiveSkillLvList().size(); i < len; i++) {
					Integer value = data.getPassiveSkillLvList().get(i);
					builder.addPassiveSkillLvList(value);
				}
				builder.setQuality(data.getQuality());
				builder.setLocked(data.isLocked());
				builder.setId(data.getId());
				builder.setConfigId(data.getConfigId());
				builder.setNum(data.getNum());
				builder.setDelTime(data.getDelTime());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Hero数据", e);
			return null;
		}
	}

	/**
	 * 解析一个HeroBean数据
	 * 
	 * @param bean
	 * @returnHero
	 */
	private static Hero parseFromHero(HeroBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			Hero hero = new Hero();
			hero.setLv(bean.getLv());
			hero.setStar(bean.getStar());
			hero.setSkillId(bean.getSkillId());
			for (int i = 0, len = bean.getPassiveSkillLvListList().size(); i < len; i++) {
				Integer value = bean.getPassiveSkillLvListList().get(i);
				hero.getPassiveSkillLvList().add(value);
			}

			hero.setQuality(bean.getQuality());
			hero.setLocked(bean.getLocked());
			hero.setId(bean.getId());
			hero.setConfigId(bean.getConfigId());
			hero.setNum(bean.getNum());
			hero.setDelTime(bean.getDelTime());
			return hero;
		} catch (Exception e) {
			log.error("解析一个HeroBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的PlayerSettingData数据
	 * 
	 * @param data
	 */
	private static PlayerSettingDataBean buildPlayerSettingDataBean(PlayerSettingData data) {
		try {
			PlayerSettingDataBean.Builder builder = PlayerSettingDataBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setChangeNameNum(data.getChangeNameNum());
				if(data.getClientSettingInfo() != null) {
					builder.setClientSettingInfo(ByteString.copyFrom(data.getClientSettingInfo()));
				}
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PlayerSettingData数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerSettingDataBean数据
	 * 
	 * @param bean
	 * @returnPlayerSettingData
	 */
	private static PlayerSettingData parseFromPlayerSettingData(PlayerSettingDataBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			PlayerSettingData playerSettingData = new PlayerSettingData();
			playerSettingData.setChangeNameNum(bean.getChangeNameNum());
			playerSettingData.setClientSettingInfo(bean.getClientSettingInfo().toByteArray());
			return playerSettingData;
		} catch (Exception e) {
			log.error("解析一个PlayerSettingDataBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的Potential数据
	 * 
	 * @param data
	 */
	private static PotentialBean buildPotentialBean(Potential data) {
		try {
			PotentialBean.Builder builder = PotentialBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setLv(data.getLv());
				builder.setQuality(data.getQuality());
				builder.setPos(data.getPos());
				for (int i = 0, len = data.getAttrList().size(); i < len; i++) {
					Long value = data.getAttrList().get(i);
					builder.addAttrList(value);
				}
				builder.setId(data.getId());
				builder.setConfigId(data.getConfigId());
				builder.setNum(data.getNum());
				builder.setDelTime(data.getDelTime());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Potential数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PotentialBean数据
	 * 
	 * @param bean
	 * @returnPotential
	 */
	private static Potential parseFromPotential(PotentialBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			Potential potential = new Potential();
			potential.setLv(bean.getLv());
			potential.setQuality(bean.getQuality());
			potential.setPos(bean.getPos());
			for (int i = 0, len = bean.getAttrListList().size(); i < len; i++) {
				Long value = bean.getAttrListList().get(i);
				potential.getAttrList().add(value);
			}

			potential.setId(bean.getId());
			potential.setConfigId(bean.getConfigId());
			potential.setNum(bean.getNum());
			potential.setDelTime(bean.getDelTime());
			return potential;
		} catch (Exception e) {
			log.error("解析一个PotentialBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的PotentialData数据
	 * 
	 * @param data
	 */
	private static PotentialDataBean buildPotentialDataBean(PotentialData data) {
		try {
			PotentialDataBean.Builder builder = PotentialDataBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				for (int i = 0, len = data.getPotentialList().size(); i < len; i++) {
					Potential value = data.getPotentialList().get(i);
					builder.addPotentialList(buildPotentialBean(value));
				}
				builder.setPotentialLv(data.getPotentialLv());
				builder.setPotentialExp(data.getPotentialExp());
				builder.setIdentifyExp(data.getIdentifyExp());
				for (int i = 0, len = data.getStimulationPotentialList().size(); i < len; i++) {
					Potential value = data.getStimulationPotentialList().get(i);
					builder.addStimulationPotentialList(buildPotentialBean(value));
				}
				for (int i = 0, len = data.getSkillList().size(); i < len; i++) {
					Integer value = data.getSkillList().get(i);
					builder.addSkillList(value);
				}
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PotentialData数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PotentialDataBean数据
	 * 
	 * @param bean
	 * @returnPotentialData
	 */
	private static PotentialData parseFromPotentialData(PotentialDataBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			PotentialData potentialData = new PotentialData();
			for (int i = 0, len = bean.getPotentialListList().size(); i < len; i++) {
				PotentialBean value = bean.getPotentialListList().get(i);
				potentialData.getPotentialList().add(parseFromPotential(value));
			}

			potentialData.setPotentialLv(bean.getPotentialLv());
			potentialData.setPotentialExp(bean.getPotentialExp());
			potentialData.setIdentifyExp(bean.getIdentifyExp());
			for (int i = 0, len = bean.getStimulationPotentialListList().size(); i < len; i++) {
				PotentialBean value = bean.getStimulationPotentialListList().get(i);
				potentialData.getStimulationPotentialList().add(parseFromPotential(value));
			}

			for (int i = 0, len = bean.getSkillListList().size(); i < len; i++) {
				Integer value = bean.getSkillListList().get(i);
				potentialData.getSkillList().add(value);
			}

			return potentialData;
		} catch (Exception e) {
			log.error("解析一个PotentialDataBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的SmelterData数据
	 * 
	 * @param data
	 */
	private static SmelterDataBean buildSmelterDataBean(SmelterData data) {
		try {
			SmelterDataBean.Builder builder = SmelterDataBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setLevel(data.getLevel());
				builder.setLvUpEndTime(data.getLvUpEndTime());
				for (int i = 0, len = data.getSmelterEquip().size(); i < len; i++) {
					Equip value = data.getSmelterEquip().get(i);
					builder.addSmelterEquip(buildEquipBean(value));
				}
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的SmelterData数据", e);
			return null;
		}
	}

	/**
	 * 解析一个SmelterDataBean数据
	 * 
	 * @param bean
	 * @returnSmelterData
	 */
	private static SmelterData parseFromSmelterData(SmelterDataBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			SmelterData smelterData = new SmelterData();
			smelterData.setLevel(bean.getLevel());
			smelterData.setLvUpEndTime(bean.getLvUpEndTime());
			for (int i = 0, len = bean.getSmelterEquipList().size(); i < len; i++) {
				EquipBean value = bean.getSmelterEquipList().get(i);
				smelterData.getSmelterEquip().add(parseFromEquip(value));
			}

			return smelterData;
		} catch (Exception e) {
			log.error("解析一个SmelterDataBean数据", e);
			return null;
		}
	}
}