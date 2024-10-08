package com.game.pack.manager;

import com.alibaba.fastjson2.JSON;
import com.game.award.manager.AwardManager;
import com.game.award.utils.ItemTypeUtils;
import com.game.bean.proto.BeanProto;
import com.game.data.bean.B_data_Bean;
import com.game.data.bean.B_item_Bean;
import com.game.data.define.MyDefineData;
import com.game.data.define.MyDefineItemType;
import com.game.data.manager.DataManager;
import com.game.data.myenum.MyEnumResourceId;
import com.game.logs.structs.LogFactory;
import com.game.player.manager.PlayerManager;
import com.game.player.manager.PlayerOtherManager;
import com.game.player.structs.*;
import com.game.prop.structs.PropId;
import com.game.redis.manager.RedisManager;
import com.game.redis.structs.RedisKey;
import com.game.utils.GameUtil;
import com.game.utils.ID;
import com.game.utils.ListUtils;
import com.game.utils.TimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * 背包管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/7 10:11
 */
@Component
@Log4j2
public class PackManager {

    private @Resource PlayerOtherManager playerOtherManager;
    @Lazy
    private @Resource PlayerManager playerManager;
    private @Resource DataManager dataManager;
    private @Resource LogFactory logFactory;
    @Lazy
    private @Resource AwardManager awardManager;
    private @Resource ItemTypeUtils itemTypeUtils;
    private @Resource RedisManager redisManager;

    private static final int MAXNUM = 20;

    /**
     * 生成物品
     *
     * @param player
     * @param configId
     * @param creator
     * @param <T>
     * @return
     */
    private <T extends Item> T createItem(WebPlayer player, Integer configId, Function<B_item_Bean, T> creator) {
        try {
            B_item_Bean itemBean = dataManager.c_item_Container.getMap().get(configId);
            if (itemBean == null) {
                return null;
            }
            T item = creator.apply(itemBean);
            item.setId(ID.getId());
            item.setConfigId(itemBean.getId());
            item.setNum(1);
            // 设置过期时间
            if (itemBean.getDelTimeSec() > 0) {
                item.setDelTime(System.currentTimeMillis() + itemBean.getDelTimeSec() * TimeUtil.MILLIS);
            }
            return item;
        } catch (Exception e) {
            log.error("生成物品异常:" + e);
            return null;
        }
    }

    /**
     * 生成道具
     *
     * @param player
     */
    public Prop createProp(WebPlayer player, Integer configId) {
        return createItem(player, configId, prop -> new Prop());
    }

    /**
     * 根据道具配置id获取背包道具
     *
     * @return
     */
    private <T extends Item> T getItemByConfigId(WebPlayer player, List<T> itemList, Integer configId, Class<T> itemType) {
        try {
            for (int i = 0; i < itemList.size(); i++) {
                T item = itemList.get(i);
                if (item.getConfigId().equals(configId)) {
                    return item;
                }
            }
            return null;
        } catch (Exception e) {
            log.error(String.format("根据道具配置id获取背包[%s]异常", itemType.getSimpleName()), e);
            return null;
        }
    }

    /**
     * 根据唯一id获取背包道具
     *
     * @return
     */
    public Prop getPropById(WebPlayer player, long propId) {
        PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
        Prop prop = playerPack.getPropMap().get(propId);
        if (prop == null) {
            prop = playerPack.getConcurrentPropMap().get(propId);
        }
        return prop;
    }

    /**
     * 根据道具配置id获取背包道具
     *
     * @param player
     * @param configId
     * @return
     */
    public Prop getPropByConfigId(WebPlayer player, Integer configId) {
        try {
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
            Prop prop = getItemByConfigId(player, playerPack.getPropList(), configId, Prop.class);
            if (prop == null) {
                prop = getItemByConfigId(player, playerPack.getConcurrentPropList(), configId, Prop.class);
            }
            return prop;
        } catch (Exception e) {
            log.error("根据道具配置id获取背包道具", e);
            return null;
        }
    }

