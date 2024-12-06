package com.game.quest.manager;

import com.alibaba.fastjson2.JSON;
import com.game.award.manager.AwardManager;
import com.game.award.structs.AwardList;
import com.game.data.bean.B_quest_Bean;
import com.game.data.bean.B_quest_target_Bean;
import com.game.data.bean.B_quest_target_collect_Bean;
import com.game.data.define.MyDefineItemChangeReason;
import com.game.data.define.MyDefineQuestTargetCollectType;
import com.game.data.manager.DataManager;
import com.game.data.myenum.MyEnumQuestResetType;
import com.game.data.myenum.MyEnumQuestTargetType;
import com.game.data.myenum.MyEnumQuestType;
import com.game.data.myenum.MyEnumResourceId;
import com.game.discord.manager.DiscordManager;
import com.game.player.manager.PlayerManager;
import com.game.player.manager.PlayerOtherManager;
import com.game.player.structs.*;
import com.game.quest.structs.*;
import com.game.twitter.manager.TwitterManager;
import com.game.utils.ListUtils;
import com.game.utils.StringUtil;
import com.game.utils.TimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 16:24
 */
@Component
@Log4j2
public class QuestManager {

    private @Resource DataManager dataManager;
    @Lazy
    private @Resource AwardManager awardManager;
    private @Resource PlayerOtherManager playerOtherManager;
    @Lazy
    private @Resource PlayerManager playerManager;
    @Lazy
    private @Resource DiscordManager discordManager;
    private @Resource TwitterManager twitterManager;

