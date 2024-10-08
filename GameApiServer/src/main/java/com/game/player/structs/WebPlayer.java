package com.game.player.structs;

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
@Document(collection = "player")
@Getter
@Setter
public class WebPlayer {


    /******************************** 单个属性 ********************************/
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
     * 用户名
     */
    private String userName = "";
    /**
     * 平台账户唯一id
     */
    private String account = "";
    /**
     * 角色名称
     */
    private String playerName = "";
    /**
     * 网页展示的一个名字，先放这里，前端自己设置
     */
    private String webUserName = "";
    /**
     * 头像下载地址
     */
    private String avatarUrl = "";
    /**
     * 头像存储路径
     */
    private String avatarPath = "";
    /**
     * 密码
     */
    private String password = "";
    /**
     * 绑定钱包地址
     */
    private String walletAddress = "";
    /**
     * 邮箱
     */
    private String email = "";
    /**
     * 快递收货地址
     */
    private String address = "";
    /**
     * 推特绑定账号
     */
    private String twitterUserId = "";
    /**
     * 推特绑定账号数据
     */
    private String twitterUserName = "";
    /**
     * telegram绑定账号
     */
    private String telegramUserId = "";
    /**
     * telegram绑定账号数据
     */
    private String telegramUserName = "";
    /**
     * discord绑定账号id
     */
    private String discordUserId = "";
    /**
     * discord绑定账号数据
     */
    private String discordUserName = "";
    /**
     * 玩家资源锁
     */
    @Transient
    private transient Object resourceLock = new Object();
    /**
     * ip地址
     */
    @Transient
    private transient String ip;
    /**
     * 玩家累计登录天数
     */
    private int loginDay;
    /**
     * 玩家连续登陆天数，在线跨天不计数
     */
    private int loginDayCon;
    /**
     * 玩家版本号，从0开始，按顺序兼容代码
     */
    private int ver;
    /**
     * 玩家创建天数（登录时计算，不需要保存）
     */
    @Transient
    private transient int createDay;
    /**
     * 禁止登录结束时间(结束时间戳)
     */
    private long bannedTime;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 登陆时间
     */
    private long loginTime;
    /**
     * 每日刷新的时间
     */
    private long everydayRefreshTime = 0;
    /**
     * 上一次每日四点刷新的时间
     */
    private long everydaySiDianRefreshTime = 0;
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

    /******************************** 对象属性 ********************************/

    /******************************** 集合属性 ********************************/


    /******************************** 临时属性 ********************************/


}
