package com.game.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义配置类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/14 21:16
 */
@Component
@ConfigurationProperties(prefix = "orange-web")
public class OrangeWebConfig {

    /**
     * 上传路径
     */
    private static String profile;
    /**
     * tsg项目上传资源
     */
    private static String tsg;

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        OrangeWebConfig.profile = profile;
    }

    public static String getTsg() {
        return tsg;
    }

    public void setTsg(String tsg) {
        OrangeWebConfig.tsg = tsg;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "avatar";
    }

    /**
     * 获取卡池上传路径
     */
    public static String getGachaPath() {
        return getTsg() + "gacha";
    }

}
