package com.game.db.utils;

import com.game.db.proto.DbProto.PlayerShopBean;
import com.game.player.structs.PlayerShop;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 转换PlayerShop存储格式工具
 *
 * @author zhangzhen
 * @time 2024/06/01 01:00
 */
public class PlayerShopDbUtils {


	private static Logger log = LogManager.getLogger(PlayerShopDbUtils.class);

	/**
	 * 构建一个可存储的PlayerShop数据
	* @param data
	* @return PlayerShopBean
	 */
	public static PlayerShopBean buildPlayerShopBean(PlayerShop data) {
		try {
			PlayerShopBean.Builder builder = PlayerShopBean.newBuilder();
			for (int i = 0, len = data.getPayShopBuyIdList().size(); i < len; i++) {
				Integer value = data.getPayShopBuyIdList().get(i);
				builder.addPayShopBuyIdList(value);
			}
			for (int i = 0, len = data.getBuyCountList().size(); i < len; i++) {
				Integer value = data.getBuyCountList().get(i);
				builder.addBuyCountList(value);
			}
			builder.setPlayerId(data.getPlayerId());
			return builder.build();
		} catch (Exception e) {
			log.error("构建一个可存储的PlayerShop数据", e);
			return null;
		}
	}

	/**
	 * 解析一个PlayerShopBean数据
	* @param data
	* @return PlayerShop
	 */
	public static PlayerShop parseFromPlayerShop(byte[] data) {
		try {
			PlayerShopBean bean = PlayerShopBean.parseFrom(data);
			PlayerShop playerShop = new PlayerShop();
			for (int i = 0, len = bean.getPayShopBuyIdListList().size(); i < len; i++) {
				Integer value = bean.getPayShopBuyIdListList().get(i);
				playerShop.getPayShopBuyIdList().add(value);
			}

			for (int i = 0, len = bean.getBuyCountListList().size(); i < len; i++) {
				Integer value = bean.getBuyCountListList().get(i);
				playerShop.getBuyCountList().add(value);
			}

			playerShop.setPlayerId(bean.getPlayerId());
			return playerShop;
		} catch (Exception e) {
			log.error("解析一个PlayerShopBean数据", e);
			return null;
		}
	}

}