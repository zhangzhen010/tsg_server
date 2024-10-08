package com.game.award.utils;

import com.alibaba.fastjson2.JSONArray;
import com.game.award.structs.AwardList;
import com.game.data.bean.B_reward_Bean;
import com.game.data.manager.DataManager;
import com.game.utils.ListUtils;
import com.game.utils.RandomUtils;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * 获取奖励工具类
 * <p>
 * 因为除开逻辑服的其他服务器没办法解析奖励库奖励，所以把读取奖励抽出来成为一个工具，其他服务器也能读取奖励，最终发送到逻辑服发放奖励
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/2/23 14:09
 */
@Component
@Log4j2
public class AwardUtils {

    private @Resource DataManager dataManager;

    /**
     * 获取奖励库奖励数量
     *
     * @param rewardId
     * @param isMerge  是否进行合并，单抽就要求不能合并
     * @return
     */
    public AwardList getAward(Integer rewardId, boolean isMerge) {
        AwardList awardList = new AwardList();
        getAward(awardList, rewardId, isMerge);
        return awardList;
    }

    /**
     * 获取奖励库奖励数量(内部调用)
     *
     * @param awardList 奖励数据
     * @param rewardId
     * @param isMerge
     * @return
     */
    private void getAward(AwardList awardList, Integer rewardId, boolean isMerge) {
        try {
            B_reward_Bean bean = dataManager.c_reward_Container.getMap().get(rewardId);
            if (bean == null) {
                log.error("获取奖励库奖励数量找不到奖励配置configId=" + rewardId);
                return;
            }
            if (awardList.getRewardIdSet().contains(rewardId)) {
                log.error("递归奖励库出现死循环rewardId=" + rewardId);
                return;
            }
            awardList.getRewardIdSet().add(rewardId);
            // 装备数量
            int equipNum = 0;
            // 英雄数量
            int heroNum = 0;
            // 固定奖励
            for (int i = 0, len = bean.getFixedAwardList().size(); i < len; i++) {
                JSONArray rewardJsonArray = bean.getFixedAwardList().getJSONArray(i);
                Integer goodsId = rewardJsonArray.getIntValue(0);
                if (goodsId == 0) {
                    continue;
                }
                int goodsNum = rewardJsonArray.getIntValue(1);
                if (isMerge) {
                    ListUtils.addList(awardList.getAwardList(), goodsId, goodsNum);
                } else {
                    awardList.getAwardList().add(goodsId);
                    awardList.getAwardList().add(goodsNum);
                }
                // 统计拥有数量
//                Integer itemType = itemTypeUtils.getItemType(goodsId);
//                if (itemType == MyDefineItemType.EQUIP) {
//                    equipNum += goodsNum;
//                } else if (itemType == MyDefineItemType.HERO) {
//                    heroNum++;
//                }
            }
            // 权重奖励库本身id奖励
            Integer weightIdSum = dataManager.c_reward_Container.getRewardIdWeightSumWeightMap().get(bean.getId());
            if (weightIdSum != null) {
                // 开始计算权重奖励
                int weightRandom = RandomUtils.random(weightIdSum);
                int weightMin = 0;
                int awardItemId = 0;
                int awardCount = 0;
                for (int i = 0, len = bean.getWeightAwardIdList().size(); i < len; i++) {
                    JSONArray rewardJsonArray = bean.getWeightAwardIdList().getJSONArray(i);
                    weightMin += rewardJsonArray.getIntValue(2);
                    if (weightRandom < weightMin) {
                        awardItemId = rewardJsonArray.getIntValue(0);
                        // 如果id=0表示没有随机到物品，跳过
                        if (awardItemId == 0) {
                            continue;
                        }
                        awardCount = rewardJsonArray.getIntValue(1);
                        // 递归奖励库
                        for (int j = 0; j < awardCount; j++) {
                            getAward(awardList, awardItemId, isMerge);
                        }
                        break;
                    }
                }
            }
            // 权重奖励
            Integer weightSum = dataManager.c_reward_Container.getRewardWeightSumWeightMap().get(bean.getId());
            if (weightSum != null) {
                // 开始计算权重奖励
                int weightRandom = RandomUtils.random(weightSum);
                int weightMin = 0;
                int awardItemId = 0;
                int awardCount = 0;
                for (int i = 0, len = bean.getWeightAwardList().size(); i < len; i++) {
                    JSONArray rewardJsonArray = bean.getWeightAwardList().getJSONArray(i);
                    weightMin += rewardJsonArray.getIntValue(2);
                    if (weightRandom < weightMin) {
                        awardItemId = rewardJsonArray.getIntValue(0);
                        // 如果id=0表示没有随机到物品，跳过
                        if (awardItemId == 0) {
                            continue;
                        }
                        awardCount = rewardJsonArray.getIntValue(1);
                        // 数量0也表示没有
                        if (awardCount == 0) {
                            continue;
                        }
                        if (isMerge) {
                            ListUtils.addList(awardList.getAwardList(), awardItemId, awardCount);
                        } else {
                            awardList.getAwardList().add(awardItemId);
                            awardList.getAwardList().add(awardCount);
                        }
//                        Integer itemType = itemTypeUtils.getItemType(awardItemId);
//                        if (itemType == MyDefineItemType.EQUIP) {
//                            equipNum += awardCount;
//                        } else if (itemType == MyDefineItemType.HERO) {
//                            heroNum++;
//                        }
                        break;
                    }
                }
            }
            // 权重数量奖励
            Integer weightNumSum = dataManager.c_reward_Container.getRewardWeightNumSumWeightMap().get(bean.getId());
            if (weightNumSum != null) {
                // 开始计算权重奖励
                int weightRandom = RandomUtils.random(weightNumSum);
                int weightMin = 0;
                int awardItemId = 0;
                int awardCount = 0;
                for (int i = 0, len = bean.getWeightNumAwardList().size(); i < len; i++) {
                    JSONArray rewardJsonArray = bean.getWeightNumAwardList().getJSONArray(i);
                    weightMin += rewardJsonArray.getIntValue(3);
                    if (weightRandom < weightMin) {
                        awardItemId = rewardJsonArray.getIntValue(0);
                        // 如果id=0表示没有随机到物品，跳过
                        if (awardItemId == 0) {
                            continue;
                        }
                        // 范围内随机数量
                        awardCount = RandomUtils.random(rewardJsonArray.getIntValue(1), rewardJsonArray.getIntValue(2));
                        if (isMerge) {
                            ListUtils.addList(awardList.getAwardList(), awardItemId, awardCount);
                        } else {
                            awardList.getAwardList().add(awardItemId);
                            awardList.getAwardList().add(awardCount);
                        }
//                        Integer itemType = itemTypeUtils.getItemType(awardItemId);
//                        if (itemType == MyDefineItemType.EQUIP) {
//                            equipNum += awardCount;
//                        } else if (itemType == MyDefineItemType.HERO) {
//                            heroNum++;
//                        }
                        break;
                    }
                }
            }
            // 万分比奖励
            for (int i = 0, len = bean.getRatioAwardList().size(); i < len; i++) {
                JSONArray rewardJsonArray = bean.getRatioAwardList().getJSONArray(i);
                // 开始计算万分比奖励
                Integer awardItemId = 0;
                int awardCount = 0;
                if (RandomUtils.isGenerate(rewardJsonArray.getIntValue(2))) {
                    awardItemId = rewardJsonArray.getIntValue(0);
                    // 如果id=0表示没有随机到物品，跳过
                    if (awardItemId == 0) {
                        continue;
                    }
                    awardCount = rewardJsonArray.getIntValue(1);
                    if (isMerge) {
                        ListUtils.addList(awardList.getAwardList(), awardItemId, awardCount);
                    } else {
                        awardList.getAwardList().add(awardItemId);
                        awardList.getAwardList().add(awardCount);
                    }
//                        Integer itemType = itemTypeUtils.getItemType(awardItemId);
//                        if (itemType == MyDefineItemType.EQUIP) {
//                            equipNum += awardCount;
//                        } else if (itemType == MyDefineItemType.HERO) {
//                            heroNum++;
//                        }
                    break;
                }
            }
            // 记录数量
            awardList.setEquipNum(equipNum);
            awardList.setHeroNum(heroNum);
        } catch (Exception e) {
            log.error("获取奖励库奖励数量增强rewardId=" + rewardId, e);
        }
    }

    /**
     * 合并奖励
     *
     * @param awardAll1
     * @param awardAll2
     * @return
     */
    public void mergeAwardList(AwardList awardAll1, AwardList awardAll2) {
        try {
            for (int i = 0, len = awardAll2.getAwardList().size(); i < len; i += 2) {
                Integer goodsId = awardAll2.getAwardList().get(i);
                Integer value = awardAll2.getAwardList().get(i + 1);
                ListUtils.addList(awardAll1.getAwardList(), goodsId, value);
            }
            awardAll1.setEquipNum(awardAll1.getEquipNum() + awardAll2.getEquipNum());
            awardAll1.setHeroNum(awardAll1.getHeroNum() + awardAll2.getHeroNum());
        } catch (Exception e) {
            log.error("合并奖励", e);
        }
    }

}
