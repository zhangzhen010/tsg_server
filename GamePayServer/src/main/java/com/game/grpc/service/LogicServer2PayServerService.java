package com.game.grpc.service;

import com.game.grpc.proto.GrpcProto;
import com.game.grpc.proto.LogicServer2PayServerServiceGrpc;
import com.game.pay.manager.PayManager;
import com.game.pay.timer.PayVerifyTimer;
import com.game.thread.manager.ThreadManager;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * 逻辑服向充值服grpc
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/31 14:57
 */
@Component
@Log4j2
public class LogicServer2PayServerService extends LogicServer2PayServerServiceGrpc.LogicServer2PayServerServiceImplBase {

    private @Resource PayManager payManager;
    private @Resource ThreadManager threadManager;

    @Override
    public void reqCreateOrder(GrpcProto.ReqPublicPayServerCreateOrder request, StreamObserver<GrpcProto.ResPublicPayServerCreateOrder> responseObserver) {
        try {
            GrpcProto.ResPublicPayServerCreateOrder resPublicPayServerCreateOrder = payManager.reqPublicPayServerCreateOrder(request);
            if (resPublicPayServerCreateOrder != null) {
                // onNext()方法向客户端返回结果
                responseObserver.onNext(resPublicPayServerCreateOrder);
            }
            // 告诉客户端这次调用已经完成
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("请求创建订单rpc service异常：", e);
        }
    }

    @Override
    public void reqPayVerify(GrpcProto.ReqPublicPayServerVerify request, StreamObserver<Empty> responseObserver) {
        try {
            PayVerifyTimer timer = new PayVerifyTimer(request);
            threadManager.getMainThread().addTimerEvent(timer);
            // onNext()方法向后台客户端返回结果
            responseObserver.onNext(null);
            // 告诉客户端这次调用已经完成
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("请求验证充值rpc service异常：", e);
        }
    }
}
