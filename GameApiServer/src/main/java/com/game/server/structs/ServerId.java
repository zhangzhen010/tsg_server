package com.game.server.structs;

import lombok.Getter;

/**
 * 服务器id
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/7/1 15:10
 */
@Getter
public enum ServerId {

    /**
     * 充值服
     */
    PAY(20201),
    /**
     * 邮件服
     */
    MAIL(21001),
    /**
     * 聊天服
     */
    CHAT(20301),
    /**
     * 竞技场
     */
    ARENA(20601),
    /**
     * 网页游戏服
     */
    WEB_GAME(21101),

    ;

    /**
     * 起始服务器id
     */
    private final Integer startId;

    ServerId(Integer startId) {
        this.startId = startId;
    }
}
