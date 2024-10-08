package com.game.player.structs;

import com.game.bean.proto.BeanProto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 玩家
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/6 11:03
 */
@Document(collection = "player")
@Getter
@Setter
public class Player {
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
     * 头像
     */
    private String head = "";
    /**
     * 形象皮肤时装(role配置表id)
     */
    private int skinId;
    /**
     * 性别 0女 1男
     */
    private int gender;
    /**
     * 玩家等级
     */
    private int lv;
    /**
     * vip等级
     */
    private int vipLv;
    /**
     * 玩家经验
     */
    private long exp;
    /**
     * 当前所在处理线
     */
    @Transient
    private transient int execId;
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
     * 创角时的客户端id（自定义sdkId）
     */
    private int sdkId;
    /**
     * 当前登录的客户端id（自定义sdkId）
     */
    @Transient
    private transient int currentSdkId;
    /**
     * 平台id(pfId,平台后台自定义配置)
     */
    private int pfId;
    /**
     * 当前登录的平台id(pfId,平台后台自定义配置)
     */
    @Transient
    private transient int currentPfId;
    /**
     * 设备号
     */
    @Transient
    private transient String deviceId;
    /**
     * 创建角色时所在服务器id
     */
    private int createServerId;
    /**
     * TODO 世界服 玩家当前所在的服务器id（在世界服类型的游戏中，主要使用该值替代createServerId）
     */
    private int currentServerId;
    /**
     * 玩家当前所在房间服务器id
     */
    private int currentRoomServerId;
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
     * 当前客户端语言类型（登录都会发送，不需要保存）
     */
    @Transient
    private transient int language;
    /**
     * 0=正常登录 1=断线重连
     */
    @Transient
    private transient BeanProto.E_LOGIN_REQ_TYPE loginType;
    /**
     * 上一次定时timer保存时间，除开离线，在线定时保存间隔不能少于1分钟
     */
    @Transient
    private transient long lastTimerSaveTime;
    /**
     * 新手引导id
     */
    private int guideId;
    /**
     * 新手引导小节点id
     */
    private int guideNodeId;
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
     * 角色下线的时间
     */
    private long loginOutTime;
    /**
     * 角色创角以来总计在线时间
     */
    private long onlineTime;
    /**
     * 每日刷新的时间
     */
    private long everydayRefreshTime = 0;
    /**
     * 上一次每日四点刷新的时间
     */
    private long everydaySiDianRefreshTime = 0;
    /**
     * 玩家战力
     */
    private long force;
    /**
     * 是否已经退出（一个玩家退出逻辑只会执行一次，直到下一次登录重置）
     */
    @Transient
    private transient boolean quit;
    /**
     * 玩家离线原因，用于日志说明
     */
    @Transient
    private transient String quitMsg = "";
    /**
     * 保存数据
     */
    @Transient
    private transient byte[] saveData;

    /******************************** 对象属性 ********************************/
    /**
     * 日志参数
     */
    @Transient
    private transient PlayerLogParam logParam = new PlayerLogParam();
    /**
     * 客户端设置信息
     */
    private PlayerSettingData settingData = new PlayerSettingData();
    /**
     * 初始化pb协议
     */
    @Transient
    private transient PlayerProtobuf protoBuf = new PlayerProtobuf();
    /**
     * 玩家临时处理数据
     */
    @Transient
    private transient PlayerTempData tempData = new PlayerTempData();
    /**
     * 熔炉数据
     */
    private SmelterData smelterData = new SmelterData();
    /**
     * 潜能数据
     */
    private PotentialData potentialData = new PotentialData();

    /******************************** 集合属性 ********************************/
    /**
     * 已开启功能的列表(配置表id，也是功能类型枚举type)
     */
    private List<Integer> functionOpenList = new ArrayList<>();
    /**
     * 图鉴列表，配置id,状态（0=已解锁，1=已领取）,配置id...
     */
    private List<Integer> bookIdList = new ArrayList<>();
    /**
     * 兑换数据，兑换配置id，已兑换次数,兑换配置id...
     */
    private List<Integer> exchangeList = new ArrayList<>();
    /**
     * 玩家基础属性列表值，下标对应MyEnumAttributeType.index（后期如果不需要保存，应该要删除）
     */
    private List<Long> attBaseList = new ArrayList<>();
    /**
     * 玩家穿戴的装备
     */
    private List<Equip> equipList = new ArrayList<>();
    /**
     * 上阵的宠物
     */
    private List<Hero> heroList = new ArrayList<>();

    /******************************** 临时属性 ********************************/
    /**
     * 上一次收到玩家请求的msgId
     */
    @Transient
    private transient int lastMsgId;
    /**
     * 推送客户端物品变化列表
     */
    @Transient
    private transient List<BeanProto.ItemInfo> pbItemChangeList = new ArrayList<>();
    /**
     * 本次竞技场挑战玩家列表和他们的积分
     */
    @Transient
    private transient Map<Long,Integer> arenaChallengePlayerIdScoreMap = new HashMap<>();
    /**
     * 本次竞技场挑战玩家列表和他们的信息
     */
    @Transient
    private transient Map<Long, BeanProto.PlayerInfo> arenaChallengePlayerInfoMap  = new HashMap<>();
    /**
     * 本次竞技场挑战配置id
     */
    @Transient
    private transient int arenaMatchConfigId;
    /**
     * 本次竞技场挑战我的积分
     */
    @Transient
    private transient int arenaTempScore;
    /**
     * 本次竞技场挑战的对手玩家id
     */
    @Transient
    private transient long arenaTargetPlayerId;

}
