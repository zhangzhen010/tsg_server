package com.game.player.manager;

import com.game.db.utils.PlayerPackDbUtils;
import com.game.db.utils.PlayerPayDbUtils;
import com.game.db.utils.PlayerQuestDbUtils;
import com.game.redis.manager.RedisManager;
import com.game.save.manager.SaveManager;
import com.game.server.SaveServer;
import com.game.utils.DataZipUtil;
import com.google.apps.card.v1.Card;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 玩家其他数据管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/10 10:54
 */
@Component
@Log4j2
public class PlayerOtherManager {

    /**
     * 数据库日志
     */
    private final Logger dbLog = LogManager.getLogger("DB");

    private @Resource MongoTemplate mongoTemplate;
    private @Resource RedisManager redisManager;
    private @Resource SaveManager saveManager;
    /**
     * 玩家背包数据map
     */
    private final ConcurrentHashMap<Long, PlayerPack> playerPackDataMap = new ConcurrentHashMap<>();
    /**
     * 玩家任务数据map
     */
    private final ConcurrentHashMap<Long, PlayerQuest> playerQuestDataMap = new ConcurrentHashMap<>();
    /**
     * 玩家充值数据map
     */
    private final ConcurrentHashMap<Long, PlayerPay> playerPayDataMap = new ConcurrentHashMap<>();

    /**
     * 保存角色其他数据
     *
     * @param player
     * @param isCreate
     */
    public void savePlayerOther(WebPlayer player, boolean isCreate) {
        try {
            // 背包数据存储
            PlayerPack playerPack = getPlayerPack(player, false);
            if (isCreate) {
                // 创建为了数据安全，直接本地保存
                byte[] data = PlayerPackDbUtils.buildPlayerPackBean(playerPack).toByteArray();
                byte[] playerPackData = DataZipUtil.dataEncode(data);
                playerPack.setSaveData(playerPackData);
                redisManager.playerOtherSet(playerPack);
                saveManager.getPlayerSave().deal(playerPack, SaveServer.INSERT);
            } else {
                if (playerPack.isSave()) {
                    playerPack.setSave(false);
                    byte[] data = PlayerPackDbUtils.buildPlayerPackBean(playerPack).toByteArray();
                    byte[] playerPackData = DataZipUtil.dataEncode(data);
                    playerPack.setSaveData(playerPackData);
                    redisManager.playerOtherSet(playerPack);
                    // 本地保存数据(这里去其他线程保存，有数据同步问题，如果要解决可以在玩家线程拷贝一份数据丢入进去存储)
                    saveManager.getPlayerSave().deal(playerPack, SaveServer.UPDATE);
                }
            }
            // 任务数据存储
            PlayerQuest playerQuest = getPlayerQuest(player, false);
            if (isCreate) {
                // 创建为了数据安全，直接本地保存
                byte[] data = PlayerQuestDbUtils.buildPlayerQuestBean(playerQuest).toByteArray();
                byte[] playerQuestData = DataZipUtil.dataEncode(data);
                playerQuest.setSaveData(playerQuestData);
                redisManager.playerOtherSet(playerQuest);
                saveManager.getPlayerSave().deal(playerQuest, SaveServer.INSERT);
            } else {
                if (playerQuest.isSave()) {
                    playerQuest.setSave(false);
                    byte[] data = PlayerQuestDbUtils.buildPlayerQuestBean(playerQuest).toByteArray();
                    byte[] playerQuestData = DataZipUtil.dataEncode(data);
                    playerQuest.setSaveData(playerQuestData);
                    redisManager.playerOtherSet(playerQuest);
                    // 本地保存数据(这里去其他线程保存，有数据同步问题，如果要解决可以在玩家线程拷贝一份数据丢入进去存储)
                    saveManager.getPlayerSave().deal(playerQuest, SaveServer.UPDATE);
                }
            }
            // 充值数据存储
            PlayerPay playerPay = getPlayerPay(player, false);
            if (isCreate) {
                // 创建为了数据安全，直接本地保存
                byte[] data = PlayerPayDbUtils.buildPlayerPayBean(playerPay).toByteArray();
                byte[] playerPayData = DataZipUtil.dataEncode(data);
                playerPay.setSaveData(playerPayData);
                redisManager.playerOtherSet(playerPay);
                saveManager.getPlayerSave().deal(playerPay, SaveServer.INSERT);
            } else {
                if (playerPay.isSave()) {
                    playerPay.setSave(false);
                    byte[] data = PlayerPayDbUtils.buildPlayerPayBean(playerPay).toByteArray();
                    byte[] playerPayData = DataZipUtil.dataEncode(data);
                    playerPay.setSaveData(playerPayData);
                    redisManager.playerOtherSet(playerPay);
                    // 本地保存数据(这里去其他线程保存，有数据同步问题，如果要解决可以在玩家线程拷贝一份数据丢入进去存储)
                    saveManager.getPlayerSave().deal(playerPay, SaveServer.UPDATE);
                }
            }
            dbLog.info("本地保存玩家playerId=" + player.getPlayerId());
        } catch (Exception e) {
            log.error("保存角色（保存中间线程调用）", e);
        }
    }

