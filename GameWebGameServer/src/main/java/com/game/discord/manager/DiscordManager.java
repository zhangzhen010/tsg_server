package com.game.discord.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.game.config.RestTemplateFactory;
import com.game.data.myenum.MyEnumQuestTargetType;
import com.game.discord.listener.DiscordListener;
import com.game.player.manager.PlayerManager;
import com.game.player.structs.WebPlayer;
import com.game.quest.manager.QuestManager;
import com.game.utils.StringUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Discord管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/19 13:49
 */
@Component
@Log4j2
public class DiscordManager {

    private @Value("${server.discord.token}") String token;
    private @Resource DiscordListener discordListener;
    private @Resource QuestManager questManager;
    private @Resource MongoTemplate mongoTemplate;
    private @Resource PlayerManager playerManager;
    private @Value("${server.discord.clientId}") String clientId;
    private @Value("${server.discord.clientSecret}") String clientSecret;
    private @Value("${server.discord.accessTokenUrl}") String accessTokenUrl;
    private @Value("${server.discord.redirectUri}") String redirectUri;
    private @Value("${server.discord.userInfoUrl}") String userInfoUrl;

    private @Resource RestTemplateFactory restTemplateFactory;

    /**
     * JDA实例
     */
    private JDA jda;

    /**
     * discord初始化
     */
    @PostConstruct
    public void init() {
        try {
            this.jda = JDABuilder.createDefault(token).enableIntents(Arrays.asList(GatewayIntent.values())).addEventListeners(discordListener).build().awaitReady();
            // 打印服务器信息
            List<Guild> guilds = jda.getGuilds();
            for (int i = 0; i < guilds.size(); i++) {
                Guild guild = guilds.get(i);
                log.info("Discord当前服务器：id=[" + guild.getId() + "] name=[" + guild.getName() + "]");
            }
        } catch (Exception e) {
            log.error("discord初始化异常：", e);
        }
    }

    /**
     * 获取用户在当前服务器中的身分组列表
     *
     * @param player
     * @return
     */
    public List<Role> getUserRolesInCurrentGuild(WebPlayer player) {
        try {
            // 一个机器人目前只会加入到一个discord服务器
            net.dv8tion.jda.api.entities.Guild currentGuild = jda.getGuilds().get(0);
            Member member = currentGuild.retrieveMemberById(player.getDiscordUserId()).complete();
            return member.getRoles();
        } catch (Exception e) {
            log.error("获取用户在当前服务器中的身分组列表异常：", e);
            return Collections.emptyList();
        }
    }

