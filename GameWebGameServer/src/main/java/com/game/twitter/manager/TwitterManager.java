package com.game.twitter.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.game.config.RestTemplateFactory;
import com.game.data.myenum.MyEnumQuestTargetType;
import com.game.player.structs.WebPlayer;
import com.game.quest.manager.QuestManager;
import com.game.utils.TimeUtil;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.api.UsersApi;
import com.twitter.clientlib.model.Get2UsersIdTweetsResponse;
import com.twitter.clientlib.model.Tweet;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Base64;

/**
 * twitter管理器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/21 14:43
 */
@Component
@Log4j2
public class TwitterManager {

    // 获取twitter信息相关
    private @Value("${server.twitter.apiKey}") String apiKey;
    private @Value("${server.twitter.apiSecretKey}") String apiSecretKey;
    private @Value("${server.twitter.accessToken}") String accessToken;
    private @Value("${server.twitter.accessTokenSecret}") String accessTokenSecret;
    private @Value("${server.twitter.shareUrl}") String shareUrl;
    private @Value("${server.twitter.bearerToken}") String bearerToken;
    // 登录授权相关
    private @Value("${server.twitter.tokenUrl}") String tokenUrl;
    private @Value("${server.twitter.clientId}") String clientId;
    private @Value("${server.twitter.clientSecret}") String clientSecret;
    private @Value("${server.twitter.redirectUri}") String redirectUri;
    @Lazy
    private @Resource QuestManager questManager;
    private @Resource RestTemplateFactory restTemplateFactory;
    // 都tm是twitter的包，那个好用，用那个
//    private Twitter twitter;
    private TwitterApi twitterApi;

    /**
     * 初始化twitter管理器
     */
    @PostConstruct
    public void init() {
        try {
            // 废弃，改为使用twitter-api-java-sdk
//            twitter4j.conf.ConfigurationBuilder cb = new twitter4j.conf.ConfigurationBuilder();
//            cb.setDebugEnabled(true).setOAuthConsumerKey(apiKey).setOAuthConsumerSecret(apiSecretKey).setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);
//            TwitterFactory tf = new TwitterFactory(cb.build());
//            twitter = tf.getInstance();

            TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(bearerToken);
            twitterApi = new TwitterApi(credentials);
        } catch (Exception e) {
            log.error("初始化twitter管理器异常：", e);
        }
    }

//    /**
//     * 验证twitter分享
//     *
//     * @author zhangzhen
//     * @version 1.0
//     * @time 2024/8/21 16:32
//     */
//    public void checkTwitterShare(WebPlayer player) {
//        try {
//            // 当前时间
//            long currentTime = System.currentTimeMillis();
//            // 获取用户最新的推文
//            List<Status> statuses = twitter.getUserTimeline(player.getTwitterUserId(), new Paging(1, 10));
//            log.info("玩家playerId=" + player.getPlayerId() + " 请求获取最近推文！");
//            for (Status status : statuses) {
//                log.info("推文内容：" + status.getText() + " time=" + status.getCreatedAt().getTime());
//                // 判断这个是不是今天的
//                if (!TimeUtil.isSameDay(status.getCreatedAt().getTime(), currentTime)) {
//                    // 不是今天的，跳过
//                    continue;
//                }
//                if (status.getText().contains(shareUrl)) {
//                    log.info("玩家[" + player.getPlayerId() + "]完成在Twitter上分享推文任务！");
//                    questManager.updateQuestTarget(player, MyEnumQuestTargetType.X_SHARE, 1);
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            log.info("验证twitter分享异常：", e);
//        }
//    }

