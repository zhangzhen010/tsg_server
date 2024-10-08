package com.game.mail.structs;

import com.esotericsoftware.minlog.Log;

/**
 * 邮件获取类型
 *
 * @author zhangzhen
 * @time 2020年4月21日
 */
public enum MailGetType {

    /**
     * 普通邮件（未读取不可一键领取）
     */
    NORMAL(0, "普通邮件（未读取不可一键领取）"),
    /**
     * 一键领取邮件
     */
    AUTO(1, "一键领取邮件"),
    ;

    private int type;

    private String name;

    MailGetType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * 根据type获取枚举
     *
     * @param type
     * @return
     */
    public static MailGetType getMailGetType(int type) {
        try {
            MailGetType[] values = MailGetType.values();
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