    /**
     * 通过configId往背包中添加资源和物品
     *
     * @param player    玩家对象
     * @param configId  配置id
     * @param num       添加数量
     * @param logReason 日志类型
     */
    public void addItemByConfigId(WebPlayer player, Integer configId, int num, Integer logReason) {
        try {
            Integer itemType = itemTypeUtils.getItemType(configId);
            if (itemType == null) {
                log.error("通过configId往背包中添加物品异常，物品类型超出范围configId=" + configId + " logReason=" + logReason);
                return;
            }
            if (itemType == MyDefineItemType.RESOURCE.intValue()) {
                addResource(player, MyEnumResourceId.getMyEnumResourceId(configId), num, logReason);
            } else if (itemType == MyDefineItemType.PROP.intValue()) {
                addProp(player, configId, num, logReason);
            } else {
                log.error("通过configId往背包中添加资源或物品找不到类型：configId=" + configId + " type=" + itemType);
            }
        } catch (Exception e) {
            log.error("通过configId往背包中添加资源或物品异常player=" + player.getPlayerId(), e);
        }
    }

    /**
     * 添加资源（非加锁资源必须放在角色线程中使用）
     *
     * @param player
     * @param resourceId
     * @param num
     * @param logReason
     */
    private void addResource(WebPlayer player, MyEnumResourceId resourceId, long num, Integer logReason) {
        try {
            if (num == 0) {
                return;
            }
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
            long value = 0;
            long resourceBeforeValue = 0;
            if (resourceId.isLock()) {
                synchronized (player.getResourceLock()) {
                    num = Math.abs(num);
                    resourceBeforeValue = playerPack.getResourceMap().getOrDefault(resourceId.getId(), 0L);
                    value = resourceBeforeValue + num;
                    // 不能超过上限
                    B_item_Bean itemBean = dataManager.c_item_Container.getMap().get(resourceId.getId());
                    if (itemBean.getMergeMax() > 0) {
                        value = Math.min(value, itemBean.getMergeMax());
                    }
                    playerPack.getResourceMap().put(resourceId.getId(), value);
                }
            } else {
                num = Math.abs(num);
                resourceBeforeValue = playerPack.getResourceMap().getOrDefault(resourceId.getId(), 0L);
                value = resourceBeforeValue + num;
                // 不能超过上限
                B_item_Bean itemBean = dataManager.c_item_Container.getMap().get(resourceId.getId());
                if (itemBean.getMergeMax() > 0) {
                    value = Math.min(value, itemBean.getMergeMax());
                }
                playerPack.getResourceMap().put(resourceId.getId(), value);
            }
            // 更新candy榜单
            if (resourceId == MyEnumResourceId.CANDY) {
                // 上传每日糖果排行榜
                redisManager.rankZincrby(redisManager.getCenterJedis(), RedisKey.RANK, num, Long.toString(player.getPlayerId()), null, BeanProto.E_WEB_RANK_TYPE.RANK_TYPE_CANDY_DAY.name());
                // 上传糖果总榜（总榜需要特殊处理，因为涉及到相同积分降序问题，积分需要编码，也不能使用叠加的方式）
                Long currentCandy = playerPack.getResourceMap().get(resourceId.getId());
                redisManager.rankZadd(redisManager.getCenterJedis(), RedisKey.RANK, Long.toString(player.getPlayerId()), GameUtil.encodeRankScoreByTime(currentCandy), null, BeanProto.E_WEB_RANK_TYPE.RANK_TYPE_CANDY.name(), 0);
            }
            // 保存日志
            logFactory.saveLogForAddResource(player, resourceId, num, logReason, resourceBeforeValue, value);
        } catch (Exception e) {
            log.error("添加资源:" + resourceId.getId(), e);
        }
    }