    /**
     * 验证twitter发推文内容@Tsg
     *
     * @author zhangzhen
     * @version 1.0
     * @time 2024/8/21 16:32
     */
    public void checkTwitterPostTagTsg(WebPlayer player) {
        try {
            log.info("玩家playerId=" + player.getPlayerId() + " 请求获取最近推文！");
            // 获取今天凌晨时间作为开始时间
            long startTimeMillis = TimeUtil.getTimeInMillisByHour(System.currentTimeMillis(), 0);
            // 当前时间作为结束时间
            long endTimeMillis = System.currentTimeMillis();
            // 将时间戳转换为 Instant
            Instant startTimeInstant = Instant.ofEpochMilli(startTimeMillis);
            Instant endTimeInstant = Instant.ofEpochMilli(endTimeMillis);
            // 将 Instant 转换为 OffsetDateTime 并指定日本时区
            ZoneId japanZoneId = ZoneId.of("Asia/Tokyo");
            OffsetDateTime startTime = OffsetDateTime.ofInstant(startTimeInstant, japanZoneId);
            OffsetDateTime endTime = OffsetDateTime.ofInstant(endTimeInstant, japanZoneId);
            // 测试获取推文
            Get2UsersIdTweetsResponse get2UsersIdTweetsResponse = twitterApi.tweets().usersIdTweets(player.getTwitterUserId()).startTime(startTime).endTime(endTime).execute();
            if (get2UsersIdTweetsResponse.getErrors() != null && !get2UsersIdTweetsResponse.getErrors().isEmpty()) {
                for (int i = 0; i < get2UsersIdTweetsResponse.getErrors().size(); i++) {
                    log.info("获取用户推文错误：" + get2UsersIdTweetsResponse.getErrors().get(i).getTitle() + " " + get2UsersIdTweetsResponse.getErrors().get(i).getDetail());
                }
            }
            if (get2UsersIdTweetsResponse.getData() != null) {
                log.info("获取用户今日的推文条数：" + get2UsersIdTweetsResponse.getData().size());
                boolean isComplete = false;
                for (Tweet tweet : get2UsersIdTweetsResponse.getData()) {
                    if (tweet.getText().contains("@TokyoStupidGame")) {
                        log.info("玩家[" + player.getPlayerId() + "]完成在Twitter上发推文内容@Tsg任务！");
                        questManager.updateQuestTarget(player, MyEnumQuestTargetType.X_POST_TAG_TSG, 1);
                        isComplete = true;
                        break;
                    }
                }
                if (!isComplete) {
                    log.info("玩家[" + player.getPlayerId() + "]今日没有发推文内容@TokyoStupidGame，无法完成任务！");
                }
            } else {
                log.info("玩家[" + player.getPlayerId() + "]今日没有推文，无法完成任务！");
            }
        } catch (Exception e) {
            log.info("验证twitter分享异常：", e);
        }
    }

    /**
     * 检查用户是否关注了指定的Twitter账户
     *
     * @param player
     */
    public void isUserFollowing(WebPlayer player) {
        try {
            // Replace with user ID below
//            String response = getFollowing(player.getTwitterUserId());
            UsersApi.APIusersIdFollowingRequest apIusersIdFollowingRequest = twitterApi.users().usersIdFollowing(player.getTwitterUserId());
//            UsersIdLikedTweetsResponse result = twitterApi.tweets().usersIdLikedTweets(player.getTwitterUserId(), null, null, null, null, null, null, null, null);
//            GenericTweetsTimelineResponse result = twitterApi.tweets().usersIdTweets(player.getTwitterUserId(), null, null, null, null, null, null, null, null, null, null, null, null, null);
            log.info("获取玩家player=" + player.getPlayerId() + " twitterId=" + player.getTwitterUserId() + " 的绑定twitter用户关注列表信息：" + apIusersIdFollowingRequest);
//            // 使用Twitter API v1.1检查用户是否关注了指定的账户
//            String playerTwitterName = getUserScreenNameById(player);
//            String tsgTwitterName = getCurrentUserScreenName();
//            // 源用户的屏幕名称 目标用户的屏幕名称(TSG官方账户)
//            log.info("twitter关注任务：检查用户：" + playerTwitterName + " 是否关注了TSG官方用户：" + tsgTwitterName);
//            Relationship relationship = twitter.showFriendship(playerTwitterName, tsgTwitterName);
//            if (relationship.isSourceFollowingTarget()) {
//                log.info("玩家[" + player.getPlayerId() + "]完成在Twitter上关注TSG官方账户任务！");
//                questManager.updateQuestTarget(player, MyEnumQuestTargetType.X_FOLLOW, 1);
//            } else {
//                log.info("玩家[" + player.getPlayerId() + "]未完成在Twitter上关注TSG官方账户任务！");
//            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("检查用户是否关注异常：", e);
        }
    }

