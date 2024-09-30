package com.game.controller.structs;

/**
 * 统一返回枚举
 *
 * @author djq
 * @version 1.0
 * @date 2023/5/13 14:26
 */
public enum ResponseCodeEnum {
    //<editor-fold desc="成功或失败的相关提示信息">
    SUCCESS(200, "Success!"),
    SUCCESS_ADD(200, "添加成功!"),
    SUCCESS_UPDATE(200, "更新成功!"),
    SUCCESS_DELETE(200, "删除成功!"),
    NO_TOKEN(400,"无token，请重新登录"),
    TOKEN_EX(401,"token验证失败，请重新登录"),
    USER_EX(402,"用户不存在，请重新登录"),
    Fail(-1, "Fail!"),
    Fail_ADD(-1, "添加失败!"),
    Fail_UPDATE(-1, "更新失败!"),
    Fail_DELETE(-1, "删除失败!"),
    FAIL_PLAYER_NAME_EX(1, "玩家名字重复！"),


    ;
    //</editor-fold>

    private int code;

    private String message;

    ResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    }