package com.game.db.utils;

import com.game.db.proto.DbProto.HeroBean;
import com.game.db.proto.DbProto.PlayerHeroBean;
import com.game.player.structs.Hero;
import com.game.player.structs.PlayerHero;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换PlayerHero存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class PlayerHeroDbUtils {


	private static Logger log = LogManager.getLogger(PlayerHeroDbUtils.class);

	/**
	 * 构建一个可存储的PlayerHero数据
	* @param data
	* @return PlayerHeroBean
	 */
	public static PlayerHeroBean buildPlayerHeroBean(PlayerHero data) {
		try {
			PlayerHeroBean.Builder builder = PlayerHeroBean.newBuilder();
			for (int i = 0, len = data.getBattledHeroList().size(); i < len; i++) {
				Hero value = data.getBattledHeroList().get(i);
				builder.addBattledHeroList(buildHeroBean(value));
			}
			for (int i = 0, len = data.getHeroList().size(); i < len; i++) {
				Hero value = data.getHeroList().get(i);
				builder.addHeroList(buildHeroBean(value));
			}
			for (int i = 0, len = data.getActiveFetterList().size(); i < len; i++) {
				Integer value = data.getActiveFetterList().get(i);
				builder.addActiveFetterList(value);
			}
			for (int i = 0, len = data.getRefreshHeroList().size(); i < len; i++) {
				Hero value = data.getRefreshHeroList().get(i);
				builder.addRefreshHeroList(buildHeroBean(value));
			}
			for (int i = 0, len = data.getRefreshGotIdList().size(); i < len; i++) {
				Long value = data.getRefreshGotIdList().get(i);
				builder.addRefreshGotIdList(value);
			}
			for (Integer value : data.getGainedHeroSet()) {
				builder.addGainedHeroSet(value);
			}
			builder.setGridNum(data.getGridNum());
			builder.setRefreshTimes(data.getRefreshTimes());
			builder.setAdRefreshNum(data.getAdRefreshNum());
			builder.setMaxAwardChoose(data.getMaxAwardChoose());
			builder.setMaxAwardRefreshTimes(data.getMaxAwardRefreshTimes());
			builder.setPassword(data.getPassword());
			builder.setForceUnlockTime(data.getForceUnlockTime());
			builder.setPlayerId(data.getPlayerId());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PlayerHero数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerHeroBean数据
	* @param data
	* @return PlayerHero
	 */
	public static PlayerHero parseFromPlayerHero(byte[] data) {
		try {
			PlayerHeroBean bean = PlayerHeroBean.parseFrom(data);
			PlayerHero playerHero = new PlayerHero();
			for (int i = 0, len = bean.getBattledHeroListList().size(); i < len; i++) {
				HeroBean value = bean.getBattledHeroListList().get(i);
				playerHero.getBattledHeroList().add(parseFromHero(value));
			}

			for (int i = 0, len = bean.getHeroListList().size(); i < len; i++) {
				HeroBean value = bean.getHeroListList().get(i);
				playerHero.getHeroList().add(parseFromHero(value));
			}

			for (int i = 0, len = bean.getActiveFetterListList().size(); i < len; i++) {
				Integer value = bean.getActiveFetterListList().get(i);
				playerHero.getActiveFetterList().add(value);
			}

			for (int i = 0, len = bean.getRefreshHeroListList().size(); i < len; i++) {
				HeroBean value = bean.getRefreshHeroListList().get(i);
				playerHero.getRefreshHeroList().add(parseFromHero(value));
			}

			for (int i = 0, len = bean.getRefreshGotIdListList().size(); i < len; i++) {
				Long value = bean.getRefreshGotIdListList().get(i);
				playerHero.getRefreshGotIdList().add(value);
			}

			for (int i = 0, len = bean.getGainedHeroSetList().size(); i < len; i++) {
				Integer value = bean.getGainedHeroSetList().get(i);
				playerHero.getGainedHeroSet().add(value);
			}

			playerHero.setGridNum(bean.getGridNum());
			playerHero.setRefreshTimes(bean.getRefreshTimes());
			playerHero.setAdRefreshNum(bean.getAdRefreshNum());
			playerHero.setMaxAwardChoose(bean.getMaxAwardChoose());
			playerHero.setMaxAwardRefreshTimes(bean.getMaxAwardRefreshTimes());
			playerHero.setPassword(bean.getPassword());
			playerHero.setForceUnlockTime(bean.getForceUnlockTime());
			playerHero.setPlayerId(bean.getPlayerId());
			return playerHero;
		} catch (Exception e) {
			log.error("解析一个PlayerHeroBean数据", e);
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
}