    /**
     * 构建所有任务数据
     *
     * @param player
     * @return
     */
    public ResQuestList buildQuestAll(WebPlayer player) {
        try {
            ResQuestList data = new ResQuestList();
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, false);
            for (int i = 0, len = playerQuest.getQuestList().size(); i < len; i++) {
                Quest quest = playerQuest.getQuestList().get(i);
                ResQuestInfo questInfo = buildQuestInfo(player, quest);
                data.getQuestInfoList().add(questInfo);
            }
            return data;
        } catch (Exception e) {
            log.error("构建所有任务数据异常：", e);
            return null;
        }
    }

    /**
     * 构建可传输的任务数据
     *
     * @param player
     * @param quest
     * @return
     */
    private ResQuestInfo buildQuestInfo(WebPlayer player, Quest quest) {
        try {
            B_quest_Bean questBean = dataManager.c_quest_Container.getMap().get(quest.getConfigId());
            ResQuestInfo questInfo = new ResQuestInfo();
            questInfo.setQuestId(quest.getConfigId());
            questInfo.setQuestType(questBean.getQuestType());
            questInfo.setQuestDesc(questBean.getDescribe());
            questInfo.setQuestState(getQuestCurrentState(player, quest));
            questInfo.setSkipUrl(questBean.getSkipUrl());
            questInfo.setPlatform(questBean.getPlatform());
            questInfo.setSkip(quest.isSkip());
            // 目前任务只会奖励糖果
            if (questBean.getAwardId() > 0) {
                AwardList awardList = awardManager.getAwardList(questBean.getAwardId());
                for (int j = 0, len = awardList.getAwardList().size(); j < len; j += 2) {
                    Integer goodsId = awardList.getAwardList().get(j);
                    Integer goodsNum = awardList.getAwardList().get(j + 1);
                    // TODO 目前任务只会奖励糖果，临时这样写
                    questInfo.setQuestAwardCandy(goodsNum);
                    break;
                }
            }
            // 目前版本不发送任务进度
//            for (int i = 0, len = quest.getTargetList().size(); i < len; i += 2) {
//                Integer targetId = quest.getTargetList().get(i);
//                Integer targetValue = quest.getTargetList().get(i + 1);
//                BeanProto.QuestTargetInfo.Builder questTargetInfoBuilder = player.getProtoBuf().getQuestTargetInfoBuilder();
//                questTargetInfoBuilder.setId(targetId);
//                questTargetInfoBuilder.setState(getQuestTargetState(player, targetId, targetValue));
//                questTargetInfoBuilder.setValue(targetValue);
//                questInfoBuilder.addTargetInfo(questTargetInfoBuilder.build());
//            }

            return questInfo;
        } catch (Exception e) {
            log.error("构建可传输的任务数据", e);
            return null;
        }
    }

    /**
     * 任务目标进度更新，包含自动接取任务接取，任务总进度更新（单条件 并且只有目标数量要求的任务，只需要调用这个方法）
     *
     * @param player
     * @param type
     * @param value
     */
    public void updateQuestTarget(WebPlayer player, MyEnumQuestTargetType type, int value) {
        updateQuestTarget(player, type, 0, 0, 0, 0, value);
    }

    /**
     * 任务目标进度更新，包含自动接取任务接取，任务总进度更新（单条件任务，只需要调用这个方法）
     *
     * @param player
     * @param type
     * @param param1
     * @param value
     */
    public void updateQuestTarget(WebPlayer player, MyEnumQuestTargetType type, int param1, int value) {
        updateQuestTarget(player, type, param1, 0, 0, 0, value);
    }

    /**
     * 任务目标进度更新，包含自动接取任务接取，任务总进度更新（双条件任务，只需要调用这个方法）
     *
     * @param player
     * @param type
     * @param param1
     * @param param2
     * @param value
     */
    public void updateQuestTarget(WebPlayer player, MyEnumQuestTargetType type, int param1, int param2, int value) {
        updateQuestTarget(player, type, param1, param2, 0, 0, value);
    }

    /**
     * 任务目标进度更新，包含自动接取任务接取，任务总进度更新
     *
     * @param player
     * @param type
     * @param param1
     * @param param2
     * @param param3
     * @param param4
     * @param value
     */
    public void updateQuestTarget(WebPlayer player, MyEnumQuestTargetType type, int param1, int param2, int param3, int param4, int value) {
        try {
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, true);
            // 统计任务进度
            QuestTargetDataList questTargetDataList = playerQuest.getQuestTargetDataMap().get(type.getType());
            QuestTargetData questTargetData = null;
            if (questTargetDataList == null) {
                questTargetDataList = new QuestTargetDataList();
                playerQuest.getQuestTargetDataMap().put(type.getType(), questTargetDataList);
            }
            for (int i = 0, len = questTargetDataList.getQuestTargetList().size(); i < len; i++) {
                QuestTargetData tempQuestTargetData = questTargetDataList.getQuestTargetList().get(i);
                if (tempQuestTargetData.getParam1() == param1 && tempQuestTargetData.getParam2() == param2 && tempQuestTargetData.getParam3() == param3 && tempQuestTargetData.getParam4() == param4) {
                    questTargetData = tempQuestTargetData;
                    break;
                }
            }
            if (questTargetData == null) {
                questTargetData = new QuestTargetData();
                questTargetData.setParam1(param1);
                questTargetData.setParam2(param2);
                questTargetData.setParam3(param3);
                questTargetData.setParam4(param4);
                questTargetDataList.getQuestTargetList().add(questTargetData);
            }
            // 计算新的任务值
            long newValue = countQuestValue(type, (int) questTargetData.getValue(), value);
            // 更新历史任务数据值
            questTargetData.setValue(newValue);
            // 更新已接取任务状态
            updateQuestState(player, type, param1, param2, param3, param4, value);
        } catch (Exception e) {
            log.error("更新任务目标", e);
        }
    }

    /**
     * 计算新的任务值
     *
     * @param type
     * @param value
     * @param addValue
     * @return
     */
    private int countQuestValue(MyEnumQuestTargetType type, int value, int addValue) {
        try {
            // 任务数据采集类型配置
            B_quest_target_collect_Bean bQuestTargetCollectBean = dataManager.c_quest_target_collect_Container.getMap().get(type.getType());
            if (bQuestTargetCollectBean == null || bQuestTargetCollectBean.getType() == MyDefineQuestTargetCollectType.ADD.intValue()) {
                // 默认为空等于叠加
                return value + addValue;
            } else if (bQuestTargetCollectBean.getType() == MyDefineQuestTargetCollectType.MAX.intValue()) {
                // 记录最大值
                return Math.max(value, addValue);
            } else if (bQuestTargetCollectBean.getType() == MyDefineQuestTargetCollectType.SET.intValue()) {
                // 覆盖
                return addValue;
            } else {
                // 未知类型
                log.error("任务采集异常：未知采集类型：" + bQuestTargetCollectBean.getType());
                return 0;
            }
        } catch (Exception e) {
            log.error("计算新的任务值", e);
            return 0;
        }
    }

    /**
     * 更新玩家任务状态
     *
     * @param player
     */
    private void updateQuestState(WebPlayer player, MyEnumQuestTargetType type, int p1, int p2, int p3, int p4, int addValue) {
        try {
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, false);
            // 检查是否有任务完成
            for (int j = 0, jlen = playerQuest.getQuestList().size(); j < jlen; j++) {
                Quest quest = playerQuest.getQuestList().get(j);
                B_quest_Bean questBean = dataManager.c_quest_Container.getMap().get(quest.getConfigId());
                // 已完成任务不更新进度
                for (int i = 0, len = questBean.getTargetIdList().size(); i < len; i++) {
                    B_quest_target_Bean targetBean = dataManager.c_quest_target_Container.getMap().get(questBean.getTargetIdList().get(i));
                    // 任务目标不符合，不更新进度
                    if (targetBean.getTargetType().intValue() != type.getType().intValue()) {
                        continue;
                    }
                    // 获取目标进度
                    Integer targetValue = ListUtils.get(quest.getTargetList(), targetBean.getId());
                    if (targetValue == null) {
                        continue;
                    }
                    // 已完成任务不更新进度
                    if (targetValue >= targetBean.getTargetValue()) {
                        continue;
                    }
                    // 更新已接取任务进度
                    int param1 = targetBean.getTargetExt1();
                    int param2 = targetBean.getTargetExt2();
                    // 完成参数3，4保留
                    int param3 = -1;
                    int param4 = -1;
                    // 更新任务进度
                    if ((p1 == param1 || param1 == -1) && (p2 == param2 || param2 == -1) && (p3 == param3 || param3 == -1) && (p4 == param4 || param4 == -1)) {
                        int newValue = countQuestValue(type, targetValue, addValue);
                        ListUtils.set(quest.getTargetList(), targetBean.getId(), newValue);
                    }
                }
            }
        } catch (Exception e) {
            log.error("更新玩家任务状态player=" + player.getPlayerId(), e);
        }
    }

    /**
     * 重置任务（根据任务类型）
     *
     * @param player
     * @param type
     */
    public void resetQuest(WebPlayer player, MyEnumQuestType type) {
        try {
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, true);
            // 重置已接取的任务（每日每周每月等等）
            for (int i = 0; i < playerQuest.getQuestList().size(); i++) {
                Quest quest = playerQuest.getQuestList().get(i);
                // 获取配置
                B_quest_Bean questBean = dataManager.c_quest_Container.getMap().get(quest.getConfigId());
                if (questBean == null) {
                    // 配置不存在的任务先跳过，日志警告
                    log.error("重置玩家任务异常，发现找不到任务配置，playerId=" + player.getPlayerId() + " id=" + quest.getConfigId() + " type=" + type.getType());
                    continue;
                }
                if (questBean.getQuestType() != type.getType().intValue()) {
                    continue;
                }
                // 重置任务
                resetQuest(playerQuest, quest);
            }
        } catch (Exception e) {
            log.error("重置任务（根据任务类型）异常：", e);
        }
    }

    /**
     * 重置每日任务
     *
     * @param player
     */
    public void resetQuest(WebPlayer player) {
        try {
            long currentTime = System.currentTimeMillis();
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, true);
            // 是否也属于跨周
            boolean isSameWeek = TimeUtil.isSameWeek(playerQuest.getWeekLastResetTime(), currentTime);
            // 重置已接取的任务（每日每周每月等等）
            for (int i = 0; i < playerQuest.getQuestList().size(); i++) {
                Quest quest = playerQuest.getQuestList().get(i);
                // 获取配置
                B_quest_Bean questBean = dataManager.c_quest_Container.getMap().get(quest.getConfigId());
                if (questBean == null) {
                    // 配置不存在的任务先跳过，日志警告
                    log.error("重置玩家任务异常，发现找不到任务配置，playerId=" + player.getPlayerId() + " id=" + quest.getConfigId());
                    continue;
                }
                if (questBean.getQuestResetType().intValue() == MyEnumQuestResetType.DAY.getType().intValue()) {
                    // 重置任务
                    resetQuest(playerQuest, quest);
                } else if (questBean.getQuestResetType().intValue() == MyEnumQuestResetType.WEEK.getType().intValue() && !isSameWeek) {
                    // 重置任务
                    resetQuest(playerQuest, quest);
                    // 设置当前周刷新时间
                    playerQuest.setWeekLastResetTime(currentTime);
                } else if (questBean.getQuestResetType().intValue() == MyEnumQuestResetType.MONTH.getType().intValue() && !TimeUtil.isSameMonth(playerQuest.getMonthLastResetTime(), currentTime)) {
                    // 设置当前周刷新时间
                    playerQuest.setMonthLastResetTime(currentTime);
                }
            }
        } catch (Exception e) {
            log.error("重置每日任务（角色线程调用）", e);
        }
    }

    /**
     * 重置单个任务
     *
     * @param quest
     */
    private void resetQuest(PlayerQuest playerQuest, Quest quest) {
        try {
            // 把目标值改为0
            for (int i = 0; i < quest.getTargetList().size(); i += 2) {
                ListUtils.set(quest.getTargetList(), quest.getTargetList().get(i), 0);
            }
            // 从已领取列表移除
            playerQuest.getGetIdSet().remove(quest.getConfigId());
        } catch (Exception e) {
            log.error("重置单个任务异常：", e);
        }
    }

    /**
     * 检查玩家任务
     *
     * @param player
     */
    public void checkPlayerQuest(WebPlayer player) {
        try {
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, true);
            // 检查已接取的任务
            for (int i = 0; i < playerQuest.getQuestList().size(); i++) {
                Quest quest = playerQuest.getQuestList().get(i);
                // 获取配置
                B_quest_Bean questBean = dataManager.c_quest_Container.getMap().get(quest.getConfigId());
                if (questBean == null) {
                    // 配置不存在的任务先跳过，日志警告
                    log.error("检查玩家任务异常，发现找不到任务配置，playerId=" + player.getPlayerId() + " id=" + quest.getConfigId());
                    continue;
                }
            }
        } catch (Exception e) {
            log.error("异常：", e);
        }
    }

    /**
     * 移除玩家任务（根据任务类型）
     *
     * @param player
     * @param type
     */
    public void removeQuest(WebPlayer player, MyEnumQuestType type) {
        try {
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, true);
            // 重置已接取的任务（每日每周每月等等）
            for (int i = 0; i < playerQuest.getQuestList().size(); i++) {
                Quest quest = playerQuest.getQuestList().get(i);
                // 获取配置
                B_quest_Bean questBean = dataManager.c_quest_Container.getMap().get(quest.getConfigId());
                if (questBean == null) {
                    // 配置不存在的任务先跳过，日志警告
                    log.error("删除玩家任务异常，发现找不到任务配置，playerId=" + player.getPlayerId() + " id=" + quest.getConfigId() + " type=" + type.getType());
                    continue;
                }
                if (questBean.getQuestType() != type.getType().intValue()) {
                    continue;
                }
                // 删除任务
                removeQuest(player, quest);
                i--;
            }
        } catch (Exception e) {
            log.error("移除玩家任务", e);
        }
    }

    /**
     * 移除玩家任务
     *
     * @param player
     * @param quest
     */
    private void removeQuest(WebPlayer player, Quest quest) {
        try {
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, true);
            playerQuest.getQuestList().remove(quest);
            playerQuest.getQuestSet().remove(quest.getConfigId());
        } catch (Exception e) {
            log.error("移除玩家任务", e);
        }
    }

    /**
     * 根据任务类型接取任务
     *
     * @param player
     * @param questType
     */
    public void questReceive(WebPlayer player, MyEnumQuestType questType) {
        try {
            // 获取此类型所有任务，接取任务
            questReceive(player, dataManager.c_quest_Container.getQuestTypeMap().get(questType.getType()));
        } catch (Exception e) {
            log.error("根据任务类型接取任务", e);
        }
    }

    /**
     * 任务接取
     *
     * @param player
     * @param beanList
     * @return
     */
    private void questReceive(WebPlayer player, List<B_quest_Bean> beanList) {
        try {
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, false);
            for (int i = 0; i < beanList.size(); i++) {
                B_quest_Bean questBean = beanList.get(i);
                // 已接取的任务跳过
                if (!isReceive(player, questBean)) {
                    continue;
                }
                Quest quest = new Quest();
                // 检查截取任务所有任务目标
                for (int j = 0, len = questBean.getTargetIdList().size(); j < len; j++) {
                    Integer targetId = questBean.getTargetIdList().get(j);
                    B_quest_target_Bean targetBean = dataManager.c_quest_target_Container.getMap().get(targetId);
                    // 自动接取此任务
                    quest.setConfigId(questBean.getId());
                    // 赋初始值(类型1=获取历史进度)
                    if (targetBean.getRecord()) {
                        // 获取任务历史进度参数
                        int questHistoryNum = getQuestHistoryNum(player, targetBean);
                        // 不超过任务目标上限值
                        questHistoryNum = Math.min(questHistoryNum, targetBean.getTargetValue());
                        // 赋值
                        ListUtils.set(quest.getTargetList(), targetId, questHistoryNum);
                    } else {
                        ListUtils.set(quest.getTargetList(), targetId, 0);
                    }
                }
                playerQuest.getQuestList().add(quest);
                playerQuest.getQuestSet().add(questBean.getId());
                // 新接取任务，保存
                playerQuest.setSave(true);
            }
        } catch (Exception e) {
            log.error("任务接取", e);
        }
    }

    /**
     * 任务是否可以被接取
     *
     * @param player
     * @param questBean
     * @return
     */
    private boolean isReceive(WebPlayer player, B_quest_Bean questBean) {
        try {
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, false);
            // 检查(因为set没有存储，防止第一次从内存加载set数据与list数据不同步)
            if (playerQuest.getQuestList().size() != playerQuest.getQuestSet().size()) {
                for (int i = 0, len = playerQuest.getQuestList().size(); i < len; i++) {
                    Quest quest = playerQuest.getQuestList().get(i);
                    playerQuest.getQuestSet().add(quest.getConfigId());
                }
            }
            // 已接取的任务不能接取
            if (playerQuest.getQuestSet().contains(questBean.getId())) {
                return false;
            }
            // 已领取的任务不能接取
            if (playerQuest.getGetIdSet().contains(questBean.getId())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("任务是否可以被接取", e);
            return false;
        }
    }

    /**
     * 获取任务目标历史值
     *
     * @param player
     * @param targetBean
     * @return
     */
    private int getQuestHistoryNum(WebPlayer player, B_quest_target_Bean targetBean) {
        try {
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, false);
            // 获取此任务完成参数
            int param1 = targetBean.getTargetExt1();
            int param2 = targetBean.getTargetExt2();
            // 完成参数3，4保留
            int param3 = -1;
            int param4 = -1;
            // 获取目标历史记录
            int targetValue = 0;
            QuestTargetDataList questTargetDataList = playerQuest.getQuestTargetDataMap().get(targetBean.getTargetType());
            if (questTargetDataList != null) {
                for (int i = 0, len = questTargetDataList.getQuestTargetList().size(); i < len; i++) {
                    QuestTargetData questTargetData = questTargetDataList.getQuestTargetList().get(i);
                    if ((questTargetData.getParam1() == param1 || param1 == -1) && (questTargetData.getParam2() == param2 || param2 == -1) && (questTargetData.getParam3() == param3 || param3 == -1) && (questTargetData.getParam4() == param4 || param4 == -1)) {
                        // 计算新的任务值（如果这里计算出的值属于当前玩家身上数据计算出的值，就不应该配置为获取历史数据，这里取出来叠加会造成值翻倍，不准确）
                        targetValue = countQuestValue(MyEnumQuestTargetType.getMyEnumQuestTargetType(targetBean.getTargetType()), (int) questTargetData.getValue(), targetValue);
                    }
                }
            }
            return targetValue;
        } catch (Exception e) {
            log.error("获取任务历史值", e);
            return 0;
        }
    }

    /**
     * 获取任务状态
     *
     * @param player
     * @param quest
     * @return QuestState
     */
    private int getQuestCurrentState(WebPlayer player, Quest quest) {
        try {
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, false);
            // 获取任务配置
            B_quest_Bean questBean = dataManager.c_quest_Container.getMap().get(quest.getConfigId());
            // 已领取过
            if (playerQuest.getGetIdSet().contains(questBean.getId())) {
                return QuestState.GET;
            }
            // 是否完成
            for (int i = 0, len = quest.getTargetList().size(); i < len; i += 2) {
                Integer targetId = quest.getTargetList().get(i);
                Integer targetValue = quest.getTargetList().get(i + 1);
                int questTargetConType = getQuestTargetState(player, targetId, targetValue);
                if (questTargetConType == QuestState.DOING) {
                    return questTargetConType;
                }
            }
            return QuestState.COMPLETE;
        } catch (Exception e) {
            log.error("获取任务状态", e);
            return 0;
        }
    }

    /**
     * 计算任务进度类型
     *
     * @param player
     * @param targetId
     * @param targetValue
     * @return
     */
    public int getQuestTargetState(WebPlayer player, Integer targetId, int targetValue) {
        try {
            B_quest_target_Bean targetBean = dataManager.c_quest_target_Container.getMap().get(targetId);
            if (targetValue < targetBean.getTargetValue()) {
                return QuestState.DOING;// 未完成
            } else {
                return QuestState.COMPLETE;
            }
        } catch (Exception e) {
            log.error("计算任务进度类型player=" + player.getPlayerId() + " targetId=" + targetId + " targetValue" + targetValue, e);
            return 0;
        }
    }

    /**
     * 获取玩家已接取任务
     *
     * @param player
     * @param questConfigId
     * @return
     */
    public Quest getPlayerQuest(WebPlayer player, int questConfigId) {
        try {
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, false);
            for (int i = 0; i < playerQuest.getQuestList().size(); i++) {
                Quest quest = playerQuest.getQuestList().get(i);
                if (quest.getConfigId() == questConfigId) {
                    return quest;
                }
            }
            return null;
        } catch (Exception e) {
            log.error("获取玩家已接取任务", e);
            return null;
        }
    }

    /**
     * 请求任务跳转链接
     *
     * @param player
     * @param questConfigId
     * @return
     */
    public void reqQuestSkip(WebPlayer player, int questConfigId) {
        try {
            // 处理逻辑[请求任务领取奖励]
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, false);
            Quest quest = getPlayerQuest(player, questConfigId);
            if (quest == null) {
                log.error("领取任务跳转异常，不存在此任务player=" + player.getPlayerId() + " questId=" + questConfigId);
                return;
            }
            quest.setSkip(true);
            playerQuest.setSave(true);
            // 保存数据
            playerManager.savePlayer(player);
        } catch (Exception e) {
            log.error("请求任务跳转", e);
        }
    }

    /**
     * 请求任务领取奖励
     *
     * @param player
     * @param reqData
     */
    public ResQuestAward reqQuestAward(WebPlayer player, ReqQuestAward reqData) {
        try {
            ResQuestAward info = new ResQuestAward();
            // 处理逻辑[请求任务领取奖励]
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, true);
            List<Quest> questList = new ArrayList<>();
            if (reqData.getQuestId() > 0) {
                // 单个领取
                Quest quest = getPlayerQuest(player, reqData.getQuestId());
                if (quest == null) {
                    log.error("领取任务奖励异常，不存在此任务player=" + player.getPlayerId() + " questId=" + reqData.getQuestId());
                    return null;
                }
                questList.add(quest);
                info.setQuestId(reqData.getQuestId());
            } else {
                // 一键领取(暂不开放)
//                for (int i = 0; i < playerQuest.getQuestList().size(); i++) {
//                    Quest quest = playerQuest.getQuestList().get(i);
//                    B_quest_Bean questBean = dataManager.c_quest_Container.getMap().get(quest.getConfigId());
//                    if (questBean.getQuestType() == reqData.getQuestType()) {
//                        questList.add(quest);
//                    }
//                }
            }
            // 特殊任务处理，这部分任务需要先去第三方平台验证是否完成，然后对任务进度进行处理
            {
                for (int i = 0; i < questList.size(); i++) {
                    Quest quest = questList.get(i);
                    B_quest_Bean questBean = dataManager.c_quest_Container.getMap().get(quest.getConfigId());
                    // TODO 这套任务系统只会有一个任务目标
                    B_quest_target_Bean questTargetBean = dataManager.c_quest_target_Container.getMap().get(questBean.getTargetIdList().get(0));
                    // 因为关注discord身分组实现不了，所有关注还是自己实现
                    if (questTargetBean.getTargetType() == MyEnumQuestTargetType.X_FOLLOW.getType().intValue()) {
//                        if (!player.getTwitterUserId().isEmpty()) {
                        // twitter无法获取关注数据
//                            twitterManager.isUserFollowing(player);
                        // twitter的关注任务比较特殊，改为只要跳转了链接，就默认算完成了任务
                        if (quest.isSkip()) {
                            updateQuestTarget(player, MyEnumQuestTargetType.getMyEnumQuestTargetType(questTargetBean.getTargetType()), 1);
                        }
//                        }
                    } else if (questTargetBean.getTargetType() == MyEnumQuestTargetType.X_POST_TAG_TSG.getType().intValue()) {
                        if (!player.getTwitterUserId().isEmpty()) {
                            twitterManager.checkTwitterPostTagTsg(player);
                        }
                    } else if (questTargetBean.getTargetType() == MyEnumQuestTargetType.DISCORD_JOIN.getType().intValue()) {
                        if (discordManager.checkUserJoinDiscordServer(player)) {
                            updateQuestTarget(player, MyEnumQuestTargetType.getMyEnumQuestTargetType(questTargetBean.getTargetType()), 1);
                        }
                    } else {
                        // discord身分组检查任务系统（需要twitter付费版才支持获取数据，改为使用discord身分组验证，身分组在discord的逻辑中是可以买卖的，我只管拥有身分组即可完成任务），
                        if (discordManager.checkUserRoleInCurrentGuild(player, questBean.getDiscordRoleName())) {
                            updateQuestTarget(player, MyEnumQuestTargetType.getMyEnumQuestTargetType(questTargetBean.getTargetType()), 1);
                        }
                    }
//                    if (questTargetBean.getTargetType() == MyEnumQuestTargetType.X_SHARE.getType().intValue()) {
//                        if (!player.getTwitterUserId().isEmpty()) {
//                            twitterManager.checkTwitterShare(player);
//                        }
//                    } else if (questTargetBean.getTargetType() == MyEnumQuestTargetType.X_UPDATE_NAME.getType().intValue()) {
//                        if (!player.getTwitterUserId().isEmpty()) {
//                            twitterManager.chekUserName(player);
//                        }
//                    } else if (questTargetBean.getTargetType() == MyEnumQuestTargetType.X_UPDATE_BANNER.getType().intValue()) {
//                        if (!player.getTwitterUserId().isEmpty()) {
//                            twitterManager.checkUserBanner(player);
//                        }
//                    }
                }
            }
            // 检查任务是否完成
            for (int i = 0; i < questList.size(); i++) {
                Quest quest = questList.get(i);
                int questCurrentState = getQuestCurrentState(player, quest);
                if (questCurrentState != QuestState.COMPLETE) {
                    log.error("请求任务领取奖励任务不是完成状态，player=" + player.getPlayerId() + " questId=" + quest.getConfigId() + " state=" + questCurrentState + " quest=" + JSON.toJSONString(quest));
                    return null;
                }
            }
            // 发放任务奖励
            for (int i = 0; i < questList.size(); i++) {
                Quest quest = questList.get(i);
                B_quest_Bean bean = dataManager.c_quest_Container.getMap().get(quest.getConfigId());
                // 添加已领取
                playerQuest.getGetIdSet().add(bean.getId());
                // 需要身分组领取后就需要移除
                if (!StringUtil.isEmptyOrNull(bean.getDiscordRoleName())) {
                    // 主线任务不移除身分组，防止用户再次购买身分组
                    if (bean.getQuestType() != MyEnumQuestType.MAIN.getType().intValue()) {
                        // 移除身分组
                        discordManager.removeRoleFromUserInCurrentGuild(player, player.getDiscordUserId(), bean.getDiscordRoleName());
                    }
                }
                // 发放奖励
                if (bean.getAwardId() > 0) {
                    AwardList awardList = awardManager.getAwardList(bean.getAwardId());
                    // 发放奖励
                    awardManager.sendAwardList(player, awardList.getAwardList(), MyDefineItemChangeReason.QUEST_GET);
                    Integer configId = awardList.getAwardList().get(0);
                    Integer candyNum = awardList.getAwardList().get(1);
                    if (configId == MyEnumResourceId.CANDY.getId().intValue()) {
                        info.setQuestAwardCandy(candyNum);
                    }
                }
                // 如果有下一级任务，进入下一级任务
                if (bean.getNextId() > 0) {
                    // 移除当前的任务
                    removeQuest(player, quest);
                    B_quest_Bean newQuestBean = dataManager.c_quest_Container.getMap().get(bean.getNextId());
                    questReceive(player, List.of(newQuestBean));
                }
            }
            // 保存数据
            playerManager.savePlayer(player);
            // 获取糖果总量
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, false);
            info.setPlayerCandy(playerPack.getResourceMap().get(MyEnumResourceId.CANDY.getId()).intValue());
            return info;
        } catch (Exception e) {
            log.error("请求任务领取奖励", e);
            return null;
        }
    }

}