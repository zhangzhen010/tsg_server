package com.game.pay.timer;


import com.game.grpc.proto.GrpcProto;
import com.game.pay.manager.PayManager;
import com.game.timer.TimerEvent;
import com.game.utils.SpringBootUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 充值验证timer（非回调方式）
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/31 15:09
 */
@Getter
@Setter
public class PayVerifyTimer extends TimerEvent {

    /**
     * 服务器id
     */
    private final GrpcProto.ReqPublicPayServerVerify reqData;

    public PayVerifyTimer(GrpcProto.ReqPublicPayServerVerify reqData) {
        super(1, 0);
        this.reqData = reqData;
    }

    @Override
    public void action() {
        SpringBootUtils.getBean(PayManager.class).reqPublicPayCheck(reqData);
        super.action();
    }

}