    /**
     * 关服保存所有缓存存在的玩家其他数据
     */
    public void savePlayerOtherAll(List<WebPlayer> allPlayerList) {
        try {
            // 缓存背包数据(关服不验证状态保存)
            List<PlayerPack> playerPackList = new ArrayList<>();
            for (int i = 0; i < allPlayerList.size(); i++) {
                WebPlayer player = allPlayerList.get(i);
                PlayerPack playerPack = getPlayerPack(player, false);
                byte[] saveData = PlayerPackDbUtils.buildPlayerPackBean(playerPack).toByteArray();
                playerPack.setSaveData(DataZipUtil.dataEncode(saveData));
                playerPackList.add(playerPack);
            }
            redisManager.playerOtherSet(playerPackList);
            // 缓存任务数据(关服不验证状态保存)
            List<PlayerQuest> playerQuestList = new ArrayList<>();
            for (int i = 0; i < allPlayerList.size(); i++) {
                WebPlayer player = allPlayerList.get(i);
                PlayerQuest playerQuest = getPlayerQuest(player, false);
                byte[] saveData = PlayerQuestDbUtils.buildPlayerQuestBean(playerQuest).toByteArray();
                playerQuest.setSaveData(DataZipUtil.dataEncode(saveData));
                playerQuestList.add(playerQuest);
            }
            redisManager.playerOtherSet(playerQuestList);
            // 缓存充值数据(关服不验证状态保存)
            List<PlayerPay> playerPayList = new ArrayList<>();
            for (int i = 0; i < allPlayerList.size(); i++) {
                WebPlayer player = allPlayerList.get(i);
                PlayerPay playerPay = getPlayerPay(player, false);
                byte[] saveData = PlayerPayDbUtils.buildPlayerPayBean(playerPay).toByteArray();
                playerPay.setSaveData(DataZipUtil.dataEncode(saveData));
                playerPayList.add(playerPay);
            }
            redisManager.playerOtherSet(playerPayList);
            for (int i = 0, len = allPlayerList.size(); i < len; i++) {
                WebPlayer player = allPlayerList.get(i);
                // 玩家背包数据(关服不验证状态保存)
                PlayerPack playerPack = getPlayerPack(player, false);
                mongoTemplate.save(playerPack);
                // 玩家任务数据(关服不验证状态保存)
                PlayerQuest playerQuest = getPlayerQuest(player, false);
                mongoTemplate.save(playerQuest);
                // 玩家充值数据(关服不验证状态保存)
                PlayerPay playerPay = getPlayerPay(player, false);
                mongoTemplate.save(playerPay);
            }
        } catch (Exception e) {
            log.error("关服保存所有缓存存在的玩家其他数据异常：", e);
        }
    }

    /**
     * 移除玩家其他数据缓存
     *
     * @param playerId 玩家唯一id
     * @param save     是否保存数据落地
     */
    public void removePlayerOther(Long playerId, boolean save) {
        try {
            removePlayerPackData(playerId, true);
            removePlayerQuestData(playerId, true);
            removePlayerPayData(playerId, true);
        } catch (Exception e) {
            log.error("移除玩家其他数据缓存异常：", e);
        }
    }

    /**
     * 创建玩家其他数据
     *
     * @param player
     */
    public void createPlayerOther(WebPlayer player) {
        try {
            // 初始化背包数据
            PlayerPack playerPack = new PlayerPack();
            playerPack.setPlayerId(player.getPlayerId());
            playerPackDataMap.put(player.getPlayerId(), playerPack);
            // 初始化任务数据
            PlayerQuest playerQuest = new PlayerQuest();
            playerQuest.setPlayerId(player.getPlayerId());
            playerQuestDataMap.put(player.getPlayerId(), playerQuest);
            // 初始化充值数据
            PlayerPay playerPay = new PlayerPay();
            playerPay.setPlayerId(player.getPlayerId());
            playerPayDataMap.put(player.getPlayerId(), playerPay);
        } catch (Exception e) {
            log.error("创建玩家其他数据异常：", e);
        }
    }

