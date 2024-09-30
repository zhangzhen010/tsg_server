package com.game.controller.structs;

import lombok.Data;

/**
 * 请求对象
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 10:35
 */
@Data
public class UserInfo {
    /**
     * 用户ID。
     */
    private Long userid;

    /**
     * 用户的电子邮件地址。
     */
    private String email;

    /**
     * 用户账户名称。
     */
    private String account;

    /**
     * 用户的物理地址。
     */
    private String address;

    /**
     * 用户所属组织的ID。
     */
    private Integer orgaId;

    /**
     * 表示用户是否为管理员的标志。
     * 通常0表示普通用户，1表示管理员。
     */
    private Integer admin;

    /**
     * 用户的时间戳或注册时间。
     */
    private String time;

}