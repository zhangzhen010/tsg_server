package com.game.player.manager;

import com.alibaba.fastjson2.JSON;
import com.game.cache.impl.PlayerCache;
import com.game.data.define.MyDefineConstant;
import com.game.data.define.MyDefineItemChangeReason;
import com.game.data.myenum.MyEnumQuestType;
import com.game.data.myenum.MyEnumResourceId;
import com.game.log.manager.LogsManager;
import com.game.login.structs.ResetPlayerType;
import com.game.pack.manager.PackManager;
import com.game.player.structs.GachaCardRefundType;
import com.game.quest.manager.QuestManager;
import com.game.redis.manager.RedisManager;
import com.game.redis.structs.RedisKey;
import com.game.save.manager.SaveManager;
import com.game.server.SaveServer;
import com.game.server.WebServer;
import com.game.utils.GameUtil;
import com.game.utils.ID;
import com.game.utils.TimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 玩家管理类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 15:04
 */
@Component
@Log4j2
public class PlayerManager {

    private @Resource RedisManager redisManager;
    private @Resource MongoTemplate mongoTemplate;
    private @Resource PlayerOtherManager playerOtherManager;
    private @Resource QuestManager questManager;
    private @Resource SaveManager saveManager;
    private @Resource LogsManager logsManager;
    private @Resource PackManager packManager;

    /**
     * 数据库日志
     */
    private final Logger dbLog = LogManager.getLogger("DB");
    /**
     * 玩家当前最大版本号
     */
    private final int PLAYER_VERSION = 1;
    /**
     * 所有玩家缓存 key=playerId value=Player
     */
    private final PlayerCache players = new PlayerCache();