    /**
     * 移除玩家背包本地缓存数据
     *
     * @param playerId
     * @param save
     */
    private void removePlayerPackData(Long playerId, boolean save) {
        try {
            PlayerPack playerPack = playerPackDataMap.remove(playerId);
            if (playerPack != null) {
                if (save) {
                    // 移除时保存一次
                    byte[] playerPackData = PlayerPackDbUtils.buildPlayerPackBean(playerPack).toByteArray();
                    playerPack.setSaveData(DataZipUtil.dataEncode(playerPackData));
                    redisManager.playerOtherSet(playerPack);
                    saveManager.getPlayerSave().deal(playerPack, SaveServer.UPDATE);
                }
            } else {
                log.error("移除玩家背包本地缓存数据异常，本地缓存没有玩家背包数据playerId=" + playerId);
            }
        } catch (Exception e) {
            log.error("移除玩家背包本地缓存数据", e);
        }
    }

    /**
     * 获取玩家（本方法限定玩家本人调用）
     *
     * @param player
     * @param isChangeSave true=取出数据需要改变并且保存
     * @return
     */
    public PlayerPack getPlayerPack(WebPlayer player, boolean isChangeSave) {
        try {
            PlayerPack playerPack = playerPackDataMap.get(player.getPlayerId());
            if (playerPack == null) {
                // 如果本地缓存没有此player，再从redis获取
                playerPack = redisManager.playerPackGet(player.getPlayerId());
                if (playerPack != null) {
                    // 添加到本地缓存
                    playerPackDataMap.put(player.getPlayerId(), playerPack);
                }
            }
            // 如果缓存没有player数据，本方法都是玩家本人调用，所以内存缓存找不到直接去数据库捞取数据
            if (playerPack == null) {
                playerPack = loadPlayerPack(player.getPlayerId());
            }
            // 取出玩家背包数据，如果需要改变数据内容，那么肯定需要保存，因为timer保存和玩家操作肯定属于一个线程（玩家所在线程），所以这里先设置，后面触发保存
            if (isChangeSave) {
                playerPack.setSave(true);
            }
            // 由于getPropMap不做数据落地，更新getPropMap数据
            if (playerPack.getPropList().size() != playerPack.getPropMap().size()) {
                playerPack.getPropMap().clear();
                for (int i = 0, len = playerPack.getPropList().size(); i < len; i++) {
                    Prop prop = playerPack.getPropList().get(i);
                    playerPack.getPropMap().put(prop.getId(), prop);
                }
            }
            // 由于concurrentPropMap不做数据落地，更新concurrentPropMap数据
            synchronized (player.getResourceLock()) {
                if (playerPack.getConcurrentPropList().size() != playerPack.getConcurrentPropMap().size()) {
                    playerPack.getConcurrentPropMap().clear();
                    for (int i = 0, len = playerPack.getConcurrentPropList().size(); i < len; i++) {
                        Prop prop = playerPack.getConcurrentPropList().get(i);
                        playerPack.getConcurrentPropMap().put(prop.getId(), prop);
                    }
                }
            }
            // 由于getSkillMap不做数据落地，更新getSkillMap数据
            if (playerPack.getSkillList().size() != playerPack.getSkillMap().size()) {
                playerPack.getSkillMap().clear();
                for (int i = 0, len = playerPack.getSkillList().size(); i < len; i++) {
                    Skill skill = playerPack.getSkillList().get(i);
                    playerPack.getSkillMap().put(skill.getId(), skill);
                }
            }
            // 由于getCardMap不做数据落地，更新getCardMap数据
            if (playerPack.getCardList().size() != playerPack.getCardMap().size()) {
                playerPack.getCardMap().clear();
                for (int i = 0, len = playerPack.getCardList().size(); i < len; i++) {
                    Card card = playerPack.getCardList().get(i);
                    // 这里使用卡片的字符串唯一id
                    playerPack.getCardMap().put(Long.parseLong(card.getCard().getId()), card);
                }
            }
            return playerPack;
        } catch (Exception e) {
            log.error("获取玩家（本方法限定玩家本人调用）player=" + player.getPlayerId(), e);
            return null;
        }
    }

