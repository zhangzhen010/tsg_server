package com.game.pay.timer;


import com.game.pay.manager.PayManager;
import com.game.pay.structs.PayOrderState;
import com.game.timer.TimerEvent;
import com.game.utils.SpringBootUtils;

/**
 * 充值回调timer
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/3/27 14:50
 */
public class PayBackTimer extends TimerEvent {

    /**
     * 服务器id
     */
    private final int serverId;
    /**
     * 订单id
     */
    private final long payOrderId;
    /**
     * pf订单号
     */
    private final String pfOrderId;
    /**
     * 状态
     */
    private final PayOrderState state;
    /**
     * 消息
     */
    private final String msg;
    /**
     * 扩展信息
     */
    private final String extInfo;

    public PayBackTimer(int serverId, long gameOrderId, String pfOrderId, PayOrderState state, String msg, String extInfo) {
        super(1, 0);
        this.serverId = serverId;
        this.payOrderId = gameOrderId;
        this.pfOrderId = pfOrderId;
        this.state = state;
        this.msg = msg;
        this.extInfo = extInfo;
    }

    @Override
    public void action() {
        SpringBootUtils.getBean(PayManager.class).payBack(serverId, payOrderId, pfOrderId, state, msg, extInfo);
        super.action();
    }

}
