package com.game.player.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.game.config.CurrentPlayer;
import com.game.config.OrangeWebConfig;
import com.game.controller.structs.ResponseBean;
import com.game.controller.structs.ResponseCodeEnum;
import com.game.data.define.MyDefineItemChangeReason;
import com.game.data.myenum.MyEnumResourceId;
import com.game.discord.manager.DiscordManager;
import com.game.login.structs.ResetPlayerType;
import com.game.pack.manager.PackManager;
import com.game.player.manager.PlayerManager;
import com.game.player.manager.PlayerOtherManager;
import com.game.player.structs.*;
import com.game.player.timer.SolanaAddBurnQueueTimer;
import com.game.player.timer.SolanaAddTransferQueueTimer;
import com.game.redis.manager.RedisManager;
import com.game.thread.manager.ThreadManager;
import com.game.twitter.manager.TwitterManager;
import com.game.utils.*;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 玩家控制器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/14 11:09
 */
@RestController
@Log4j2
@RequestMapping(value = "/tsg/player")
//@CrossOrigin(origins = "http://example.com") 后期支持跨域使用，为了安全起见，指定允许的源
public class PlayerController {

    private @Resource PlayerManager playerManager;
    private @Resource DiscordManager discordManager;
    private @Resource TwitterManager twitterManager;
    private @Resource PlayerOtherManager playerOtherManager;
    private @Resource MongoTemplate mongoTemplate;
    private @Resource PackManager packManager;
    private @Resource RedisManager redisManager;
    private @Resource ThreadManager threadManager;

    /**
     * 请求每日刷新
     *
     * @param player
     * @return
     */
    @PostMapping("/refresh")
    public ResponseBean<Object> refresh(@CurrentPlayer WebPlayer player) {
        try {
            // 请求刷新
            playerManager.resetPlayer(player, ResetPlayerType.ONEDAYPLAYER);
            // 后面补充需要刷新返回的内容
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("请求每日刷新", e);
            return ResponseBean.fail("reqPlayerRefresh error: ".concat(e.getMessage()));
        }
    }

//    /**
//     * 设置玩家头像
//     *
//     * @param player    当前玩家
//     * @param avatarUrl 头像文件
//     * @return 响应结果
//     */
//    @PostMapping("/updateAvatar")
//    public ResponseBean<JSONObject> updateAvatar(@CurrentPlayer WebPlayer player, @RequestParam("avatarUrl") String avatarUrl) {
//        try {
//            if (avatarUrl.length() > 512) {
//                return ResponseBean.fail("avatarUrl is too long.playerId=" + player.getPlayerId());
//            }
////            if (avatarUrl.isEmpty()) {
////                return ResponseBean.fail("avatarFile is null.playerId=" + player.getPlayerId());
////            }
////            // 如果之前有上传的头像，需要先删除之前的文件
////            if (!player.getAvatarPath().isEmpty()) {
////                File oldAvatarFile = new File(player.getAvatarPath());
////                if (oldAvatarFile.exists()) {
////                    if (oldAvatarFile.delete()) {
////                        log.error("删除玩家旧头像成功，playerId=" + player.getPlayerId() + " path=" + player.getAvatarPath());
////                    }
////                }
////            }
////            // 这里应该调用文件上传服务，返回头像的URL
////            String avatarUrl = uploadAvatar(player, avatarFile);
////            if (StringUtil.isEmptyOrNull(avatarUrl)) {
////                return ResponseBean.fail("avatarFile is error.playerId=" + player.getPlayerId());
////            }
//            // 更新玩家头像URL
//            player.setAvatarUrl(avatarUrl);
//            // 保存玩家数据
//            playerManager.savePlayer(player);
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("playerId", player.getPlayerId());
//            jsonObject.put("playerName", player.getPlayerName());
//            return ResponseBean.success(jsonObject);
//        } catch (Exception e) {
//            log.error("设置头像时发生错误", e);
//            return ResponseBean.fail("设置头像失败: ".concat(e.getMessage()));
//        }
//    }

//    /**
//     * 获取玩家头像使用第三方库，客户端自己存储
//     */
//    @GetMapping("/getAvatar")
//    public void resourceDownload(@CurrentPlayer WebPlayer player, @RequestParam("avatarUrl") String avatarUrl, HttpServletResponse response) {
//        try {
//            if (StringUtil.isEmptyOrNull(avatarUrl)) {
//                avatarUrl = player.getAvatarUrl();
//            }
//            if (!FileUtils.checkAllowDownload(avatarUrl)) {
//                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", player.getAvatarUrl()));
//            }
//            // 本地资源路径
//            String localPath = OrangeWebConfig.getProfile();
//            // 数据库资源地址
//            String downloadPath = localPath + StringUtils.substringAfter(player.getAvatarUrl(), Constants.RESOURCE_PREFIX);
//            // 下载名称
//            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
//            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//            FileUtils.setAttachmentResponseHeader(response, downloadName);
//            FileUtils.writeBytes(downloadPath, response.getOutputStream());
//        } catch (Exception e) {
//            log.error("获取玩家头像失败", e);
//        }
//    }

//    /**
//     * 设置玩家名字
//     *
//     * @param player     当前玩家
//     * @param playerName 新的名字
//     * @return 响应结果
//     */
//    @PostMapping("/updateName")
//    public ResponseBean<JSONObject> updateName(@CurrentPlayer WebPlayer player, @NotBlank @RequestParam("playerName") String playerName) {
//        try {
//            // 昵称设置
//            String newName = GameUtil.checkName(playerName);
//            // 验证名字长度
//            if (newName.length() < 2 || newName.length() > 50) {
//                log.error("玩家名字太长,playerId=" + player.getPlayerId());
//                return ResponseBean.fail("player names are too long!");
//            }
//            // 验证名字是否重复
//            if (playerManager.isExistName(newName)) {
//                log.error("玩家名字重复：playerId=" + player.getPlayerId() + " name=" + newName);
//                return ResponseBean.fail("duplicate player names!");
//            }
//            // 更新玩家名字
//            playerManager.updatePlayerNewName(player, newName);
//            // 保存玩家数据
//            playerManager.savePlayer(player);
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("playerId", player.getPlayerId());
//            jsonObject.put("playerName", player.getPlayerName());
//            return ResponseBean.success(jsonObject);
//        } catch (Exception e) {
//            log.error("设置名字时发生错误", e);
//            return ResponseBean.fail("update name fail: ".concat(e.getMessage()));
//        }
//    }

