package com.game.player.structs;

import lombok.Getter;
import lombok.Setter;

/**
 * 请求修改玩家信息
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/26 16:27
 */
@Getter
@Setter
public class ReqPlayerContactMeInfo {

    /**
     * 聊天内容
     */
    private String message = "";

}