    /**
     * 从数据库加载玩家背包数据(强烈建议先调用getPlayer方法找不到player才能调用此方法)
     *
     * @param playerId
     * @return
     */
    private PlayerPack loadPlayerPack(Long playerId) {
        try {
            PlayerPack playerPack = mongoTemplate.findOne(Query.query(Criteria.where("playerId").is(playerId)), PlayerPack.class);
            if (playerPack == null) {
                // 兼容老玩家新背包存储数据,初始化背包数据，或者新开发的功能需要这里兼容修复
                playerPack = new PlayerPack();
                playerPack.setPlayerId(playerId);
                playerPackDataMap.put(playerId, playerPack);
                // 存入mongo
                saveManager.getPlayerSave().deal(playerPack, SaveServer.INSERT);
                log.error("从数据库加载玩家背包数据不存在playerId=" + playerId + " 进行修复背包数据！");
                return playerPack;
            }
            // 从数据库加载的玩家数据缓存到redis，如果redis已存在（那是因为有代码没有做正确的获取玩家操作顺序），则redis不做任何操作
            byte[] data = PlayerPackDbUtils.buildPlayerPackBean(playerPack).toByteArray();
            byte[] playerPackData = DataZipUtil.dataEncode(data);
            playerPack.setSaveData(playerPackData);
            redisManager.playerOtherSet(playerPack);
            // 添加到本地缓存
            playerPackDataMap.put(playerId, playerPack);
            return playerPack;
        } catch (Exception e) {
            log.error("加载玩家背包数据失败", e);
            return null;
        }
    }

    /**
     * 移除玩家任务本地缓存数据
     *
     * @param playerId
     * @param save
     */
    private void removePlayerQuestData(Long playerId, boolean save) {
        try {
            PlayerQuest playerQuest = playerQuestDataMap.remove(playerId);
            if (playerQuest != null) {
                if (save) {
                    // 移除时保存一次
                    byte[] playerQuestData = PlayerQuestDbUtils.buildPlayerQuestBean(playerQuest).toByteArray();
                    playerQuest.setSaveData(DataZipUtil.dataEncode(playerQuestData));
                    redisManager.playerOtherSet(playerQuest);
                    saveManager.getPlayerSave().deal(playerQuest, SaveServer.UPDATE);
                }
            } else {
                log.error("移除玩家任务本地缓存数据异常，本地缓存没有玩家任务数据playerId=" + playerId);
            }
        } catch (Exception e) {
            log.error("移除玩家任务本地缓存数据", e);
        }
    }

    /**
     * 获取玩家（本方法限定玩家本人调用）
     *
     * @param player
     * @param isChangeSave true=取出数据需要改变并且保存
     * @return
     */
    public PlayerQuest getPlayerQuest(WebPlayer player, boolean isChangeSave) {
        try {
            PlayerQuest playerQuest = playerQuestDataMap.get(player.getPlayerId());
            if (playerQuest == null) {
                // 如果本地缓存没有此player，再从redis获取
                playerQuest = redisManager.playerQuestGet(player.getPlayerId());
                if (playerQuest != null) {
                    // 添加到本地缓存
                    playerQuestDataMap.put(player.getPlayerId(), playerQuest);
                }
            }
            // 如果缓存没有player数据，本方法都是玩家本人调用，所以内存缓存找不到直接去数据库捞取数据
            if (playerQuest == null) {
                playerQuest = loadPlayerQuest(player.getPlayerId());
            }
            // 取出玩家任务数据，如果需要改变数据内容，那么肯定需要保存，因为timer保存和玩家操作肯定属于一个线程（玩家所在线程），所以这里先设置，后面触发保存
            if (isChangeSave) {
                playerQuest.setSave(true);
            }
            return playerQuest;
        } catch (Exception e) {
            log.error("获取玩家（本方法限定玩家本人调用）player=" + player.getPlayerId(), e);
            return null;
        }
    }

    /**
     * 从数据库加载玩家任务数据(强烈建议先调用getPlayer方法找不到player才能调用此方法)
     *
     * @param playerId
     * @return
     */
    private PlayerQuest loadPlayerQuest(Long playerId) {
        try {
            PlayerQuest playerQuest = mongoTemplate.findOne(Query.query(Criteria.where("playerId").is(playerId)), PlayerQuest.class);
            if (playerQuest == null) {
                // 兼容老玩家新任务存储数据,初始化任务数据，或者新开发的功能需要这里兼容修复
                playerQuest = new PlayerQuest();
                playerQuest.setPlayerId(playerId);
                playerQuestDataMap.put(playerId, playerQuest);
                // 存入mongo
                saveManager.getPlayerSave().deal(playerQuest, SaveServer.INSERT);
                log.error("从数据库加载玩家任务数据不存在playerId=" + playerId + " 进行修复任务数据！");
                return playerQuest;
            }
            // 从数据库加载的玩家数据缓存到redis，如果redis已存在（那是因为有代码没有做正确的获取玩家操作顺序），则redis不做任何操作
            byte[] data = PlayerQuestDbUtils.buildPlayerQuestBean(playerQuest).toByteArray();
            byte[] playerQuestData = DataZipUtil.dataEncode(data);
            playerQuest.setSaveData(playerQuestData);
            redisManager.playerOtherSet(playerQuest);
            // 添加到本地缓存
            playerQuestDataMap.put(playerId, playerQuest);
            return playerQuest;
        } catch (Exception e) {
            log.error("加载玩家任务数据失败", e);
            return null;
        }
    }