    /**
     * 检查用户是否在当前服务器中拥有指定身分组
     *
     * @param player
     * @param roleName
     * @return
     */
    public boolean checkUserRoleInCurrentGuild(WebPlayer player, String roleName) {
        try {
            if (player.getDiscordUserId().isEmpty()) {
                return false;
            }
            if (StringUtil.isEmptyOrNull(roleName)) {
                return false;
            }
            // 一个机器人目前只会加入到一个discord服务器
            net.dv8tion.jda.api.entities.Guild currentGuild = jda.getGuilds().get(0);
            Member member = currentGuild.retrieveMemberById(player.getDiscordUserId()).complete();
            List<Role> roles = member.getRoles();
            log.info("任务：discord领取任务player=" + player.getPlayerId() + " 需要身份组：" + roleName);
            for (int i = 0; i < roles.size(); i++) {
                Role role = roles.get(i);
                log.info("任务：discord用户当前身份组：" + role.getName());
                if (role.getName().equalsIgnoreCase(roleName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("获取用户在当前服务器中的身分组列表异常：", e);
            return false;
        }
    }

    /**
     * 移除用户在当前服务器中的指定身分组
     *
     * @param userId   用户ID
     * @param roleName 身分组名字
     */
    public void removeRoleFromUserInCurrentGuild(WebPlayer player, String userId, String roleName) {
        try {
            // 一个机器人目前只会加入到一个discord服务器
            net.dv8tion.jda.api.entities.Guild currentGuild = jda.getGuilds().get(0);
            Member member = currentGuild.retrieveMemberById(userId).complete();
            List<Role> removeRoleList = currentGuild.getRolesByName(roleName, true);
            // 获取用户当前的角色列表
            List<Role> oldRoleList = member.getRoles();
            // 创建一个新的可变列表
            List<Role> newRoles = new ArrayList<>(oldRoleList.size());
            // 遍历原始角色列表并添加到新列表中，除了要移除的角色
            for (int i = 0; i < oldRoleList.size(); i++) {
                Role oldRole = oldRoleList.get(i);
                boolean isRemove = false;
                for (int j = 0; j < removeRoleList.size(); j++) {
                    Role removeRole = removeRoleList.get(j);
                    if (oldRole.getId().equalsIgnoreCase(removeRole.getId())) {
                        isRemove = true;
                        log.info("移除用户在当前服务器中的指定身分组，playerId=[" + player.getPlayerId() + "] 组=" + removeRole.getName());
                        break;
                    }
                }
                if (!isRemove) {
                    newRoles.add(oldRole);
                }
            }
            // 执行移除角色的操作
            if (!newRoles.isEmpty()) {
                currentGuild.modifyMemberRoles(member, newRoles).queue((Consumer<Void>) v -> {
                    log.info("移除用户在当前服务器中的指定身分组成功！player=" + player.getPlayerId());
                }, (Consumer<Throwable>) error -> {
                    log.error("移除用户在当前服务器中的指定身分组失败：", error);
                });
            } else {
                log.info("移除用户在当前服务器中的指定身分组失败，因为没有找到要移除的身分组！player=" + player.getPlayerId());
            }
        } catch (Exception e) {
            log.error("移除用户在当前服务器中的指定身分组异常：", e);
        }
    }

    /**
     * discord消息接收
     *
     * @param event
     */
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        try {
            // 获取消息内容
            Message message = event.getMessage();
            String content = message.getContentRaw();
            log.info("discord收到聊天频道信息：channelName=" + message.getChannel().getName() + " message=" + content);
            // 检查消息是否为 "GM"(每日任务：在我们的 Discord 频道里说早上好)(改为说任何话都可以)
            if (!content.isEmpty()) {
                // 收到指定消息，执行奖励逻辑，获取discord用户信息(如果消息是在一个私聊频道中发送的，那么你需要使用 event.getAuthor() 而不是 event.getMember()，因为私聊中没有成员对象)
                String discordUserId = event.getMember().getUser().getId();
                // 获取玩家信息(有可能多个钱包用户绑定了一个discord账户，他们的任务都算完成)
                List<WebPlayer> playerList = mongoTemplate.find(Query.query(Criteria.where("discordUserId").is(discordUserId)), WebPlayer.class);
                for (WebPlayer webPlayer : playerList) {
                    log.info("discord消息接收成功，玩家信息playerId=" + webPlayer.getPlayerId() + ",discordUserId=" + discordUserId + " message:" + content);
                    // 完成任务
                    questManager.updateQuestTarget(webPlayer, MyEnumQuestTargetType.DISCORD_CHAT, 1);
                    playerManager.savePlayer(webPlayer);
                }
            }
        } catch (Exception e) {
            log.error("discord消息接收异常：", e);
        }
    }

    /**
     * 获取Discord用户的Access Token
     *
     * @param code
     * @return
     */
    public String getAccessToken(String code) {
        try {
            // 构建请求参数
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("grant_type", "authorization_code");
            params.add("code", code);
            params.add("redirect_uri", redirectUri);
            params.add("scope", "identify");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplateFactory.getRestTemplate().postForEntity(accessTokenUrl, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                JSONObject jsonObject = JSON.parseObject(responseBody);
                return jsonObject.getString("access_token");
            } else {
                log.error("获取Discord用户的Access Token异常：" + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            log.error("获取Discord用户的Access Token异常：", e);
            return null;
        }
    }

    /**
     * 获取Discord用户数据
     *
     * @param accessToken
     * @return
     */
    public JSONObject getUserId(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplateFactory.getRestTemplate().exchange(userInfoUrl, HttpMethod.GET, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                // 解析响应体中的用户ID
                return JSON.parseObject(responseBody);
            } else {
                log.error("获取Discord用户数据异常：" + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            log.error("获取Discord用户数据异常：", e);
            return null;
        }
    }

}