    /**
     * 添加实体道具
     *
     * @param player
     * @param num
     * @param logReason
     */
    private void addProp(WebPlayer player, Integer configId, int num, Integer logReason) {
        try {
            B_data_Bean dataBean4 = dataManager.c_data_Container.getMap().get(MyDefineData.DATA4);
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
            if (playerPack.getPropMap().size() + playerPack.getConcurrentPropMap().size() > dataBean4.getValue()) {
                log.info("该玩家道具数量已达到上限，不添加道具item=" + configId + " num=" + num + " player=" + player.getPlayerId());
                return;
            }
            B_item_Bean itemBean = dataManager.c_item_Container.getMap().get(configId);
            if (itemBean == null) {
                log.error("玩家[" + player.getPlayerId() + "]添加实体道具不存在propConfigId=" + configId + " log=" + logReason);
                return;
            }
            Prop prop = getPropByConfigId(player, configId);
            // 道具枚举
            PropId propId = PropId.getPropId(configId);
            // 如果道具不能叠加
            if (!itemBean.getMerge()) {
                if (propId == null || !propId.isLock()) {
                    // 添加到背包
                    for (int i = 0; i < num; i++) {
                        Prop newProp = createProp(player, configId);
                        newProp.setNum(1);
                        playerPack.getPropMap().put(newProp.getId(), newProp);
                        playerPack.getPropList().add(newProp);
                        // 添加获得道具日志
                        logFactory.saveLogForAddItem(player, newProp.getId(), configId, 1, logReason, newProp.getNum(), "");
                    }
                } else {
                    synchronized (player.getResourceLock()) {
                        for (int i = 0; i < num; i++) {
                            Prop newProp = createProp(player, configId);
                            newProp.setNum(1);
                            playerPack.getConcurrentPropMap().put(newProp.getId(), newProp);
                            playerPack.getConcurrentPropList().add(newProp);
                            // 添加获得道具日志
                            logFactory.saveLogForAddItem(player, newProp.getId(), configId, 1, logReason, newProp.getNum(), "");
                        }
                    }
                }
            } else {
                // 可以叠加
                if (prop == null) {
                    Prop newProp = createProp(player, configId);
                    if (itemBean.getMergeMax() > 0) {
                        num = (int) Math.min(num, itemBean.getMergeMax());
                    }
                    newProp.setNum(num);
                    if (propId == null || !propId.isLock()) {
                        playerPack.getPropMap().put(newProp.getId(), newProp);
                        playerPack.getPropList().add(newProp);
                    } else {
                        synchronized (player.getResourceLock()) {
                            playerPack.getConcurrentPropMap().put(newProp.getId(), newProp);
                            playerPack.getConcurrentPropList().add(newProp);
                        }
                    }
                    // 添加获得道具日志
                    logFactory.saveLogForAddItem(player, newProp.getId(), configId, num, logReason, newProp.getNum(), "");
                } else {
                    if (itemBean.getMergeMax() > 0) {
                        num = (int) Math.min(prop.getNum() + num, itemBean.getMergeMax());
                    }
                    if (propId == null || !propId.isLock()) {
                        prop.setNum(prop.getNum() + num);
                    } else {
                        synchronized (player.getResourceLock()) {
                            prop.setNum(prop.getNum() + num);
                        }
                    }
                    // 添加获得道具日志
                    logFactory.saveLogForAddItem(player, prop.getId(), configId, num, logReason, prop.getNum(), "");
                }
            }
        } catch (Exception e) {
            log.error("通过configId往背包中添加实体物品异常player=" + player.getPlayerId(), e);
        }
    }

    /**
     * 添加卡片
     *
     * @param player
     * @param gachaCard
     * @param logReason
     */
    public void addCard(WebPlayer player, GachaCard gachaCard, Integer logReason) {
        try {
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
            // 添加到背包
            Card card = new Card();
            card.setId(Long.parseLong(gachaCard.getId()));
            card.setConfigId(PropId.NFT_CARD.getId());
            card.setNum(1);
            card.setCard(gachaCard);
            playerPack.getCardMap().put(Long.parseLong(card.getCard().getId()), card);
            playerPack.getCardList().add(card);
            log.info("玩家[" + player.getPlayerId() + "]添加卡片 card=" + JSON.toJSONString(gachaCard));
            // 添加获得道具日志
            logFactory.saveLogForAddItem(player, card.getId(), card.getConfigId(), 1, logReason, 1, JSON.toJSONString(card));
        } catch (Exception e) {
            log.error("添加卡片异常：", e);
        }
    }

    /**
     * 移除背包卡片
     *
     * @param player
     * @param cardId
     * @param logReason
     */
    public void removeCard(WebPlayer player, long cardId, Integer logReason) {
        try {
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
            Card card = playerPack.getCardMap().get(cardId);
            if (card != null) {
                playerPack.getCardMap().remove(cardId);
                playerPack.getCardList().remove(card);
                log.info("玩家[" + player.getPlayerId() + "]移除卡片 card=" + JSON.toJSONString(card));
                // 添加获得道具日志
                logFactory.saveLogForAddItem(player, card.getId(), card.getConfigId(), -1, logReason, 0, JSON.toJSONString(card));
            }
        } catch (Exception e) {
            log.error("移除背包卡片异常：", e);
        }
    }

