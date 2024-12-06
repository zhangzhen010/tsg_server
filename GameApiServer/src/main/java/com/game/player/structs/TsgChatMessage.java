package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 单个聊天消息内容
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/11/18 9:23
 */
@Getter
@Setter
public class TsgChatMessage {

    /**
     * 消息类型 0=用户 1=后台管理员
     */
    private int type;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 发送时间
     */
    private long sendTime;

}
