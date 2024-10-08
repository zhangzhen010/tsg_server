package com.game.player.structs;

/**
 * 玩家设置数据（所有直接由客户端传上来，服务器保存的数据，都可以走这里）
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/7 10:16
 */
public class PlayerSettingData {

    /**
     * 修改名字次数（第一次免费）
     */
    private int changeNameNum;
    /**
     * 客户端设置数据
     */
    private byte[] clientSettingInfo;

    public int getChangeNameNum() {
        return changeNameNum;
    }

    public void setChangeNameNum(int changeNameNum) {
        this.changeNameNum = changeNameNum;
    }

    public byte[] getClientSettingInfo() {
        return clientSettingInfo;
    }

    public void setClientSettingInfo(byte[] clientSettingInfo) {
        this.clientSettingInfo = clientSettingInfo;
    }

}
