package com.game.login.controller;

import com.alibaba.fastjson2.JSONObject;
import com.game.cache.impl.CodeLinkedHashMap;
import com.game.controller.structs.ResponseBean;
import com.game.data.myenum.MyEnumResourceId;
import com.game.login.structs.ReqLoginData;
import com.game.player.manager.PlayerManager;
import com.game.player.manager.PlayerOtherManager;
import com.game.player.structs.PlayerPack;
import com.game.player.structs.WebPlayer;
import com.game.utils.EMailAwsUtils;
import com.game.utils.GoogleAuthorUtil;
import com.game.utils.JwtUtil;
import com.game.utils.RandomUtils;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.p2p.solanaj.core.Account;
import org.sol4k.Base58;
import org.sol4k.PublicKey;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 9:29
 */
@RestController
@Log4j2
@RequestMapping(value = "/tsg/login")
public class LoginController {

    private @Resource MongoTemplate mongoTemplate;
    private @Resource PlayerManager playerManager;
    private @Resource PlayerOtherManager playerOtherManager;
    private @Resource GoogleAuthorUtil googleAuthorUtil;

    /**
     * 存放电子邮件登录验证map key=邮件地址 value=code
     */
    private CodeLinkedHashMap codeLinkedHashMap = new CodeLinkedHashMap(5000);

    /**
     * 登录成功进入游戏
     *
     * @param player
     * @return
     */
    private ResponseBean<Object> enterGame(WebPlayer player) {
        try {
            // 进入游戏
            playerManager.enterGame(player);
            // 生成token
            String jwtToken = JwtUtil.getJwtToken(player.getPlayerId(), player.getUserName());
            Map<String, Object> resData = new HashMap<>();
            resData.put("token", jwtToken);
            resData.put("account", player.getUserName());
            resData.put("playerId", player.getPlayerId());
            resData.put("playerName", player.getPlayerName());
            resData.put("userName", player.getWebUserName());
            resData.put("avatarUrl", player.getAvatarUrl());
            PlayerPack playerPack = playerOtherManager.getPlayerPack(player, true);
            resData.put("candy", playerPack.getResourceMap().get(MyEnumResourceId.CANDY.getId()));
            resData.put("discordUserId", player.getDiscordUserId());
            resData.put("discordUserName", player.getDiscordUserName());
            resData.put("twitterUserId", player.getTwitterUserId());
            resData.put("twitterUserName", player.getTwitterUserName());
            resData.put("telegramUserId", player.getTelegramUserId());
            resData.put("telegramUserName", player.getTelegramUserName());
            resData.put("walletAddress", player.getWalletAddress());
            return ResponseBean.success(resData);
        } catch (Exception e) {
            log.error("登录成功进入游戏异常：", e);
            return ResponseBean.fail("login error: ".concat(e.getMessage()));
        }
    }

    /**
     * 钱包登录
     *
     * @param reqData
     * @return
     */
    @GetMapping("/qb")
    public ResponseBean<Object> qbLogin(ReqLoginData reqData) {
        try {
            //判断输入账号
            if (ObjectUtils.isEmpty(reqData.getAccount())) {
                log.error("钱包登录账号为空！");
                return ResponseBean.fail("Account or password incorrect!");
            }
            // 钱包登录验证
            String message = reqData.getSignContent();
            byte[] messageBytes = message.getBytes();
            PublicKey publicKey = new PublicKey(reqData.getAccount());
            byte[] signatureBytes = Base58.decode(reqData.getPwd());
            if (!publicKey.verify(signatureBytes, messageBytes)) {
                log.error("钱包登录验证失败！account=" + reqData.getAccount());
                return ResponseBean.fail("Account or password incorrect!");
            }
            // 根据钱包地址查询用户信息
            WebPlayer player = mongoTemplate.findOne(Query.query(Criteria.where("userName").is(reqData.getAccount())), WebPlayer.class);
            //如果不存在且为玩家登录则新增一条用户信息
            if (player == null) {
                //新增用户信息
                player = playerManager.createPlayer(reqData.getAccount(), reqData.getAccount(), "");
            }
            return enterGame(player);
        } catch (Exception e) {
            log.error("钱包登录异常：", e);
            return ResponseBean.fail("qbLogin error: ".concat(e.getMessage()));
        }
    }

    /**
     * google登录
     *
     * @param code
     * @return
     */
    @GetMapping("/googleLogin")
    public ResponseBean<Object> googleLogin(@RequestParam("code") String code) {
        try {
            JSONObject resultData = googleAuthorUtil.googleLoginVerify(code);
            if (ObjectUtils.isEmpty(code)) {
                return ResponseBean.fail("googleLogin error: code is null");
            }
            // google邮箱
            String email = resultData.getString("email");
            // 根据钱包地址查询用户信息
            WebPlayer player = mongoTemplate.findOne(Query.query(Criteria.where("userName").is(email)), WebPlayer.class);
            //如果不存在且为玩家登录则新增一条用户信息
            if (player == null) {
                //新增用户信息
                Account account = new Account();
                //新增用户信息
                player = playerManager.createPlayer(email, account.getPublicKeyBase58(), email);
                player.setWalletPrivateKey(account.getPrivateKeyBase58());
                // google用户信息设置给新玩家
                player.setAvatarUrl(resultData.getString("picture"));
                player.setPlayerName(resultData.getString("name"));
                playerManager.savePlayer(player);
            }
            // 进入游戏
            return enterGame(player);
        } catch (Exception e) {
            log.error("google登录异常：", e);
            return ResponseBean.fail("googleLogin error: ".concat(e.getMessage()));
        }
    }

    /**
     * 邮件登录
     *
     * @param email
     * @param code
     * @return
     */
    @GetMapping("/emailLogin")
    public ResponseBean<Object> emailLogin(@RequestParam("email") String email, @RequestParam("code") int code) {
        try {
            if (code <= 0) {
                Map<String, Object> resData = new HashMap<>();
                // 生成随机验证code
                int newCode = RandomUtils.random(100000, 999999);
                codeLinkedHashMap.put(email, newCode);
                // 发送aws邮件
                EMailAwsUtils.sendMail(email, newCode);
                // 返回前端验证码
                resData.put("emailCode", newCode);
                // 没有code表示是创建一个账户请求，发送aws邮件验证码
                return ResponseBean.success(resData);
            } else {
                // 验证code创建玩家数据
                if (codeLinkedHashMap.get(email) != code) {
                    return ResponseBean.fail("email code error!");
                }
                // 移除验证码
                codeLinkedHashMap.remove(email);
                // 根据钱包地址查询用户信息
                WebPlayer player = mongoTemplate.findOne(Query.query(Criteria.where("userName").is(email)), WebPlayer.class);
                //如果不存在且为玩家登录则新增一条用户信息
                if (player == null) {
                    Account account = new Account();
                    //新增用户信息
                    player = playerManager.createPlayer(email, account.getPublicKeyBase58(), email);
                    player.setWalletPrivateKey(account.getPrivateKeyBase58());
                    // google用户信息设置给新玩家
                    playerManager.savePlayer(player);
                }
                return enterGame(player);
            }
        } catch (Exception e) {
            log.error("google登录异常：", e);
            return ResponseBean.fail("googleLogin error: ".concat(e.getMessage()));
        }
    }

}