    /**
     * 设置玩家修改信息
     *
     * @param player              当前玩家
     * @param reqUpdatePlayerInfo 修改的信息
     * @return 响应结果
     */
    @PostMapping("/updatePlayerInfo")
    public ResponseBean<JSONObject> updatePlayerInfo(@CurrentPlayer WebPlayer player, @RequestBody ReqUpdatePlayerInfo reqUpdatePlayerInfo) {
        try {
            boolean isSave = false;
            // 不为空修改头像地址
            if (!reqUpdatePlayerInfo.getAvatarUrl().isEmpty()) {
                if (reqUpdatePlayerInfo.getAvatarUrl().length() > 512) {
                    return ResponseBean.fail("avatarUrl is too long.playerId=" + player.getPlayerId());
                }
                // 更新玩家头像URL
                player.setAvatarUrl(reqUpdatePlayerInfo.getAvatarUrl());
                isSave = true;
            }
            // 不为空修改名字
            if (!reqUpdatePlayerInfo.getPlayerName().isEmpty()) {
                // 昵称设置
                String newName = GameUtil.checkName(reqUpdatePlayerInfo.getPlayerName());
                // 验证名字长度
                if (newName.length() < 2 || newName.length() > 50) {
                    log.error("玩家名字太长,playerId=" + player.getPlayerId());
                    return ResponseBean.fail("player names are too long!");
                }
                // 验证名字是否重复
                if (playerManager.isExistName(newName)) {
                    log.error("玩家名字重复：playerId=" + player.getPlayerId() + " name=" + newName);
                    return ResponseBean.fail(ResponseCodeEnum.FAIL_PLAYER_NAME_EX, "duplicate player names!");
                }
                // 更新玩家名字
                playerManager.updatePlayerNewName(player, newName);
                isSave = true;
            }
            // 不为空修改网站显示的userName
            if (!reqUpdatePlayerInfo.getUserName().isEmpty()) {
                player.setWebUserName(reqUpdatePlayerInfo.getUserName());
                isSave = true;
            }
            // 不为空修改邮箱
            if (!reqUpdatePlayerInfo.getEmail().isEmpty()) {
                player.setEmail(reqUpdatePlayerInfo.getEmail());
                isSave = true;
            }
            // 不为空修改地址
            if (!reqUpdatePlayerInfo.getAddress().isEmpty()) {
                player.setAddress(reqUpdatePlayerInfo.getAddress());
                isSave = true;
            }
            if (isSave) {
                // 保存玩家数据
                playerManager.savePlayer(player);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("playerId", player.getPlayerId());
            jsonObject.put("playerName", player.getPlayerName());
            jsonObject.put("userName", player.getWebUserName());
            jsonObject.put("avatarUrl", player.getAvatarUrl());
            jsonObject.put("email", player.getEmail());
            jsonObject.put("address", player.getAddress());
            return ResponseBean.success(jsonObject);
        } catch (Exception e) {
            log.error("设置玩家修改信息错误", e);
            return ResponseBean.fail("update player info fail: ".concat(e.getMessage()));
        }
    }

    /**
     * 获取玩家信息
     *
     * @param player
     * @return
     */
    @PostMapping("/playerInfo")
    public ResponseBean<JSONObject> updatePlayerInfo(@CurrentPlayer WebPlayer player) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account", player.getUserName());
            jsonObject.put("playerId", player.getPlayerId());
            jsonObject.put("playerName", player.getPlayerName());
            jsonObject.put("userName", player.getWebUserName());
            jsonObject.put("avatarUrl", player.getAvatarUrl());
            jsonObject.put("email", player.getEmail());
            jsonObject.put("address", player.getAddress());
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, false);
            jsonObject.put("candy", playerPack.getResourceMap().get(MyEnumResourceId.CANDY.getId()));
            jsonObject.put("discordUserId", player.getDiscordUserId());
            jsonObject.put("discordUserName", player.getDiscordUserName());
            jsonObject.put("twitterUserId", player.getTwitterUserId());
            jsonObject.put("twitterUserName", player.getTwitterUserName());
            jsonObject.put("telegramUserId", player.getTelegramUserId());
            jsonObject.put("telegramUserName", player.getTelegramUserName());
            return ResponseBean.success(jsonObject);
        } catch (Exception e) {
            log.error("获取玩家信息错误", e);
            return ResponseBean.fail("get player info fail: ".concat(e.getMessage()));
        }
    }

    /**
     * 上传头像文件到服务器，并返回头像URL
     *
     * @param file 文件
     * @return 头像URL
     */
    private String uploadAvatar(WebPlayer player, MultipartFile file) {
        try {
            // 假设使用阿里云OSS存储，这里应该调用文件上传服务，返回头像的URL
            // 目前为了经济考虑，只能存储本地服务器
            return FileUploadUtils.upload(player, OrangeWebConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
        } catch (Exception e) {
            log.error("上传头像文件到服务器，并返回头像URL异常：", e);
            return "";
        }
    }

    /**
     * 绑定discord
     *
     * @param player
     * @param code
     * @return
     */
    @GetMapping("/bindDiscord")
    public ResponseBean<Object> bindDiscord(@CurrentPlayer WebPlayer player, @RequestParam("code") String code) {
        try {
            log.info("请求绑定discord player=" + player.getPlayerId() + " code=" + code);
            if (!player.getDiscordUserId().isEmpty()) {
                log.info("绑定失败，该用户已绑定过！playerId=" + player.getPlayerId());
                return ResponseBean.fail("discordUserId is not empty");
            }
            // 通过code获取access_token
            String accessToken = discordManager.getAccessToken(code);
            // 通过access_token获取用户ID
            JSONObject userJsonObject = discordManager.getUserId(accessToken);
            String userId = userJsonObject.getString("id");
            player.setDiscordUserId(userId);
            player.setDiscordUserName(userJsonObject.getString("username"));
            playerManager.savePlayer(player);
            log.info("绑定discord成功 playerId=" + player.getPlayerId() + "userInfo=" + userJsonObject);
            return ResponseBean.success(userJsonObject);
        } catch (Exception e) {
            log.error("绑定discord异常：", e);
            return ResponseBean.fail("bindDiscord error: ".concat(e.getMessage()));
        }
    }

    /**
     * 解绑 Discord
     *
     * @param player 当前登录的用户
     * @return 响应结果
     */
    @GetMapping("/unbindDiscord")
    public ResponseBean<Object> unbindDiscord(@CurrentPlayer WebPlayer player) {
        try {
            log.info("请求解绑 Discord player=" + player.getPlayerId());
            if (player.getDiscordUserId().isEmpty()) {
                log.info("解绑失败，该用户未绑定 Discord！playerId=" + player.getPlayerId());
                return ResponseBean.fail("discordUserId is empty");
            }
            // 清空 Discord 用户 ID 和用户名
            player.setDiscordUserId("");
            player.setDiscordUserName("");
            playerManager.savePlayer(player);
            log.info("解绑 Discord 成功 playerId=" + player.getPlayerId());
            return ResponseBean.success("Unbind Discord successfully");
        } catch (Exception e) {
            log.error("解绑 Discord 异常：", e);
            return ResponseBean.fail("unbindDiscord error: ".concat(e.getMessage()));
        }
    }


    /**
     * 绑定twitter
     *
     * @param player
     * @param code
     * @return
     */
    @GetMapping("/bindTwitter")
    public ResponseBean<Object> bindTwitter(@CurrentPlayer WebPlayer player, @RequestParam("code") String code, @RequestParam("code_verifier") String code_verifier) {
        try {
            log.info("请求绑定twitter player=" + player.getPlayerId() + " code=" + code + " code_verifier=" + code_verifier);
            if (!player.getTwitterUserId().isEmpty()) {
                log.info("绑定失败，该用户已绑定过！playerId=" + player.getPlayerId());
                return ResponseBean.fail("twitterUserId is not empty");
            }
            String accessToken = twitterManager.getAccessToken(code, code_verifier);
            log.info("绑定twitter获取的token：" + accessToken);
            if (StringUtil.isEmptyOrNull(accessToken)) {
                log.error("绑定twitter获取token失败playerId=" + player.getPlayerId());
                return ResponseBean.fail("get accessToken fail");
            }
            String userInfo = twitterManager.callTwitterApi(accessToken);
            log.info("twitter获取用户信息：" + userInfo);
            // 通过access_token获取用户ID
            JSONObject userJsonObject = JSON.parseObject(userInfo);
            JSONObject dataJsonObject = userJsonObject.getJSONObject("data");
            if (dataJsonObject == null) {
                log.error("twitter获取用户信息失败playerId=" + player.getPlayerId());
                return ResponseBean.fail("get twitter user info fail");
            }
            String twitterUserId = dataJsonObject.getString("id");
            if (StringUtil.isEmptyOrNull(twitterUserId)) {
                log.error("twitter获取用户ID失败playerId=" + player.getPlayerId());
                return ResponseBean.fail("get twitter user id fail");
            }
            player.setTwitterUserId(twitterUserId);
            player.setTwitterUserName(dataJsonObject.getString("name"));
            playerManager.savePlayer(player);
            log.info("绑定twitter成功 playerId=" + player.getPlayerId() + "userInfo=" + userInfo);
            return ResponseBean.success(userJsonObject);
        } catch (Exception e) {
            log.error("绑定twitter异常：", e);
            return ResponseBean.fail("bindTwitter error: ".concat(e.getMessage()));
        }
    }

    /**
     * 解绑 Twitter
     *
     * @param player 当前登录的用户
     * @return 响应结果
     */
    @GetMapping("/unbindTwitter")
    public ResponseBean<Object> unbindTwitter(@CurrentPlayer WebPlayer player) {
        try {
            log.info("请求解绑 Twitter player=" + player.getPlayerId());
            if (player.getTwitterUserId().isEmpty()) {
                log.info("解绑失败，该用户未绑定 Twitter！playerId=" + player.getPlayerId());
                return ResponseBean.fail("twitterUserId is empty");
            }
            // 清空 Twitter 用户 ID 和用户名
            player.setTwitterUserId("");
            player.setTwitterUserName("");
            playerManager.savePlayer(player);
            log.info("解绑 Twitter 成功 playerId=" + player.getPlayerId());
            return ResponseBean.success("Unbind Twitter successfully");
        } catch (Exception e) {
            log.error("解绑 Twitter 异常：", e);
            return ResponseBean.fail("unbindTwitter error: ".concat(e.getMessage()));
        }
    }

    /**
     * 绑定Telegram
     *
     * @param player
     * @param data
     * @return
     */
    @GetMapping("/bindTelegram")
    public ResponseBean<Object> bindTelegram(@CurrentPlayer WebPlayer player, @RequestParam("data") String data) {
        try {
            log.info("请求绑定telegram player=" + player.getPlayerId() + " data=" + data);
            if (!player.getTelegramUserId().isEmpty()) {
                log.info("绑定失败，该用户已绑定过！playerId=" + player.getPlayerId());
                return ResponseBean.fail("telegramUserId is not empty");
            }
            log.info("收到绑定telegram数据=" + data);
            JSONObject userJsonObject = JSON.parseObject(data);
            String userId = userJsonObject.getString("id");
            player.setTelegramUserId(userId);
            player.setTelegramUserName(userJsonObject.getString("first_name") + " " + userJsonObject.getString("last_name"));
            playerManager.savePlayer(player);
            log.info("绑定telegram成功 playerId=" + player.getPlayerId() + "data=" + data);
            return ResponseBean.success(userJsonObject);
        } catch (Exception e) {
            log.error("绑定telegram异常：", e);
            return ResponseBean.fail("bindTelegram error: ".concat(e.getMessage()));
        }
    }

    /**
     * 解绑 Telegram
     *
     * @param player 当前登录的用户
     * @return 响应结果
     */
    @GetMapping("/unbindTelegram")
    public ResponseBean<Object> unbindTelegram(@CurrentPlayer WebPlayer player) {
        try {
            log.info("请求解绑 Telegram player=" + player.getPlayerId());
            if (player.getTelegramUserId().isEmpty()) {
                log.info("解绑失败，该用户未绑定 Telegram！playerId=" + player.getPlayerId());
                return ResponseBean.fail("Telegram is empty");
            }
            // 清空 Telegram 用户 ID 和用户名
            player.setTelegramUserId("");
            player.setTelegramUserName("");
            playerManager.savePlayer(player);
            log.info("解绑 Telegram 成功 playerId=" + player.getPlayerId());
            return ResponseBean.success("Unbind Telegram successfully");
        } catch (Exception e) {
            log.error("解绑 Telegram 异常：", e);
            return ResponseBean.fail("unbindTelegram error: ".concat(e.getMessage()));
        }
    }

    /**
     * 获取背包卡片列表
     *
     * @param player 当前登录的用户
     * @return 响应结果
     */
    @GetMapping("/getCardList")
    public ResponseBean<Object> getCardList(@CurrentPlayer WebPlayer player) {
        try {
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, false);
            List<GachaCard> cardList = new ArrayList<>();
            for (int i = 0; i < playerPack.getCardList().size(); i++) {
                Card card = playerPack.getCardList().get(i);
                cardList.add(card.getCard());
            }
            return ResponseBean.success(cardList);
        } catch (Exception e) {
            log.error("获取背包卡片列表异常：", e);
            return ResponseBean.fail("getCardList error: " + e.getMessage());
        }
    }

    /**
     * 请求抽卡
     *
     * @param player
     * @param gachaPoolId
     * @param count
     * @return
     */
    @GetMapping("/gachaDraw")
    public ResponseBean<Object> gachaDraw(@CurrentPlayer WebPlayer player, @RequestParam("gachaPoolId") String gachaPoolId, @RequestParam("count") int count) {
        // redisson分布式锁
        boolean publicLock = false;
        try {
            publicLock = redisManager.publicLock(redisManager.getCenterRedisson(), "gachaDraw", TimeUtil.TWO_MILLIS);
            if (publicLock) {
                log.info("玩家请求获得抽卡锁成功，player=" + player.getPlayerId());
                // 获取卡池
                GachaPool gachaPool = mongoTemplate.findOne(new Query(Criteria.where("id").is(gachaPoolId)), GachaPool.class);
                if (gachaPool == null) {
                    log.error("卡池不存在，无法抽卡playerId=" + player.getPlayerId() + " gachaPoolId=" + gachaPoolId);
                    return ResponseBean.fail("gachaPool is not exist");
                }
                // 当前时间
                long currentTime = System.currentTimeMillis();
                // 必须在卡池被关闭的情况下才能修改卡池，防止后台在更新，玩家正在抽卡
                if (currentTime <= gachaPool.getStartTime() || currentTime >= gachaPool.getEndTime()) {
                    log.error("请求抽卡失败，卡池已关闭 playerId=" + player.getPlayerId() + " gachaPoolId=" + gachaPoolId);
                    return ResponseBean.fail("gachaPool is stop!");
                }
                // 计算总权重
                int totalWeight = 0;
                for (int i = 0; i < gachaPool.getWeightList().size(); i++) {
                    totalWeight += gachaPool.getWeightList().get(i).getWeight();
                }
                if (totalWeight == 0) {
                    log.error("卡池总权重为0，无法抽卡playerId=" + player.getPlayerId() + " gachaPoolId=" + gachaPoolId);
                    return ResponseBean.fail("totalWeight is 0");
                }
                // 验证抽卡价格
                if (gachaPool.getCandy() <= 0) {
                    log.error("卡池抽卡价格异常，无法抽卡playerId=" + player.getPlayerId() + " gachaPoolId=" + gachaPoolId + " cost=" + gachaPool.getCandy());
                    return ResponseBean.fail("gachaPool candy is 0");
                }
                // 抽卡返回结果
                JSONObject resultJsonObject = new JSONObject();
                // 记录成功抽取的卡片
                List<GachaCard> gachaCardList = new ArrayList<>();
                // 抽卡
                for (int i = 0; i < count; i++) {
                    // 抽卡消耗(抽一次卡扣一次钱，防止扣完因为其他问题，抽卡失败)
                    List<Integer> costList = List.of(MyEnumResourceId.CANDY.getId(), gachaPool.getCandy());
                    // 扣除抽卡消耗
                    if (!packManager.itemCheckAndReduceByConfigId(player, costList, MyDefineItemChangeReason.LUCKYDRAW)) {
                        log.info("抽卡消耗失败playerId=" + player.getPlayerId() + " gachaPoolId=" + gachaPoolId + " costList=" + costList);
                        return ResponseBean.fail("gachaDraw itemCheckAndReduceByConfigId error playerId=" + player.getPlayerId());
                    }
                    // 开始计算权重奖励
                    int weightRandom = RandomUtils.random(totalWeight);
                    int weightMin = 0;
                    GachaPoolWeight gachaPoolWeight = null;
                    for (int j = 0, len = gachaPool.getWeightList().size(); j < len; j++) {
                        GachaPoolWeight tempGachaPoolWeight = gachaPool.getWeightList().get(j);
                        weightMin += tempGachaPoolWeight.getWeight();
                        if (weightRandom < weightMin) {
                            // 如果都被抽取，跳过
                            for (GachaPoolWeightCardInfo entry : tempGachaPoolWeight.getCardInfoList()) {
                                // 卡片剩余数量
                                if (entry.getNum() > 0) {
                                    gachaPoolWeight = tempGachaPoolWeight;
                                    // 表示还未被抽取完，可以被选中
                                    break;
                                }
                            }
                            if (gachaPoolWeight == null) {
                                continue;
                            }
                            break;
                        }
                    }
                    if (gachaPoolWeight == null) {
                        log.error("卡池权重计算失败1，无法抽卡playerId=" + player.getPlayerId() + " gachaPoolId=" + gachaPoolId);
                        return ResponseBean.fail("gachaPoolWeight is null,playerId=" + player.getPlayerId());
                    }
                    // 从当前选中的权重卡池中，随机获得一个卡片模板组
                    List<GachaPoolWeightCardInfo> cardTemplateIdList = new ArrayList<>();
                    for (GachaPoolWeightCardInfo entry : gachaPoolWeight.getCardInfoList()) {
                        if (entry.getNum() > 0) {
                            cardTemplateIdList.add(entry);
                        }
                    }
                    Collections.shuffle(cardTemplateIdList);
                    GachaPoolWeightCardInfo cardInfo = cardTemplateIdList.get(0);
                    // 更新剩余数量
                    cardInfo.setNum(cardInfo.getNum() - 1);
                    // 查询一个卡片
                    Query query = new Query();
                    query.addCriteria(Criteria.where("gachaCardTemplateId").is(cardInfo.getCardTemplateId()));
                    // 卡片为非销毁状态的卡片才更新绑定卡池
                    query.addCriteria(Criteria.where("burn").is(false));
                    // 排除已被抽走的卡片
                    query.addCriteria(Criteria.where("ownerPlayerId").is(0));
                    // 排除已被其他卡池选中的卡片
                    query.addCriteria(Criteria.where("gachaPoolId").is(gachaPoolId));
                    // 在卡片库中找到此卡片(前面已经随机过模板类型，这里直接获取找到的第一个卡片)
                    GachaCard gachaCard = mongoTemplate.findOne(query, GachaCard.class);
                    if (gachaCard == null) {
                        log.error("卡池中卡片不存在，无法抽卡playerId=" + player.getPlayerId() + " gachaPoolId=" + gachaPoolId + " templateId=" + cardInfo.getCardTemplateId() + " cardInfo=" + JSON.toJSONString(cardInfo));
                        return ResponseBean.fail("gachaCard is not exist");
                    }
                    log.info("抽卡成功playerId=" + player.getPlayerId() + " gachaPoolId=" + gachaPoolId + " gachaCardId=" + gachaCard.getId());
                    // 调用Solana转移NFT
                    SolanaAddTransferQueueTimer solanaAddTransferQueueTimer = new SolanaAddTransferQueueTimer(gachaCard, player.getWalletAddress());
                    threadManager.getThread(threadManager.getSolanaNftThreadName()).addTimerEvent(solanaAddTransferQueueTimer);
                    // 不管是否transfer成功，游戏内部要先修改卡片以及卡池数据
                    log.info("Solana添加转移NFT任务 playerId=" + player.getPlayerId() + " gachaPoolId=" + gachaPoolId + " gachaCardId=" + gachaCard.getId());
                    // 记录已被抽取
                    gachaCard.setOwnerPlayerId(player.getPlayerId());
                    // 记录卡片价格（等于当前卡池抽卡价格）
                    gachaCard.setCost(gachaPool.getCandy());
                    // 记录卡片回收candy比例
                    gachaCard.setBurnCandyRatio(gachaPoolWeight.getBurnCandyRatio());
                    gachaCard.setBurnFtRatio(gachaPoolWeight.getBurnFtRatio());
                    gachaCard.setOwnerPlayerId(player.getPlayerId());
                    // 添加背包
                    packManager.addCard(player, gachaCard, MyDefineItemChangeReason.LUCKYDRAW);
                    // 记录被抽取卡片返回给前端
                    gachaCardList.add(gachaCard);
                    // 在卡片库中保存
                    query = new Query(Criteria.where("id").is(gachaCard.getId()));
                    Update update = new Update();
                    update.set("ownerPlayerId", gachaCard.getOwnerPlayerId());
                    update.set("cost", gachaCard.getCost());
                    update.set("burnCandyRatio", gachaCard.getBurnCandyRatio());
                    update.set("burnFtRatio", gachaCard.getBurnFtRatio());
                    mongoTemplate.upsert(query, update, GachaCard.class);
                    // 记录卡池抽卡次数
                    gachaPool.setDrawCardCount(gachaPool.getDrawCardCount() + 1);
                    // 保存
                    mongoTemplate.save(gachaPool);
                    log.info("保存卡池成功playerId=" + player.getPlayerId() + " gachaPoolId=" + gachaPoolId + " gachaCardId=" + gachaCard.getId());
                }
                // 返回结果
                resultJsonObject.put("gachaCardList", gachaCardList);
                return ResponseBean.success(resultJsonObject);
            } else {
                log.error("请求抽卡繁忙，请稍后再试player=" + player.getPlayerId());
                return ResponseBean.fail("The lottery is currently busy, please try again later!");
            }
        } catch (Exception e) {
            log.error("请求抽卡异常：", e);
            return ResponseBean.fail("gachaDraw error: " + e.getMessage());
        } finally {
            if (publicLock) {
                log.info("玩家请求抽卡完成，释放分布式锁！player=" + player.getPlayerId());
                redisManager.publicUnlock(redisManager.getCenterRedisson(), "gachaDraw");
            }
        }
    }


    /**
     * 请求销毁卡片
     *
     * @param player
     * @param reqData
     * @return
     */
    @PostMapping("/burnCard")
    public ResponseBean<Object> burnCard(@CurrentPlayer WebPlayer player, @RequestBody GachaCardRefundList reqData) {
        try {
            // 验证信息是否正确
            for (int i = 0; i < reqData.getRefundList().size(); i++) {
                GachaCardRefund gachaCardRefund = reqData.getRefundList().get(i);
                if (StringUtil.isEmptyOrNull(gachaCardRefund.getTransactionId())) {
                    log.error("销毁卡片交易id为空,playerId=" + player.getPlayerId());
                    return ResponseBean.fail("burn transactionId is empty,playerId=" + player.getPlayerId());
                }
            }
            for (int i = 0; i < reqData.getRefundList().size(); i++) {
                GachaCardRefund gachaCardRefund = reqData.getRefundList().get(i);
                // 赋值唯一id
                gachaCardRefund.setId(Long.toString(ID.getId()));
                // 赋值玩家id
                gachaCardRefund.setPlayerId(player.getPlayerId());
                // 设置创建事件
                gachaCardRefund.setCreateTime(System.currentTimeMillis());
                SolanaAddBurnQueueTimer solanaAddBurnQueueTimer = new SolanaAddBurnQueueTimer(gachaCardRefund);
                threadManager.getThread(threadManager.getSolanaNftThreadName()).addTimerEvent(solanaAddBurnQueueTimer);
            }
            // 返回结果
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("请求销毁卡片异常：", e);
            return ResponseBean.fail("burnCard error: " + e.getMessage());
        }
    }

    /**
     * 请求销毁卡片（测试使用，不去链上验证）
     *
     * @param player
     * @param cardId
     * @return
     */
    @PostMapping("/burnCardNoVerify")
    public ResponseBean<Object> burnCardNoVerify(@CurrentPlayer WebPlayer player, @RequestBody String cardId) {
        try {
            // burn成功后，删除卡片信息发放奖励
            GachaCard gachaCard = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(cardId)), GachaCard.class);
            // 设置已销毁
            gachaCard.setBurn(true);
            gachaCard.setOwnerPlayerId(0);
            mongoTemplate.save(gachaCard);
            // 移除玩家背包卡片
            packManager.removeCard(player, Long.parseLong(cardId), MyDefineItemChangeReason.SOLANA_BURN);
            // 计算返利
            // candy与美分比例 1:10，即1000candy=1美元
            int addCandy = (int) (gachaCard.getUsd() / GameUtil.bfb * gachaCard.getBurnCandyRatio()) * 10;
            if (addCandy <= 0) {
                log.error("测试：销毁卡片返还资源异常：卡牌销毁比例异常，playerId=" + player.getPlayerId() + " data=" + JSON.toJSONString(gachaCard) + " addCandy=" + addCandy);
                return ResponseBean.fail("burnCard error: candy ratio error");
            }
            // 返利candy
            packManager.addItemByConfigId(player, MyEnumResourceId.CANDY.getId(), addCandy, MyDefineItemChangeReason.SOLANA_BURN);
            log.info("测试：玩家成功销毁卡片返还Candy资源：playerId=" + player.getPlayerId() + " id=" + cardId + " addCandy=" + addCandy);
            // 返回结果
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("请求销毁卡片异常：", e);
            return ResponseBean.fail("burnCard error: " + e.getMessage());
        }
    }

    /**
     * 获取卡片列表
     */
    @GetMapping("/cardList")
    public ResponseBean<Object> cardList(@CurrentPlayer WebPlayer player, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam("quality") String quality, @RequestParam("all") boolean all) {
        try {
            Query query = new Query();
            if (!quality.isEmpty()) {
                query.addCriteria(Criteria.where("quality").is(quality));
            }
            // 如果不是查看所有，就只查询未被抽走的
            if (!all) {
                // 排除已被抽走的卡片
                query.addCriteria(Criteria.where("ownerPlayerId").is(0));
                // 排除未被其他卡池选中的卡片（用于展示可被玩家抽取的卡片）
                query.addCriteria(Criteria.where("gachaPoolId").ne(0));
            }
            // 只查询未被销毁的卡片
            query.addCriteria(Criteria.where("burn").is(false));
            // 分页降序查询
            Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.ASC, "level"));
            // 查询最大条数，用于设置分页
            long max = mongoTemplate.count(query, GachaCard.class);
            query.with(pageable);
            List<GachaCard> list = mongoTemplate.find(query, GachaCard.class);
            JSONObject dataTableJson = new JSONObject();
            dataTableJson.put("list", list);
            dataTableJson.put("total", max);
            return ResponseBean.success(dataTableJson);
        } catch (Exception e) {
            log.error("获取卡片列表异常：", e);
            return ResponseBean.fail("cardList error" + e.getMessage());
        }
    }

}
