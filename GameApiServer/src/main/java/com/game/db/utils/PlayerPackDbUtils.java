package com.game.db.utils;

import com.game.db.proto.DbProto.*;
import com.game.player.structs.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map.Entry;

/**
 * 转换PlayerPack存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class PlayerPackDbUtils {


	private static Logger log = LogManager.getLogger(PlayerPackDbUtils.class);

	/**
	 * 构建一个可存储的PlayerPack数据
	* @param data
	* @return PlayerPackBean
	 */
	public static PlayerPackBean buildPlayerPackBean(PlayerPack data) {
		try {
			PlayerPackBean.Builder builder = PlayerPackBean.newBuilder();
			for (Entry<Integer, Long> entry : data.getResourceMap().entrySet()) {
				builder.putResourceMap(entry.getKey(), entry.getValue());
			}
			for (int i = 0, len = data.getPropList().size(); i < len; i++) {
				Prop value = data.getPropList().get(i);
				builder.addPropList(buildPropBean(value));
			}
			for (int i = 0, len = data.getConcurrentPropList().size(); i < len; i++) {
				Prop value = data.getConcurrentPropList().get(i);
				builder.addConcurrentPropList(buildPropBean(value));
			}
			for (int i = 0, len = data.getSkillList().size(); i < len; i++) {
				Skill value = data.getSkillList().get(i);
				builder.addSkillList(buildSkillBean(value));
			}
			for (int i = 0, len = data.getCardList().size(); i < len; i++) {
				Card value = data.getCardList().get(i);
				builder.addCardList(buildCardBean(value));
			}
			builder.setPlayerId(data.getPlayerId());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PlayerPack数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerPackBean数据
	* @param data
	* @return PlayerPack
	 */
	public static PlayerPack parseFromPlayerPack(byte[] data) {
		try {
			PlayerPackBean bean = PlayerPackBean.parseFrom(data);
			PlayerPack playerPack = new PlayerPack();
			for (Entry<Integer, Long> entry : bean.getResourceMapMap().entrySet()) {
				playerPack.getResourceMap().put(entry.getKey(), entry.getValue());
			}

			for (int i = 0, len = bean.getPropListList().size(); i < len; i++) {
				PropBean value = bean.getPropListList().get(i);
				playerPack.getPropList().add(parseFromProp(value));
			}

			for (int i = 0, len = bean.getConcurrentPropListList().size(); i < len; i++) {
				PropBean value = bean.getConcurrentPropListList().get(i);
				playerPack.getConcurrentPropList().add(parseFromProp(value));
			}

			for (int i = 0, len = bean.getSkillListList().size(); i < len; i++) {
				SkillBean value = bean.getSkillListList().get(i);
				playerPack.getSkillList().add(parseFromSkill(value));
			}

			for (int i = 0, len = bean.getCardListList().size(); i < len; i++) {
				CardBean value = bean.getCardListList().get(i);
				playerPack.getCardList().add(parseFromCard(value));
			}

			playerPack.setPlayerId(bean.getPlayerId());
			return playerPack;
		} catch (Exception e) {
			log.error("解析一个PlayerPackBean数据", e);
			return null;
		}
	}

	/**
	 * 构建一个可存储的Card数据
	 * 
	 * @param data
	 */
	private static CardBean buildCardBean(Card data) {
		try {
			CardBean.Builder builder = CardBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setCard(buildGachaCardBean(data.getCard()));
				builder.setId(data.getId());
				builder.setConfigId(data.getConfigId());
				builder.setNum(data.getNum());
				builder.setDelTime(data.getDelTime());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Card数据", e);
			return null;
		}
	}

	/**
	 * 解析一个CardBean数据
	 * 
	 * @param bean
	 * @returnCard
	 */
	private static Card parseFromCard(CardBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			Card card = new Card();
			card.setCard(parseFromGachaCard(bean.getCard()));
			card.setId(bean.getId());
			card.setConfigId(bean.getConfigId());
			card.setNum(bean.getNum());
			card.setDelTime(bean.getDelTime());
			return card;
		} catch (Exception e) {
			log.error("解析一个CardBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的CardAttribute数据
	 * 
	 * @param data
	 */
	private static CardAttributeBean buildCardAttributeBean(CardAttribute data) {
		try {
			CardAttributeBean.Builder builder = CardAttributeBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setTraitType(data.getTraitType());
				builder.setValue(data.getValue());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的CardAttribute数据", e);
			return null;
		}
	}

	/**
	 * 解析一个CardAttributeBean数据
	 * 
	 * @param bean
	 * @returnCardAttribute
	 */
	private static CardAttribute parseFromCardAttribute(CardAttributeBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			CardAttribute cardAttribute = new CardAttribute();
			cardAttribute.setTraitType(bean.getTraitType());
			cardAttribute.setValue(bean.getValue());
			return cardAttribute;
		} catch (Exception e) {
			log.error("解析一个CardAttributeBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的GachaCard数据
	 * 
	 * @param data
	 */
	private static GachaCardBean buildGachaCardBean(GachaCard data) {
		try {
			GachaCardBean.Builder builder = GachaCardBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setId(data.getId());
				builder.setGachaCardTemplateId(data.getGachaCardTemplateId());
				builder.setMintPublicKey(data.getMintPublicKey());
				builder.setMetaPublicKey(data.getMetaPublicKey());
				builder.setName(data.getName());
				builder.setDescription(data.getDescription());
				builder.setImage(data.getImage());
				builder.setCost(data.getCost());
				builder.setUsd(data.getUsd());
				builder.setLevel(data.getLevel());
				builder.setQuality(data.getQuality());
				builder.setGachaPoolId(data.getGachaPoolId());
				builder.setOwnerPlayerId(data.getOwnerPlayerId());
				builder.setBurnCandyRatio(data.getBurnCandyRatio());
				builder.setBurnFtRatio(data.getBurnFtRatio());
				builder.setBurn(data.isBurn());
				for (int i = 0, len = data.getAttributes().size(); i < len; i++) {
					CardAttribute value = data.getAttributes().get(i);
					builder.addAttributes(buildCardAttributeBean(value));
				}
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的GachaCard数据", e);
			return null;
		}
	}

	/**
	 * 解析一个GachaCardBean数据
	 * 
	 * @param bean
	 * @returnGachaCard
	 */
	private static GachaCard parseFromGachaCard(GachaCardBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			GachaCard gachaCard = new GachaCard();
			gachaCard.setId(bean.getId());
			gachaCard.setGachaCardTemplateId(bean.getGachaCardTemplateId());
			gachaCard.setMintPublicKey(bean.getMintPublicKey());
			gachaCard.setMetaPublicKey(bean.getMetaPublicKey());
			gachaCard.setName(bean.getName());
			gachaCard.setDescription(bean.getDescription());
			gachaCard.setImage(bean.getImage());
			gachaCard.setCost(bean.getCost());
			gachaCard.setUsd(bean.getUsd());
			gachaCard.setLevel(bean.getLevel());
			gachaCard.setQuality(bean.getQuality());
			gachaCard.setGachaPoolId(bean.getGachaPoolId());
			gachaCard.setOwnerPlayerId(bean.getOwnerPlayerId());
			gachaCard.setBurnCandyRatio(bean.getBurnCandyRatio());
			gachaCard.setBurnFtRatio(bean.getBurnFtRatio());
			gachaCard.setBurn(bean.getBurn());
			for (int i = 0, len = bean.getAttributesList().size(); i < len; i++) {
				CardAttributeBean value = bean.getAttributesList().get(i);
				gachaCard.getAttributes().add(parseFromCardAttribute(value));
			}

			return gachaCard;
		} catch (Exception e) {
			log.error("解析一个GachaCardBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的Prop数据
	 * 
	 * @param data
	 */
	private static PropBean buildPropBean(Prop data) {
		try {
			PropBean.Builder builder = PropBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setId(data.getId());
				builder.setConfigId(data.getConfigId());
				builder.setNum(data.getNum());
				builder.setDelTime(data.getDelTime());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Prop数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PropBean数据
	 * 
	 * @param bean
	 * @returnProp
	 */
	private static Prop parseFromProp(PropBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			Prop prop = new Prop();
			prop.setId(bean.getId());
			prop.setConfigId(bean.getConfigId());
			prop.setNum(bean.getNum());
			prop.setDelTime(bean.getDelTime());
			return prop;
		} catch (Exception e) {
			log.error("解析一个PropBean数据", e);
			return null;
		}
	}
	/**
	 * 构建一个可存储的Skill数据
	 * 
	 * @param data
	 */
	private static SkillBean buildSkillBean(Skill data) {
		try {
			SkillBean.Builder builder = SkillBean.newBuilder();
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (data == null) {
				builder.setNull(true);
			} else {
				builder.setId(data.getId());
				builder.setConfigId(data.getConfigId());
				builder.setNum(data.getNum());
				builder.setDelTime(data.getDelTime());
			}
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的Skill数据", e);
			return null;
		}
	}

	/**
	 * 解析一个SkillBean数据
	 * 
	 * @param bean
	 * @returnSkill
	 */
	private static Skill parseFromSkill(SkillBean bean) {
		try {
			// 数据对象为null就设置null状态为true，反序列化的时候也返回null
			if (bean.getNull()) {
				return null;
			}
			Skill skill = new Skill();
			skill.setId(bean.getId());
			skill.setConfigId(bean.getConfigId());
			skill.setNum(bean.getNum());
			skill.setDelTime(bean.getDelTime());
			return skill;
		} catch (Exception e) {
			log.error("解析一个SkillBean数据", e);
			return null;
		}
	}
}