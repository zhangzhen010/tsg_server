package com.game.log.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 服务器在线人数日志
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/27 17:04
 */
@Document(collection = "serverOnlineLog")
public class ServerOnlineLog extends BaseLog {

    /**
     * 服务器id
     */
    @Id
    private int serverId;
    /**
     * 在线数量
     */
    private long num;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

}
