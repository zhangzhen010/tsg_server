package com.game.log.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 服务器在线人数历史日志（用来显示历史在线人数柱状图）
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/27 17:04
 */
@Document(collection = "serverOnlineHistoryLog")
public class ServerOnlineHistoryLog extends BaseLog {

    /**
     * 服务器id
     */
    @Id
    private long id;
    /**
     * 在线数量
     */
    private long num;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

}
