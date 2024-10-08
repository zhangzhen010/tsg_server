package com.game.server.structs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 逻辑服务器数据，用于存放逻辑服务器一些数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/9 11:22
 */
@Document(collection = "serverData")
public class ServerData {

    /**
     * 逻辑服务器唯一id
     */
    @Id
    private int serverId;
    /**
     * 首次开服时间
     */
    private long openTime;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }
}