    /**
     * 移除玩家充值本地缓存数据
     *
     * @param playerId
     * @param save
     */
    private void removePlayerPayData(Long playerId, boolean save) {
        try {
            PlayerPay playerPay = playerPayDataMap.remove(playerId);
            if (playerPay != null) {
                if (save) {
                    // 移除时保存一次
                    byte[] playerPayData = PlayerPayDbUtils.buildPlayerPayBean(playerPay).toByteArray();
                    playerPay.setSaveData(DataZipUtil.dataEncode(playerPayData));
                    redisManager.playerOtherSet(playerPay);
                    saveManager.getPlayerSave().deal(playerPay, SaveServer.UPDATE);
                }
            } else {
                log.error("移除玩家充值本地缓存数据异常，本地缓存没有玩家充值数据playerId=" + playerId);
            }
        } catch (Exception e) {
            log.error("移除玩家充值本地缓存数据", e);
        }
    }

    /**
     * 获取玩家（本方法限定玩家本人调用）
     *
     * @param player
     * @param isChangeSave true=取出数据需要改变并且保存
     * @return
     */
    public PlayerPay getPlayerPay(WebPlayer player, boolean isChangeSave) {
        try {
            PlayerPay playerPay = playerPayDataMap.get(player.getPlayerId());
            if (playerPay == null) {
                // 如果本地缓存没有此player，再从redis获取
                playerPay = redisManager.playerPayGet(player.getPlayerId());
                if (playerPay != null) {
                    // 添加到本地缓存
                    playerPayDataMap.put(player.getPlayerId(), playerPay);
                }
            }
            // 如果缓存没有player数据，本方法都是玩家本人调用，所以内存缓存找不到直接去数据库捞取数据
            if (playerPay == null) {
                playerPay = loadPlayerPay(player.getPlayerId());
            }
            // 取出玩家充值数据，如果需要改变数据内容，那么肯定需要保存，因为timer保存和玩家操作肯定属于一个线程（玩家所在线程），所以这里先设置，后面触发保存
            if (isChangeSave) {
                playerPay.setSave(true);
            }
            return playerPay;
        } catch (Exception e) {
            log.error("获取玩家（本方法限定玩家本人调用）player=" + player.getPlayerId(), e);
            return null;
        }
    }

    /**
     * 从数据库加载玩家充值数据(强烈建议先调用getPlayer方法找不到player才能调用此方法)
     *
     * @param playerId
     * @return
     */
    private PlayerPay loadPlayerPay(Long playerId) {
        try {
            PlayerPay playerPay = mongoTemplate.findOne(Query.query(Criteria.where("playerId").is(playerId)), PlayerPay.class);
            if (playerPay == null) {
                // 兼容老玩家新充值存储数据,初始化充值数据，或者新开发的功能需要这里兼容修复
                playerPay = new PlayerPay();
                playerPay.setPlayerId(playerId);
                playerPayDataMap.put(playerId, playerPay);
                // 存入mongo
                saveManager.getPlayerSave().deal(playerPay, SaveServer.INSERT);
                log.error("从数据库加载玩家充值数据不存在playerId=" + playerId + " 进行修复充值数据！");
                return playerPay;
            }
            // 从数据库加载的玩家数据缓存到redis，如果redis已存在（那是因为有代码没有做正确的获取玩家操作顺序），则redis不做任何操作
            byte[] data = PlayerPayDbUtils.buildPlayerPayBean(playerPay).toByteArray();
            byte[] playerPayData = DataZipUtil.dataEncode(data);
            playerPay.setSaveData(playerPayData);
            redisManager.playerOtherSet(playerPay);
            // 添加到本地缓存
            playerPayDataMap.put(playerId, playerPay);
            return playerPay;
        } catch (Exception e) {
            log.error("加载玩家充值数据失败", e);
            return null;
        }
    }

}
