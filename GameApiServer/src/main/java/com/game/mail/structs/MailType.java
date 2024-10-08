package com.game.mail.structs;

import com.esotericsoftware.minlog.Log;

/**
 * 邮件类型
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/2/26 13:59
 */
public enum MailType {

    /**
     * 系统
     */
    SYSTEM(1, "系统邮件"),
    /**
     * 后台邮件
     */
    BACK(2, "后台邮件"),
    /**
     * 玩家
     */
    PLAYER(3, "玩家邮件"),

    ;

    private int type;

    private String name;

    MailType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * 根据type获取枚举
     *
     * @param type
     * @return
     */
    public static MailType getMailType(int type) {
        try {
            MailType[] values = MailType.values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].getType() == type) {
                    return values[i];
                }
            }
            return null;
        } catch (Exception e) {
            Log.error("根据id获取类型", e);
            return null;
        }
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
