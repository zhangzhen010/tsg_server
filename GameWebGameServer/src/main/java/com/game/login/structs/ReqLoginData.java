package com.game.login.structs;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * 请求登录数据
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 16:42
 */
@Getter
@Setter
public class ReqLoginData {

    /**
     * 钱包账号
     */
    private String account;
    /**
     * 钱包签名
     */
    @NotEmpty(message = "Password cannot be empty!")
    private String pwd;
    /**
     * 签名原文
     */
    private String signContent;

}
