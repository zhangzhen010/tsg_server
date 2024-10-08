package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家简易数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 18:53
 */
@Getter
@Setter
@Document(collection = "simplePlayer")
public class SimplePlayer {

    /**
     * 角色id
     */
    @Id
    private Long playerId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 服务器id
     */
    private int serverId;
    /**
     * 玩家当前所在的服务器id
     */
    private int currentServerId;
    /**
     * 角色名称
     */
    private String playerName = "";
    /**
     * 头像
     */
    private String head;
    /**
     * 形象皮肤时装(role配置表id)
     */
    private int skinId;
    /**
     * 角色等级
     */
    private int lv;
    /**
     * vip等级
     */
    private int vipLv;
    /**
     * 总充值金额(单位：分)
     */
    private long sumMoney;
    /**
     * 战力
     */
    private long force;
    /**
     * 平台id(pfId,平台后台自定义配置)
     */
    private int pfId;
    /**
     * sdkId（服务器定义平台id）
     */
    private int sdkId;
    /**
     * 当前客户端语言类型
     */
    private int language;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 登陆时间
     */
    private long loginTime;
    /**
     * 角色下线的时间
     */
    private long loginOutTime;
    /**
     * 禁止登录结束时间(结束时间戳)
     */
    private long loginBannedTime;
    /**
     * 属性列表
     */
    private List<Long> attBaseList = new ArrayList<>();
    /**
     * 上次保存时间（仅用于服务器开启期间保存定时保存时验证）
     */
    @Transient
    private transient long lastSaveTime;

}