    /*
     * This method calls the v2 followers lookup endpoint by user ID
     * */
//    public String getFollowing(String userId) {
//        try {
//            // 构建请求URL
//            String url = "https://api.x.com/2/users/" + userId + "/following";
//            // 构建请求头
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", "Bearer " + accessToken);
//            headers.set("Content-Type", "application/json");
//            // 构建请求体
//            Map<String, Object> requestBody = Map.of("user.fields", "id,name,username");
//            // 将请求体转换为HttpEntity
//            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
//            // 发送POST请求
//            ResponseEntity<String> response = restTemplateFactory.getRestTemplate().exchange(url, HttpMethod.POST, requestEntity, String.class);
//            // 处理响应
//            String tweetResponse = response.getBody();
//            return tweetResponse;
//        } catch (Exception e) {
//            log.error("异常：", e);
//            return "";
//        }
//    }

//    private String getFollowing(String userId) {
//        try {
//            String tweetResponse = null;
//
//            org.apache.http.client.HttpClient httpClient = org.apache.http.impl.client.HttpClients.custom()
//                    .setDefaultRequestConfig(org.apache.http.client.config.RequestConfig.custom()
//                            .setCookieSpec(CookieSpecs.STANDARD).build())
//                    .build();
//
//            org.apache.http.client.utils.URIBuilder uriBuilder = new org.apache.http.client.utils.URIBuilder(String.format("https://api.twitter.com/2/users/%s/following", userId));
//            ArrayList<org.apache.http.NameValuePair> queryParameters;
//            queryParameters = new ArrayList<>();
//            queryParameters.add(new org.apache.http.message.BasicNameValuePair("user.fields", "created_at"));
//            uriBuilder.addParameters(queryParameters);
//
//            org.apache.http.client.methods.HttpGet httpGet = new org.apache.http.client.methods.HttpGet(uriBuilder.build());
//            httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
//            httpGet.setHeader("Content-Type", "application/json");
//
//            org.apache.http.HttpResponse response = httpClient.execute(httpGet);
//            org.apache.http.HttpEntity entity = response.getEntity();
//            if (null != entity) {
//                tweetResponse = org.apache.http.util.EntityUtils.toString(entity, "UTF-8");
//            }
//            return tweetResponse;
//        } catch (Exception e) {
//            log.error("异常：", e);
//            return "";
//        }
//    }

    /**
     * 检查用户的名字是否增加了TSG后缀
     *
     * @param player
     */
//    public void chekUserName(WebPlayer player) {
//        try {
//            // 使用Twitter API v1.1检查用户是否关注了指定的账户
//            // 源用户的屏幕名称 目标用户的屏幕名称(TSG官方账户)
//            String userName = getUserNameById(player);
//            if (userName.toLowerCase().endsWith("tsg")) {
//                log.info("玩家[" + player.getPlayerId() + "]完成在Twitter上修改名字任务！");
//                questManager.updateQuestTarget(player, MyEnumQuestTargetType.X_UPDATE_NAME, 1);
//            }
//        } catch (Exception e) {
//            log.error("检查用户的名字是否增加了TSG后缀异常：", e);
//        }
//    }

    /**
     * 比较用户的横幅图片 URL 是否与 TSG 的横幅图片 URL 相同
     *
     * @param player
     */
//    public void checkUserBanner(WebPlayer player) {
//        try {
//            // 获取系统用户图片URL
//            String tsgBannerUrl = getCurrentUserBanner();
//            // 获取用户的横幅图片URL
//            String userBannerUrl = getUserBannerUrlById(player);
//            log.info("玩家[" + player.getPlayerId() + "]系统用户图片URL：" + tsgBannerUrl + " 玩家的横幅图片URL：" + userBannerUrl);
//            // 比较横幅图片URL
//            if (tsgBannerUrl.equalsIgnoreCase(userBannerUrl)) {
//                log.info("玩家[" + player.getPlayerId() + "]完成在Twitter上修改横幅任务！");
//                questManager.updateQuestTarget(player, MyEnumQuestTargetType.X_UPDATE_BANNER, 1);
//            }
//        } catch (Exception e) {
//            log.error("比较用户的横幅图片 URL 是否与 TSG 的横幅图片 URL 相同异常：", e);
//        }
//    }