    /**
     * 验证资源或物品是否足够(配置id)
     *
     * @param player
     * @param list
     * @return
     */
    public boolean itemCheckByConfigId(WebPlayer player, List<Integer> list) {
        try {
            return reduceLock(player, list, null, 0);
        } catch (Exception e) {
            log.error("验证某个操作是否满足需求物品或者资源(配置id)，否则返回失败", e);
            return false;
        }
    }

    /**
     * 验证资源或物品是否足够并扣除(配置id)
     *
     * @param player
     * @param list
     * @param logReason
     * @return
     */
    public boolean itemCheckAndReduceByConfigId(WebPlayer player, List<Integer> list, Integer logReason) {
        try {
            return reduceLock(player, list, logReason, 1);
        } catch (Exception e) {
            log.error("验证某个操作是否满足需求物品或者资源(配置id)，足够就删除道具，否则返回失败", e);
            return false;
        }
    }

    /**
     * 不验证资源是否足够，直接扣除，不足扣除为负数(配置id)
     *
     * @param player
     * @param list
     * @param logReason
     * @return
     */
    public boolean itemReduceByConfigId(WebPlayer player, List<Integer> list, Integer logReason) {
        try {
            return reduceLock(player, list, logReason, 2);
        } catch (Exception e) {
            log.error("验证某个操作是否满足需求物品或者资源(配置id)，足够就删除道具，否则返回失败", e);
            return false;
        }
    }

    /**
     * 批量减少资源(配置id)
     *
     * @param player
     * @param list
     * @param logReason
     * @param reduceType 0=只验证资源是否足够 1=验证是否足够，足够就进行扣除 2=不验证是否足够，直接扣，不足扣位负数（用于少数情况的timer中）
     * @return
     * @deprecated 此方法非业务逻辑直接调用，请使用isGoodsTrue或者isGoodsTrueAndReduce或者isItemReduce方法
     */
    @Deprecated
    private boolean reduceLock(WebPlayer player, List<Integer> list, Integer logReason, int reduceType) {
        try {
            boolean isLock = false;
            if (list != null) {
                for (int i = 0, len = list.size(); i < len; i += 2) {
                    int configId = list.get(i);
                    Integer itemType = itemTypeUtils.getItemType(configId);
                    if (itemType.intValue() == MyDefineItemType.RESOURCE.intValue()) {
                        MyEnumResourceId resourceId = MyEnumResourceId.getMyEnumResourceId(configId);
                        if (resourceId != null && resourceId.isLock()) {
                            // 有资源需要加锁处理
                            isLock = true;
                            break;
                        }
                    } else if (itemType.intValue() == MyDefineItemType.PROP.intValue()) {
                        PropId propId = PropId.getPropId(configId);
                        if (propId != null && propId.isLock()) {
                            // 有道具需要加锁处理
                            isLock = true;
                            break;
                        }
                    }
                }
            }
            if (isLock) {
                synchronized (player.getResourceLock()) {
                    return reduceByConfigId(player, list, logReason, reduceType);
                }
            } else {
                return reduceByConfigId(player, list, logReason, reduceType);
            }
        } catch (Exception e) {
            log.error("批量减少资源或道具异常", e);
            return false;
        }
    }

