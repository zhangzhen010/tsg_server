package com.game.grpc.service;

import com.game.gm.structs.GMComm;
import com.game.grpc.proto.GrpcProto;
import com.game.grpc.proto.Server2ServerGmServiceGrpc;
import com.game.pay.structs.PayOrderState;
import com.game.pay.timer.PayBackTimer;
import com.game.thread.manager.ThreadManager;
import com.game.timer.ITimerCallback;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * 后台-向其他服务器发送Gm命令rpc service
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/1/24 16:58
 */
@Component
@Log4j2
public class Server2ServerGmService extends Server2ServerGmServiceGrpc.Server2ServerGmServiceImplBase {

    private @Resource ThreadManager threadManager;

    @Override
    public void reqGrpcGm(GrpcProto.GMReqCommand request, StreamObserver<GrpcProto.GMResCommand> responseObserver) {
        try {
            // 执行转发gm命令
            if (request.getCommand().equals(GMComm.T_PAY_REPAIR.getValue())) {
                long gameOrderId = request.getParameterLongValue(0);
                String pfOrderId = request.getParameter(0);
                PayBackTimer timer = new PayBackTimer(0, gameOrderId, pfOrderId, PayOrderState.REPAIR, "后台补单", "");
                timer.setCallback(new ITimerCallback() {
                    @Override
                    public void actionAfter() {
                        // onNext()方法向后台客户端返回结果
                        responseObserver.onNext(GrpcProto.GMResCommand.newBuilder().setState(0).setMsg("后台补单成功！").build());
                        // 告诉客户端这次调用已经完成
                        responseObserver.onCompleted();
                    }
                });
                threadManager.getMainThread().addTimerEvent(timer);

            }
        } catch (Exception e) {
            log.error("后台-向其他服务器发送Gm命令rpc service异常：", e);
        }
    }

}
