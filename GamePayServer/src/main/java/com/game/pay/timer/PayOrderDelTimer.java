package com.game.pay.timer;

import com.game.pay.manager.PayManager;
import com.game.timer.TimerEvent;
import com.game.utils.TimeUtil;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 定期移除缓存历史充值订单
 *
 * @author zhangzhen
 * @time 2021年10月11日
 */
@Component
public class PayOrderDelTimer extends TimerEvent {

    @Lazy
    private @Resource PayManager payManager;

    public PayOrderDelTimer() {
        super(-1, TimeUtil.TEN_MIN_MILLIS);
    }

    @Override
    public void action() {
        payManager.removePayOrder();
    }

}
