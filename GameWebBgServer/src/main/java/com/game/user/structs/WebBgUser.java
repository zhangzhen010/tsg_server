package com.game.user.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * web玩家数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 11:10
 */
@Document(collection = "webBgUser")
@Getter
@Setter
public class WebBgUser {

    /**
     * 用户唯一id
     */
    @Id
    private Long userId;
    /**
     * 用户名
     */
    private String userName = "";
    /**
     * 密码
     */
    private String password = "";
    /**
     * 盐加密
     */
    private String salt = "";
    /**
     * 每秒请求次数
     */
    @Transient
    private transient int reqCount;
    /**
     * 上一次请求消息整秒的时间戳
     */
    @Transient
    private transient long lastReqTime;

}