    /**
     * 获取当前认证用户的屏幕名称
     *
     * @return 当前认证用户的屏幕名称
     */
//    public String getCurrentUserScreenName() {
//        try {
//            User user = twitter.verifyCredentials();
//            return user.getScreenName();
//        } catch (TwitterException e) {
//            log.error("获取当前认证用户的屏幕名称异常：", e);
//            return null;
//        }
//    }

    /**
     * 根据用户 ID 获取用户的屏幕名称
     *
     * @param player
     * @return
     */
//    public String getUserScreenNameById(WebPlayer player) {
//        try {
//            User user = twitter.showUser(player.getTwitterUserId());
//            return user.getScreenName();
//        } catch (TwitterException e) {
//            log.error("根据用户 ID 获取屏幕名称异常：", e);
//            return null;
//        }
//    }

    /**
     * 根据用户 ID 获取用户的名字
     *
     * @param player
     * @return
     */
//    public String getUserNameById(WebPlayer player) {
//        try {
//            User user = twitter.showUser(player.getTwitterUserId());
//            return user.getName();
//        } catch (TwitterException e) {
//            log.error("根据用户 ID 获取屏幕名称异常：", e);
//            return null;
//        }
//    }

    /**
     * 获取当前认证用户的横幅
     *
     * @return 当前认证用户的屏幕名称
     */
//    public String getCurrentUserBanner() {
//        try {
//            User user = twitter.verifyCredentials();
//            return user.getProfileBannerURL();
//        } catch (TwitterException e) {
//            log.error("获取当前认证用户的屏幕名称异常：", e);
//            return null;
//        }
//    }

    /**
     * 根据用户 ID 获取用户的横幅图片 URL
     *
     * @param player WebPlayer 对象，包含 Twitter 用户 ID
     * @return 用户的横幅图片 URL 或 null
     */
//    public String getUserBannerUrlById(WebPlayer player) {
//        try {
//            User user = twitter.showUser(player.getTwitterUserId());
//            return user.getProfileBannerURL();
//        } catch (TwitterException e) {
//            log.error("根据用户 ID 获取横幅图片 URL 异常：", e);
//            return null;
//        }
//    }


    /**
     * 获取twitter登录授权token
     *
     * @param code
     * @param code_verifier
     * @return
     */
    public String getAccessToken(String code, String code_verifier) {
        try {
            // 商户id和商户的私钥
            String credentials = clientId + ":" + clientSecret;
            // 对商户id和私钥进行Base64编码
            String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
//            log.info("base64Credentials=" + base64Credentials);
            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Authorization", "Basic " + base64Credentials);
            // 构建请求体
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "authorization_code");
            body.add("code", code);
            body.add("redirect_uri", redirectUri);
            body.add("client_id", clientId);
            body.add("client_secret", clientSecret);
            body.add("code_verifier", code_verifier);
            // 创建 HttpEntity
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
            // 发送 POST 请求
            ResponseEntity<String> responseEntity = restTemplateFactory.getRestTemplate().postForEntity(tokenUrl, requestEntity, String.class);
            // 处理响应
            if (responseEntity.getStatusCodeValue() == 200) {
                String jsonResponse = responseEntity.getBody();
                JSONObject json = JSON.parseObject(jsonResponse);
                return json.getString("access_token");
            } else {
                log.error("获取twitter登录授权token失败：" + responseEntity.getBody());
                return null;
            }
        } catch (Exception e) {
            log.error("获取twitter登录授权token异常：", e);
            return null;
        }
    }

    /**
     * 获取twitter用户信息
     *
     * @param accessToken
     * @return
     */
    public String callTwitterApi(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            HttpEntity<String> request = new HttpEntity<>(headers);
            // 参考文档：https://developer.x.com/en/docs/x-api/users/lookup/api-reference/get-users-me#tab3
            ResponseEntity<String> response = restTemplateFactory.getRestTemplate().exchange("https://api.x.com/2/users/me", HttpMethod.GET, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            log.error("获取twitter用户信息异常：", e);
            return null;
        }
    }

}
