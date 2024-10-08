package com.game.pay.structs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 充值订单
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/19 17:20
 */
@Document(collection = "payOrder")
public class PayOrder {

    /**
     * 游戏订单id
     */
    @Id
    private long gameOrderId;
    /**
     * 平台订单号
     */
    private String pfOrderId = "";
    /**
     * 充值源配置id
     */
    private int payId;
    /**
     * 充值映射配置id
     */
    private int payMapId;
    /**
     * 充值类型
     */
    private int type;
    /**
     * 金额（单位：分）
     */
    private int money;
    /**
     * 用户id
     */
    private long userId;
    /**
     * 角色id
     */
    private long playerId;
    /**
     * 服务器id
     */
    private int serverId;
    /**
     * 角色名称
     */
    private String playerName;
    /**
     * 订单状态
     */
    private int payState;
    /**
     * 订单状态消息
     */
    private String msg = "";
    /**
     * pfId
     */
    private int pfId;
    /**
     * sdkId
     */
    private int sdkId;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 扩展信息
     */
    private String extInfo = "";

    public long getGameOrderId() {
        return gameOrderId;
    }

    public void setGameOrderId(long gameOrderId) {
        this.gameOrderId = gameOrderId;
    }

    public String getPfOrderId() {
        return pfOrderId;
    }

    public void setPfOrderId(String pfOrderId) {
        this.pfOrderId = pfOrderId;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public int getPayMapId() {
        return payMapId;
    }

    public void setPayMapId(int payMapId) {
        this.payMapId = payMapId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPayState() {
        return payState;
    }

    public void setPayState(int payState) {
        this.payState = payState;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPfId() {
        return pfId;
    }

    public void setPfId(int pfId) {
        this.pfId = pfId;
    }

    public int getSdkId() {
        return sdkId;
    }

    public void setSdkId(int sdkId) {
        this.sdkId = sdkId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }
}
