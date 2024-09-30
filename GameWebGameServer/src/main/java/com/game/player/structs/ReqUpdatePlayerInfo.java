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
public class ReqUpdatePlayerInfo {

    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 玩家名字
     */
    private String playerName;
    /**
     * 玩家昵称
     */
    private String userName;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 实物快递收货地址
     */
    private String address;

}
