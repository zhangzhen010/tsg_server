package com.game.rank.manager;

import com.game.bean.proto.BeanProto;
import com.game.player.manager.PlayerManager;
import com.game.player.structs.WebPlayer;
import com.game.rank.structs.RankPlayerInfo;
import com.game.rank.structs.ResRankCandy;
import com.game.redis.manager.RedisManager;
import com.game.redis.structs.RedisKey;
import com.game.utils.GameUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import redis.clients.jedis.resps.Tuple;

import java.util.ArrayList;
import java.util.List;

/**
 * 排行榜管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/14 17:35
 */
@Component
@Log4j2
public class RankManager {

    private @Resource RedisManager redisManager;
    private @Resource PlayerManager playerManager;

    /**
     * 请求糖果榜
     *
     * @param player
     * @return
     */
    public ResRankCandy reqRankCandyInfo(WebPlayer player) {
        try {
            // 处理逻辑[排行榜信息]
            ResRankCandy resRankCandy = new ResRankCandy();
            // 获取排行榜数据
            List<Tuple> tupleList = redisManager.rankZrevrange(redisManager.getCenterJedis(), RedisKey.RANK, 99, null, BeanProto.E_WEB_RANK_TYPE.RANK_TYPE_CANDY.name());
            // 获取本人排行数据
            int rank = redisManager.rankZrevrank(redisManager.getCenterJedis(), RedisKey.RANK, player.getPlayerId(), null, BeanProto.E_WEB_RANK_TYPE.RANK_TYPE_CANDY.name());
            // 获取总榜积分
            long score = redisManager.rankZscore(redisManager.getCenterJedis(), RedisKey.RANK, player.getPlayerId(), null, BeanProto.E_WEB_RANK_TYPE.RANK_TYPE_CANDY.name());
            // 解码积分
            score = GameUtil.decodeRankScoreByTime(score);
            // 获取每日榜积分(每日榜不显示排名，积分不解码)
            long todayScore = redisManager.rankZscore(redisManager.getCenterJedis(), RedisKey.RANK, player.getPlayerId(), null, BeanProto.E_WEB_RANK_TYPE.RANK_TYPE_CANDY_DAY.name());
            resRankCandy.setSelfRank(rank);
            resRankCandy.setSelfTodayCandy((int) todayScore);
            resRankCandy.setSelfCandy((int) score);
            resRankCandy.setAvatarUrl(player.getAvatarUrl());
            // 排行玩家id列表
            List<Long> playerIdList = new ArrayList<>();
            for (Tuple tuple : tupleList) {
                long playerId = Long.parseLong(tuple.getElement());
                playerIdList.add(playerId);
            }
            List<Object> objectList = redisManager.rankZscore(redisManager.getCenterJedis(), RedisKey.RANK, playerIdList, null, BeanProto.E_WEB_RANK_TYPE.RANK_TYPE_CANDY_DAY.name());
            for (int i = 0; i < tupleList.size(); i++) {
                Tuple tuple = tupleList.get(i);
                long playerId = Long.parseLong(tuple.getElement());
                WebPlayer targetPlayer = playerManager.getPlayer(playerId, true);
                RankPlayerInfo rankPlayerInfo = new RankPlayerInfo();
                rankPlayerInfo.setPlayerName(targetPlayer.getPlayerName());
                rankPlayerInfo.setAvatarUrl(targetPlayer.getAvatarUrl());
                // 今日糖果分每日重置后可能为null
                Object todaySocre = objectList.get(i);
                if(todaySocre != null){
                    rankPlayerInfo.setTodayCandyScore((int) Double.parseDouble(String.valueOf(todaySocre)));
                }
                rankPlayerInfo.setCandyScore((int) GameUtil.decodeRankScoreByTime((long) tuple.getScore()));
                resRankCandy.getRankPlayerInfoList().add(rankPlayerInfo);
            }
            return resRankCandy;
        } catch (Exception e) {
            log.error("请求糖果榜异常：", e);
            return null;
        }
    }

    /**
     * 重置每日排行榜
     */
    public void resetEveryDayRank() {
        try {
            redisManager.rankDel(redisManager.getCenterJedis(), RedisKey.RANK, null, BeanProto.E_WEB_RANK_TYPE.RANK_TYPE_CANDY_DAY.name());
            log.info("重置每日排行榜成功！");
        } catch (Exception e) {
            log.error("重置每日排行榜异常：", e);
        }
    }

}