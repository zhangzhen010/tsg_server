package com.game.player.structs;

import com.game.redis.structs.RedisKey;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家商店数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 18:37
 */
@Document(collection = "playerShop")
public class PlayerShop extends PlayerOther {

    /**
     * 商店数据map key=商店类型 ShopType value=shop
     */
//	private Map<Integer, Shop> shopMap = new HashMap<>();
    /**
     * 已购买充值商店id列表（首充双倍逻辑）（这都是兼容以前的逻辑，后面会删除，改到正式充值逻辑去）
     */
    private List<Integer> payShopBuyIdList = new ArrayList<>();
    /**
     * 每日购买次数，商店商品配置id，今日已购买次数，商店商品配置id...
     */
    private List<Integer> buyCountList = new ArrayList<>();

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER_SHOP;
    }

    public List<Integer> getPayShopBuyIdList() {
        return payShopBuyIdList;
    }

    public void setPayShopBuyIdList(List<Integer> payShopBuyIdList) {
        this.payShopBuyIdList = payShopBuyIdList;
    }

    public List<Integer> getBuyCountList() {
        return buyCountList;
    }

    public void setBuyCountList(List<Integer> buyCountList) {
        this.buyCountList = buyCountList;
    }

//	public Map<Integer, Shop> getShopMap() {
//		return shopMap;
//	}
//
//	public void setShopMap(Map<Integer, Shop> shopMap) {
//		this.shopMap = shopMap;
//	}

}