    /**
     * 检查并扣除资源(配置id)
     *
     * @param player
     * @param list
     * @param logReason
     * @param reduceType 0=只验证资源是否足够 1=验证是否足够，足够就进行扣除 2=不验证是否足够，直接扣，不足扣位负数（用于少数情况的timer中）
     * @return
     * @deprecated 此方法非业务逻辑直接调用，请使用isGoodsTrue或者isGoodsTrueAndReduce方法
     */
    @Deprecated
    private boolean reduceByConfigId(WebPlayer player, List<Integer> list, Integer logReason, int reduceType) {
        try {
            if (ListUtils.isEmptyOrNull(list)) {
                return false;
            }
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
            // 检查list是否有扣除资源
            if (reduceType == 0 || reduceType == 1) {
                for (int i = 0, len = list.size(); i < len; i += 2) {
                    Integer goodsId = list.get(i);
                    Integer goodsNum = list.get(i + 1);
                    // 转为正数
                    goodsNum = Math.abs(goodsNum);
                    Integer itemType = itemTypeUtils.getItemType(goodsId);
                    if (itemType.intValue() == MyDefineItemType.RESOURCE) {
                        Long value = playerPack.getResourceMap().getOrDefault(goodsId, 0L);
                        if (value < goodsNum) {
                            log.error("资源不足player=" + player.getPlayerId() + " goodsId=" + goodsId + " num=" + value + " needNum=" + goodsNum);
                            return false;
                        }
                    } else {
                        if (itemType.intValue() == MyDefineItemType.PROP) {
                            // 道具
                            Prop prop = getPropByConfigId(player, goodsId);
                            if (prop == null || prop.getNum() < goodsNum) {
                                log.error("道具不足player=" + player.getPlayerId());
                                return false;
                            }
                        } else {
                            log.error("资源或物品(配置id)不支持player=" + player.getPlayerId() + " goodsId=" + goodsId + " needNum=" + goodsNum);
                            return false;
                        }
                    }
                }
            }
            if (reduceType == 1 || reduceType == 2) {
                // 扣除goodsList资源
                for (int i = 0, len = list.size(); i < len; i += 2) {
                    Integer goodsId = list.get(i);
                    Integer goodsNum = list.get(i + 1);
                    Integer itemType = itemTypeUtils.getItemType(goodsId);
                    if (itemType.intValue() == MyDefineItemType.RESOURCE) {
                        MyEnumResourceId resourceId = MyEnumResourceId.getMyEnumResourceId(goodsId);
                        reduceResource(player, resourceId, goodsNum, logReason);
                    } else {
                        reduceItem(player, goodsId, goodsNum, logReason);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            log.error("批量减少资源(配置id)异常", e);
            return false;
        }
    }

    /**
     * 验证资源或物品是否足够（唯一id）
     *
     * @param player
     * @param list
     * @return
     */
    public boolean itemCheckById(WebPlayer player, List<Long> list) {
        try {
            return reduceById(player, list, null, 0);
        } catch (Exception e) {
            log.error("验证某个操作是否满足需求物品或者资源（唯一id），否则返回失败", e);
            return false;
        }
    }

    /**
     * 验证资源或物品是否足够并扣除（唯一id）
     *
     * @param player
     * @param list
     * @param logReason
     * @return
     */
    public boolean itemCheckAndReduceById(WebPlayer player, List<Long> list, Integer logReason) {
        try {
            return reduceById(player, list, logReason, 1);
        } catch (Exception e) {
            log.error("验证某个操作是否满足需求物品或者资源（唯一id），足够就删除道具，否则返回失败", e);
            return false;
        }
    }

    /**
     * 不验证资源是否足够，直接扣除，不足扣除为负数（唯一id）
     *
     * @param player
     * @param list
     * @param logReason
     * @return
     */
    public boolean itemReduceById(WebPlayer player, List<Long> list, Integer logReason) {
        try {
            return reduceById(player, list, logReason, 2);
        } catch (Exception e) {
            log.error("验证某个操作是否满足需求物品或者资源（唯一id），足够就删除道具，否则返回失败", e);
            return false;
        }
    }

    /**
     * 检查并扣除资源(唯一id)
     *
     * @param player
     * @param list
     * @param logReason
     * @param reduceType 0=只验证资源是否足够 1=验证是否足够，足够就进行扣除 2=不验证是否足够，直接扣，不足扣位负数（用于少数情况的timer中）
     * @return
     * @deprecated 此方法非业务逻辑直接调用，请使用isGoodsTrue或者isGoodsTrueAndReduce方法
     */
    @Deprecated
    private boolean reduceById(WebPlayer player, List<Long> list, Integer logReason, int reduceType) {
        try {
            if (ListUtils.isEmptyOrNull(list)) {
                return false;
            }
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
            // 检查list是否有扣除资源
            if (reduceType == 0 || reduceType == 1) {
                for (int i = 0, len = list.size(); i < len; i += 2) {
                    Long goodsId = list.get(i);
                    Long goodsNum = list.get(i + 1);
                    // 转为正数
                    goodsNum = Math.abs(goodsNum);
                    // 唯一id都是lang类型，除非是int类型的资源id（资源id一般不走reduceByConfigId逻辑）
                    if (goodsId < Integer.MAX_VALUE && MyEnumResourceId.getMyEnumResourceId(goodsId.intValue()) != null) {
                        Long value = playerPack.getResourceMap().getOrDefault(goodsId.intValue(), 0L);
                        if (value < goodsNum) {
                            log.error("资源不足player=" + player.getPlayerId() + " goodsId=" + goodsId + " num=" + value + " needNum=" + goodsNum);
                            return false;
                        }
                    } else {
                        Item item;
                        if ((item = getPropById(player, goodsId)) != null) {
                            // 道具
                            if (item.getNum() < goodsNum) {
                                log.error("道具不足player=" + player.getPlayerId());
                                return false;
                            }
                        } else {
                            log.error("资源或物品(唯一id)不足player=" + player.getPlayerId() + " goodsId=" + goodsId + " needNum=" + goodsNum);
                            return false;
                        }
                    }
                }
            }
            if (reduceType == 1 || reduceType == 2) {
                // 扣除goodsList资源
                for (int i = 0, len = list.size(); i < len; i += 2) {
                    Long goodsId = list.get(i);
                    Long goodsNum = list.get(i + 1);
                    if (goodsId < Integer.MAX_VALUE && MyEnumResourceId.getMyEnumResourceId(goodsId.intValue()) != null) {
                        MyEnumResourceId resourceId = MyEnumResourceId.getMyEnumResourceId(goodsId.intValue());
                        reduceResource(player, resourceId, goodsNum, logReason);
                    } else {
                        reduceItem(player, goodsId, goodsNum, logReason);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            log.error("批量减少资源(唯一id)异常", e);
            return false;
        }
    }

    /**
     * 减少资源（非加锁资源必须放在角色线程中使用）(因为客户端需要的是改变后的值，所以减少方法改为不汇总推送，全部当场推送)
     *
     * @param player
     * @param resourceId
     * @param num
     * @param logReason
     * @return
     * @deprecated 此方法非业务逻辑直接调用，请使用isGoodsTrue或者isGoodsTrueAndReduce方法
     */
    @Deprecated
    private boolean reduceResource(WebPlayer player, MyEnumResourceId resourceId, long num, Integer logReason) {
        try {
            if (num == 0) {
                return true;
            }
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
            num = Math.abs(num);
            long resourceBeforeValue = 0;
            long toReduceCount = 0;
            long value = 0;
            if (resourceId.isLock()) {
                synchronized (player.getResourceLock()) {
                    resourceBeforeValue = playerPack.getResourceMap().getOrDefault(resourceId.getId(), 0L);
//                    if (resourceBeforeValue < num) {
//						toReduceCount = resourceBeforeValue;
//                        return false;
//                    } else {
                    toReduceCount = num;
//                    }
                    value = resourceBeforeValue - toReduceCount;
                    playerPack.getResourceMap().put(resourceId.getId(), value);
                }
            } else {
                resourceBeforeValue = playerPack.getResourceMap().getOrDefault(resourceId.getId(), 0L);
//                if (resourceBeforeValue < num) {
//					toReduceCount = resourceBeforeValue;
//                    return false;
//                } else {
                toReduceCount = num;
//                }
                value = resourceBeforeValue - toReduceCount;
                playerPack.getResourceMap().put(resourceId.getId(), value);
            }
            // 更新candy榜单
            if (resourceId == MyEnumResourceId.CANDY) {
                // 上传每日糖果排行榜
                redisManager.rankZincrby(redisManager.getCenterJedis(), RedisKey.RANK, -toReduceCount, Long.toString(player.getPlayerId()), null, BeanProto.E_WEB_RANK_TYPE.RANK_TYPE_CANDY_DAY.name());
                // 上传糖果总榜（总榜需要特殊处理，因为涉及到相同积分降序问题，积分需要编码，也不能使用叠加的方式）
                Long currentCandy = playerPack.getResourceMap().get(resourceId.getId());
                redisManager.rankZadd(redisManager.getCenterJedis(), RedisKey.RANK, Long.toString(player.getPlayerId()), GameUtil.encodeRankScoreByTime(currentCandy), null, BeanProto.E_WEB_RANK_TYPE.RANK_TYPE_CANDY.name(), 0);
            }
            // 保存日志
            logFactory.saveLogForReduceResource(player, resourceId, -toReduceCount, logReason, resourceBeforeValue, value);
            return true;
        } catch (Exception e) {
            log.error("减少钻石异常", e);
            return false;
        }
    }

    /**
     * 背包中减少物品（配置id）
     *
     * @param player
     * @param configId
     * @param num
     * @param logReason
     * @deprecated 此方法非业务逻辑直接调用，请使用isGoodsTrue或者isGoodsTrueAndReduce方法
     */
    @Deprecated
    private void reduceItem(WebPlayer player, int configId, int num, Integer logReason) {
        try {
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
            Integer itemType = itemTypeUtils.getItemType(configId);
            if (itemType == null) {
                log.error("通过configId背包中减少物品（配置id）异常，物品类型超出范围configId=" + configId);
                return;
            }
            num = Math.abs(num);
            Item item;
            if (itemType.intValue() == MyDefineItemType.PROP) {
                Prop prop = getPropByConfigId(player, configId);
                PropId propId = PropId.getPropId(prop.getConfigId());
                if (propId == null || !propId.isLock()) {
                    if (prop.getNum() > num) {
                        prop.setNum(prop.getNum() - num);
                    } else {
                        playerPack.getPropMap().remove(prop.getId());
                        playerPack.getPropList().remove(prop);
                        num = prop.getNum();
                        prop.setNum(0);
                    }
                } else {
                    synchronized (player.getResourceLock()) {
                        if (prop.getNum() > num) {
                            prop.setNum(prop.getNum() - num);
                        } else {
                            playerPack.getConcurrentPropMap().remove(prop.getId());
                            playerPack.getConcurrentPropList().remove(prop);
                            num = prop.getNum();
                            prop.setNum(0);
                        }
                    }
                }
                item = prop;
            } else {
                log.error("背包中减少物品（配置id）找不到类型configId=" + configId);
                return;
            }
            // 记录日志
            logFactory.saveLogForRemoveItem(player, item.getId(), item.getConfigId(), -num, logReason, item.getNum(), "");
        } catch (Exception e) {
            log.error("背包中减少物品（配置id）异常player=" + player.getPlayerId() + " configId=" + configId, e);
        }
    }

    /**
     * 背包中减少物品（唯一id）
     *
     * @param player
     * @param id
     * @param num
     * @param logReason
     * @deprecated 此方法非业务逻辑直接调用，请使用isGoodsTrue或者isGoodsTrueAndReduce方法
     */
    @Deprecated
    private void reduceItem(WebPlayer player, long id, long num, Integer logReason) {
        try {
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
            num = Math.abs(num);
            Item item;
            if ((item = getPropById(player, id)) != null) {
                Prop prop = (Prop) item;
                PropId propId = PropId.getPropId(prop.getConfigId());
                if (propId == null || !propId.isLock()) {
                    if (prop.getNum() > num) {
                        prop.setNum(prop.getNum() - (int) num);
                    } else {
                        playerPack.getPropMap().remove(prop.getId());
                        playerPack.getPropList().remove(prop);
                        num = prop.getNum();
                        prop.setNum(0);
                    }
                } else {
                    synchronized (player.getResourceLock()) {
                        if (prop.getNum() > num) {
                            prop.setNum(prop.getNum() - (int) num);
                        } else {
                            playerPack.getConcurrentPropMap().remove(prop.getId());
                            playerPack.getConcurrentPropList().remove(prop);
                            num = prop.getNum();
                            prop.setNum(0);
                        }
                    }
                }
            } else {
                log.error("背包中减少物品（唯一id）找不到类型id=" + id);
                return;
            }
            // 记录日志
            logFactory.saveLogForRemoveItem(player, item.getId(), item.getConfigId(), (int) -num, logReason, item.getNum(), "");
        } catch (Exception e) {
            log.error("背包中减少物品（唯一id）异常player=" + player.getPlayerId() + " id=" + id, e);
        }
    }

}