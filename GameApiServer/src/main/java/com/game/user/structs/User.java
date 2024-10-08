package com.game.user.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/5 20:25
 */
@Getter
@Setter
@Document(collection = "user")
public class User {

    /**
     * 用户id
     */
    @Id
    private long userId;
    /**
     * 用户名
     * 根据注解创建索引，需要在类上加@Document(collection = "user")才会生效
     */
    @Indexed(unique = true)
    private String userName;
    /**
     * 用户密码
     */
    private String userPass;
    /**
     * sdkId
     */
    private int sdkId;
    /**
     * pfId
     */
    private int pfId;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 是否禁止登录 0否1是
     */
    private int isLogin;
    /**
     * 上次登录服务器id
     */
    private int lastLoginServerId;
    /**
     * 设备号 数据类型 字符串(内测版本需要，一个账号只能在一个设备上登录)
     */
    private String deviceId = "";
    /**
     * 角色编号map key=serverId value=UserPlayerData
     */
    private Map<Integer, UserPlayerData> playerMap = new HashMap<>();

}