    private @Value("${server.serverId}") int serverId;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        try {
            // 启动检查玩家id是否正常
            boolean playerIdExist = redisManager.redisKeyExist(redisManager.getCenterJedis(), RedisKey.PLAYER_ID, null, null);
            if (!playerIdExist) {
                // 这个都为空了，也不在乎查询全部user数据了，如果是新服更没有影响
                List<WebPlayer> userList = mongoTemplate.findAll(WebPlayer.class);
                if (userList.isEmpty()) {
                    // 如果不存在有两种情况，一种是正常确实是新开服务器，那么进行初始化,从10000开始
                    long newPlayerId = redisManager.stringIncrBy(redisManager.getCenterJedis(), RedisKey.PLAYER_ID, null, null, MyDefineConstant.PLAYERID_INIT);
                    log.info("正常初始化全局玩家ID：" + newPlayerId);
                } else {
                    // 这种情况就比较严重了，有用户的情况下，从redis找不到玩家id，那么标识缓存丢失，需要全局查找检查恢复playerId
                    long currentMaxPlayerId = MyDefineConstant.PLAYERID_INIT;
                    for (int i = 0, len = userList.size(); i < len; i++) {
                        WebPlayer webPlayer = userList.get(i);
                        currentMaxPlayerId = Math.max(currentMaxPlayerId, webPlayer.getPlayerId());
                    }
                    // 每次恢复防止有未统计到的玩家id，再最大值基础上+10000
                    long newPlayerId = redisManager.stringIncrBy(redisManager.getCenterJedis(), RedisKey.PLAYER_ID, null, null, MyDefineConstant.PLAYERID_INIT + currentMaxPlayerId);
                    log.error("异常恢复全局玩家ID：" + newPlayerId);
                }
            }
        } catch (Exception e) {
            log.error("初始异常：", e);
        }
    }

    /**
     * 名字检查重复 这个方法在多线程调用下还是有几率出现角色名重复，但是极个别名字重复影响不大
     *
     * @param name
     * @return
     */
    public boolean isExistName(String name) {
        try {
            // 跨服检查名字是否重复
            if (redisManager.redisKeyExist(redisManager.getCenterJedis(), RedisKey.ALL_PLAYER_NAME_ID_MAP, null, String.valueOf(name.hashCode()))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("名字检查重复", e);
            return false;
        }
    }

    /**
     * 获取玩家
     *
     * @param playerId
     * @param isLoadDB 缓存找不到是否从db加载数据
     * @return
     */
    public WebPlayer getPlayer(Long playerId, boolean isLoadDB) {
        synchronized (players) {
            WebPlayer player = players.get(playerId);
            // 如果缓存没有player数据
            if (player == null && isLoadDB) {
                player = loadPlayer(playerId);
                checkPlayerNewVersion(player);
            }
            return player;
        }
    }

    /**
     * 从数据库加载玩家(强烈建议先调用getPlayer方法找不到player才能调用此方法)
     *
     * @param playerId
     * @return
     */
    private WebPlayer loadPlayer(Long playerId) {
        try {
            WebPlayer player = mongoTemplate.findOne(Query.query(Criteria.where("playerId").is(playerId)), WebPlayer.class);
            if (player == null) {
                log.error("从数据库加载玩家不存在playerId=" + playerId);
                return null;
            }
            // 添加到本地缓存
            players.put(playerId, player);
            return player;
        } catch (Exception e) {
            log.error("加载玩家失败", e);
            return null;
        }
    }

    /**
     * 创建一个角色
     *
     * @param userName
     * @return
     */
    public WebPlayer createPlayer(String userName, String walletAddress, String googleEmail) {
        try {
            // 获取玩家全局唯一id
            long newPlayerId = 0;
            newPlayerId = redisManager.stringIncrBy(redisManager.getCenterJedis(), RedisKey.PLAYER_ID, null, null, 1);
            // 防止异常无法正常登录游戏
            if (newPlayerId <= MyDefineConstant.PLAYERID_INIT) {
                newPlayerId = ID.getId(serverId);
            }
            // 默认初始名字
            String playerName = "Player" + newPlayerId;
            WebPlayer player = new WebPlayer();
            player.setPlayerId(newPlayerId);
            player.setUserName(userName);
            player.setAccount(userName);
            player.setPlayerName(playerName);
            player.setCreateTime(System.currentTimeMillis());
            player.setWalletAddress(walletAddress);
            player.setEmail(googleEmail);
            // 添加到本地缓存
            players.put(player.getPlayerId(), player);
            // 初始化玩家其他数据
            playerOtherManager.createPlayerOther(player);
            // 发送日志
            logsManager.savePlayerCreateLog(player);
            // 初始化角色
            if (!initPlayer(player)) {
                log.error("初始化角色失败！");
                return null;
            }
            savePlayer(player, true);
            log.info("创建新角色成功！playerId=" + player.getPlayerId() + " userName=" + player.getUserName());
            return player;
        } catch (Exception e) {
            log.error("创建一个角色异常：", e);
            return null;
        }
    }

    /**
     * 初始化角色
     *
     * @param player
     * @return
     */
    public boolean initPlayer(WebPlayer player) {
        try {
            // 当前时间
            long currentTime = System.currentTimeMillis();
            // 上一次零点刷新时间
            player.setEverydayRefreshTime(TimeUtil.getTimeInMillisByHour(currentTime, 0));
            // 今日四点的时间
            long refreshTime = TimeUtil.getTimeInMillisByHour(currentTime, 4);
            if (currentTime < refreshTime) {// 还未到刷新的时候,保留当日刷新
                // 记录刷新昨日
                long zuoriTime = currentTime - TimeUtil.DAY_MILLIS;
                // 记录4点刷新时间
                player.setEverydaySiDianRefreshTime(TimeUtil.getTimeInMillisByHour(zuoriTime, 4));
            } else {// 超过本日四点刷新时间
                // 记录4点刷新时间
                player.setEverydaySiDianRefreshTime(TimeUtil.getTimeInMillisByHour(currentTime, 4));
            }
            // 获取任务数据
            PlayerQuest playerQuest = playerOtherManager.getPlayerQuest(player, true);
            // 初始化周刷新和月刷新时间
            playerQuest.setWeekLastResetTime(currentTime);
            playerQuest.setMonthLastResetTime(currentTime);
            // 接取每日任务
            questManager.questReceive(player, MyEnumQuestType.EVERY_DAY);
            // 接取主线任务
            questManager.questReceive(player, MyEnumQuestType.MAIN);
            // 新建角色版本号与当前最新版本号一致
            player.setVer(PLAYER_VERSION);
            return true;
        } catch (Exception e) {
            log.error("初始化角色异常", e);
            return false;
        }
    }

    /**
     * 更新玩家的新名字
     *
     * @param player
     * @param newName
     */
    public void updatePlayerNewName(WebPlayer player, String newName) {
        try {
            // 获取之前的名字
            String oldName = player.getPlayerName();
            // 设置新名字
            player.setPlayerName(newName);
            // 更新本服名字id对照map redis
            redisManager.playerNameIdUpdate(RedisKey.ALL_PLAYER_NAME_ID_MAP, oldName, newName, player.getPlayerId());
        } catch (Exception e) {
            log.error("更新玩家的新名字", e);
        }
    }

    /**
     * 登陆离线检查 （避免全局更新数据库 只维护活跃用户）
     *
     * @param player
     */
    public void checkOffLine(WebPlayer player) {
        try {
            // 凌晨零点
            resetPlayer(player, ResetPlayerType.ONEDAYPLAYER);
            // 凌晨4点
            resetPlayer(player, ResetPlayerType.SIDIANPLAYER);
        } catch (Exception e) {
            log.error("重置天数属性计算 （避免全局更新数据库 只维护活跃用户）异常", e);
        }
    }

    /**
     * 重置 玩家 属性（如 天数属性等）
     *
     * @param player
     */
    public void resetPlayer(WebPlayer player, ResetPlayerType resetPlayerType) {
        try {
            if (resetPlayerType == ResetPlayerType.ONEDAYPLAYER) {
                /* 一般情况下玩家属性发生变化这里要手动更新到客户端，其他功能属性由进入功能更新 */
                long currentTime = System.currentTimeMillis();
                // 验证是否隔天（凌晨零点）
                int day = TimeUtil.getDayNum(player.getEverydayRefreshTime());// 上一次零点刷新时间
                int today = TimeUtil.getDayNum(currentTime);
                if (day != today) {// 当日首次登陆(处理)
                    // 是否也属于跨周
                    boolean isSameWeek = TimeUtil.isSameWeek(player.getEverydayRefreshTime(), currentTime);
                    // 更新刷新时间
                    player.setEverydayRefreshTime(TimeUtil.getTimeInMillisByHour(currentTime, 0));
                    // 修改玩家创建天数
                    player.setCreateDay(getPlayerCreateDay(player));
                    // 重置任务数据
                    questManager.resetQuest(player);
                    // 接取每日任务（检查是否有新的每日任务可接取）
                    questManager.questReceive(player, MyEnumQuestType.EVERY_DAY);
                    // 跨周处理
                    if (!isSameWeek) {

                    }
                    // 累计登录天数
                    player.setLoginDay(player.getLoginDay() + 1);
                    // 保存玩家数据
                    savePlayer(player);
                } else {

                }
            } else if (resetPlayerType == ResetPlayerType.SIDIANPLAYER) {// 每日四点刷新
                // 验证是否隔天（凌晨4点）
                long sidianrefrenTime = player.getEverydaySiDianRefreshTime() + TimeUtil.DAY_MILLIS;// 上次刷新超出这个时间则重新刷新
                if (System.currentTimeMillis() >= sidianrefrenTime) {
                    // 当前时间
                    long now = System.currentTimeMillis();
                    // 今日四点的时间
                    long refrensTime = TimeUtil.getTimeInMillisByHour(now, 4);
                    if (now < refrensTime) {// 还未到刷新的时候,保留当日刷新
                        // 记录刷新昨日
                        long zuoriTime = now - TimeUtil.DAY_MILLIS;
                        // 记录4点刷新时间
                        player.setEverydaySiDianRefreshTime(TimeUtil.getTimeInMillisByHour(zuoriTime, 4));
                        // 保存玩家数据（暂时没有4点刷新逻辑）
//                        savePlayer(player);
                    } else {// 小于等于刷新时间
                        // 记录4点刷新时间
                        player.setEverydaySiDianRefreshTime(refrensTime);
                    }
                }
            }
        } catch (Exception e) {
            log.error("重置 玩家 属性（如 天数属性等）player=" + player.getPlayerId(), e);
        }
    }

    /**
     * 保存所有玩家数据（关服时候调用）
     */
    public void exitServerSaveAllPlayer() {
        try {
            long currentTime = System.currentTimeMillis();
            synchronized (players) {
                List<WebPlayer> allPlayerList = new ArrayList<>();
                Set<Entry<Long, WebPlayer>> entrySet = players.getCache().entrySet();
                log.info("close server save Player num = " + entrySet.size());
                for (Entry<Long, WebPlayer> en : entrySet) {
                    WebPlayer player = en.getValue();
                    // 记录需要保存的角色
                    allPlayerList.add(player);
                }
                // 保存所有已缓存角色数据
                savePlayerListToDb(allPlayerList);
            }
            log.info("保存所有玩家数据成功!耗时：" + (System.currentTimeMillis() - currentTime) + "ms");
        } catch (Exception e) {
            log.error("保存所有玩家数据（关服时候调用）", e);
        }
    }

    /**
     * 获取角色创建到现在的天数，当天算第一天，返回1
     *
     * @param player
     * @return
     */
    public int getPlayerCreateDay(WebPlayer player) {
        return TimeUtil.getDifferDays(player.getCreateTime(), System.currentTimeMillis()) + 1;
    }

    /**
     * 手动调用保存角色
     *
     * @param player
     */
    public void savePlayer(WebPlayer player) {
        savePlayer(player, false);
    }

    /**
     * 保存角色
     *
     * @param player
     * @param isCreate
     */
    public void savePlayer(WebPlayer player, boolean isCreate) {
        try {
            // 保存角色数据
            if (isCreate) {
                // 创建为了数据安全，直接本地保存
                saveManager.getPlayerSave().deal(player, SaveServer.INSERT);
            } else {
                // 本地保存数据(这里去其他线程保存，有数据同步问题，如果要解决可以在玩家线程拷贝一份数据丢入进去存储)
                saveManager.getPlayerSave().deal(player, SaveServer.UPDATE);
            }
            // 保存玩家其他数据
            playerOtherManager.savePlayerOther(player, isCreate);
            dbLog.info("本地保存玩家其他数据playerId=" + player.getPlayerId());
        } catch (Exception e) {
            log.error("保存角色（保存中间线程调用）", e);
        }
    }

    /**
     * 保存角色（关服调用）
     *
     * @param allPlayerList
     */
    public void savePlayerListToDb(List<WebPlayer> allPlayerList) {
        try {
            for (int i = 0, len = allPlayerList.size(); i < len; i++) {
                WebPlayer player = allPlayerList.get(i);
                mongoTemplate.save(player);
            }
            // 关服保存所有缓存存在的玩家其他数据
            playerOtherManager.savePlayerOtherAll(allPlayerList);
        } catch (Exception e) {
            log.error("保存角色（关服调用）", e);
        }
    }

    /**
     * 进入游戏
     *
     * @param player
     */
    public void enterGame(WebPlayer player) {
        try {
            long currentTime = System.currentTimeMillis();
            // 修改玩家创建天数
            player.setCreateDay(getPlayerCreateDay(player));
            // 是否连续登录
            boolean isContinuous = TimeUtil.getDifferDays(player.getLoginTime(), currentTime) == 1;
            // 隔天登录
            boolean isSameDay = TimeUtil.isSameDay(player.getLoginTime(), currentTime);
            // 后续在reset方法触发
            if (!isSameDay && player.getCreateDay() == 1) {
                // 累计登录天数
                player.setLoginDay(player.getLoginDay() + 1);
            }
            // 设置登录时间
            player.setLoginTime(currentTime);
            // 检查最后下线时间以及每日刷新
            checkOffLine(player);
            // 上面reset这里在采集任务
            if (!isSameDay) {
                if (isContinuous) {
                    player.setLoginDayCon(player.getLoginDayCon() + 1);
                } else {
                    player.setLoginDayCon(0);
                }
            }
            // 验证玩家登录新版本功能兼容
            checkPlayerNewVersion(player);
            // 检查玩家任务是否过期
            questManager.checkPlayerQuest(player);
            // 发送日志
            logsManager.savePlayerLoginLog(player);
            // 进入游戏保存一次数据
            savePlayer(player);
            // 设置玩家最后一次登录的逻辑服务器id到redis
            redisManager.stringSet(redisManager.getCenterJedis(), RedisKey.PLAYER_LOGIC_SERVER_ID, null, Long.toString(player.getPlayerId()), WebServer.serverIdString.getBytes(), 0);
            log.info("登录角色player耗时：" + (System.currentTimeMillis() - currentTime));
        } catch (Exception e) {
            log.error("进入游戏异常player=" + player.getPlayerId(), e);
        }
    }

    /**
     * 玩家登录验证新版本功能兼容
     *
     * @param player
     */
    private void checkPlayerNewVersion(WebPlayer player) {
        try {
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
            // 是否有新增加资源货币（此兼容需永久存在）(放在这里是防止加载其他玩家出现货币没有初始化的问题)
            List<MyEnumResourceId> resourceValueList = MyEnumResourceId.getList();
            for (int i = 0, len = resourceValueList.size(); i < len; i++) {
                Integer resourceId = resourceValueList.get(i).getId();
                if (!playerPack.getResourceMap().containsKey(resourceId)) {
                    playerPack.getResourceMap().put(resourceId, 0L);
                }
            }
            {
                /* 用于玩家版本兼容统一处理，根据不同的版本号做不同的兼容处理 */
                for (int i = player.getVer(); i < PLAYER_VERSION; i++) {
                    if (i == 0) {
                        // 兼容逻辑

                        // 兼容后改为1
                        player.setVer(i + 1);
                        log.info("玩家：[" + player.getPlayerId() + "]更新兼容版本：" + player.getVer());
                    }
                }
            }
        } catch (Exception e) {
            log.error("玩家登录验证新版本功能兼容", e);
        }
    }

    /**
     * 销毁卡片返还资源
     *
     * @param playerId
     * @param gachaCardId
     * @param gachaCardRefund
     */
    public void burnCard(long playerId, String gachaCardId, GachaCardRefund gachaCardRefund) {
        try {
            WebPlayer player = getPlayer(playerId, true);
            if (player == null) {
                log.error("销毁卡片返还资源异常：玩家不存在，playerId=" + playerId);
                return;
            }
            GachaCard gachaCard = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(gachaCardId)), GachaCard.class);
            if (gachaCard == null) {
                log.error("销毁卡片返还资源异常：卡牌不存在，playerId=" + player.getPlayerId() + " id=" + gachaCardId);
                return;
            }
            if (gachaCard.getOwnerPlayerId() <= 0) {
                log.error("销毁卡片返还资源异常：卡牌还未被抽取，playerId=" + player.getPlayerId() + " id=" + gachaCardId);
                return;
            }
            // 卡片被玩家在网上交易了，这里判断不了
//            if (gachaCard.getOwnerPlayerId() != playerId) {
//                log.error("销毁卡片返还资源异常：卡牌不是当前玩家，id=" + gachaCardId);
//                return;
//            }
            if (gachaCard.isBurn()) {
                log.error("销毁卡片返还资源异常：卡牌已销毁，playerId=" + playerId + " id=" + gachaCardId);
                return;
            }
            if (gachaCard.getBurnCandyRatio() < 0 || gachaCard.getBurnCandyRatio() > GameUtil.bfb) {
                log.error("销毁卡片返还资源异常：卡牌销毁比例异常，playerId=" + playerId + " data=" + JSON.toJSONString(gachaCard));
                return;
            }
            if (gachaCard.getBurnFtRatio() < 0 || gachaCard.getBurnFtRatio() > GameUtil.bfb) {
                log.error("销毁卡片返还资源异常：卡牌销毁比例异常，playerId=" + playerId + " data=" + JSON.toJSONString(gachaCard));
                return;
            }
            // 设置已销毁
            gachaCard.setBurn(true);
            gachaCard.setOwnerPlayerId(0);
            mongoTemplate.save(gachaCard);
            // 移除玩家背包卡片
            packManager.removeCard(player, Long.parseLong(gachaCardId), MyDefineItemChangeReason.SOLANA_BURN);
            // 计算返利
            if (gachaCardRefund.getType() == GachaCardRefundType.CANDY.getType()) {
                // candy与美分比例 1:10，即1000candy=1美元
                int addCandy = (int) (gachaCard.getUsd() / GameUtil.bfb * gachaCard.getBurnCandyRatio())  * 10;
                if (addCandy <= 0) {
                    log.error("销毁卡片返还资源异常：卡牌销毁比例异常，playerId=" + playerId + " data=" + JSON.toJSONString(gachaCard) + " addCandy=" + addCandy);
                    return;
                }
                // 返利candy
                packManager.addItemByConfigId(player, MyEnumResourceId.CANDY.getId(), addCandy, MyDefineItemChangeReason.SOLANA_BURN);
                log.info("玩家成功销毁卡片返还Candy资源：playerId=" + playerId + " id=" + gachaCardId + " addCandy=" + addCandy);
            } else if (gachaCardRefund.getType() == GachaCardRefundType.FT.getType()) {
                // 设置卡片xiangq
                gachaCardRefund.setGachaCard(gachaCard);
                mongoTemplate.insert(gachaCardRefund);
                log.info("玩家成功销毁卡片返还FT资源(仅作记录)：playerId=" + playerId + " id=" + gachaCardId + " gachaCardRefundId=" + gachaCardRefund.getId());
            }
            log.info("玩家成功销毁卡片返还资源：playerId=" + playerId + " id=" + gachaCardId);
        } catch (Exception e) {
            log.error("销毁卡片返还资源异常：", e);
        }
    }

}