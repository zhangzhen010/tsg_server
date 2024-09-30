package com.game.pay.timer;


import com.alibaba.fastjson2.JSONObject;
import com.game.pay.manager.PayManager;
import com.game.timer.TimerEvent;
import com.game.utils.SpringBootUtils;

/**
 * MoonPay交易回调timer
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/4 17:13
 */
public class MoonPayBackTimer extends TimerEvent {

    /**
     * 服务器id
     */
    private final JSONObject data;

    public MoonPayBackTimer(JSONObject data) {
        super(1, 0);
        this.data = data;
    }

    @Override
    public void action() {
        SpringBootUtils.getBean(PayManager.class).moonPayBack(data);
        super.action();
    }

}
