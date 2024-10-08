package com.game.data.define;

/**
 * 游戏常量
 */
public class MyDefineConstant {

    /**
     * 初始玩家id
     */
    public static final long PLAYERID_INIT = 10000;
    /**
     * 最大玩家逻辑区线程
     */
    public static final int MAXEXECTHREAD = 20;
    /**
     * 玩家逻辑区线程最大管理玩家数
     */
    public static final int MAXEXEC = 250;
    /**
     * 日志服：执行redis日志timer间隔时间毫秒
     */
    public static final int REDIS_LOG_DELAY = 1000;
    /**
     * 日志服：每次从redis获取日志数量
     */
    public static final int REDIS_LOG_NUM = 100;
    /**
     * GM命令版本号
     */
    public static final String GM_VERSION = "ver201801";
    /**
     * int最大值
     */
    public static final long INTEGER_MAX_VALUE = Integer.MAX_VALUE;
    /**
     * 逻辑服最大承载人数
     */
    public static int SERVER_LOGIC_MAX_PLAYER = 10000;
    /**
     * 房间服最大承载人数
     */
    public static int SERVER_ROOM_MAX_PLAYER = 10000;
    /**
     * kcp conv默认值
     */
    public static int KCP_CONV_DEFAULT = 0;
    /**
     * 万分比
     */
    public static int MAX_PROBABILITY = 10000;

    /**
     * 百分比
     */
    public static int MAX_PROBABILITY_BAI = 100;
    /**
     * 服务器当前状态（目前逻辑服务器使用，动态修改）
     */
    public static MyDefineServerRunState serverState = MyDefineServerRunState.LOAD;
    /**
     * 帧间隔时间(实际大概每秒22帧左右)
     */
    public static final int FRAME_TIME = 40;

}