package com.game.log.bean;

import org.springframework.data.annotation.Transient;

/**
 * 用户创建日志
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/26 15:45
 */
public class UserCreateLog extends BaseLog {

    @Transient
    private String tabName = "userCreateLog_";

    /**
     * 用户唯一id
     */
    private long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * sdkId
     */
    private int sdkId;
    /**
     * pfId
     */
    private int pfId;
    /**
     * 设备号
     */
    private String deviceId;
    /**
     * ip
     */
    private String ip;
    /**
     * 客户端版本号
     */
    private String clientVersion;
    /**
     * 用户创建时间
     */
    private long createTime;

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSdkId() {
        return sdkId;
    }

    public void setSdkId(int sdkId) {
        this.sdkId = sdkId;
    }

    public int getPfId() {
        return pfId;
    }

    public void setPfId(int pfId) {
        this.pfId = pfId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
