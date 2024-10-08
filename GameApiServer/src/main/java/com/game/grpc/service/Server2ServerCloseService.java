package com.game.grpc.service;

import com.game.grpc.manager.GrpcGameServerManager;
import com.game.grpc.proto.Server2ServerCloseServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * 指示关闭服务器进程 rpc service
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/27 17:30
 */
@Component
@Log4j2
public class Server2ServerCloseService extends Server2ServerCloseServiceGrpc.Server2ServerCloseServiceImplBase {

    private @Resource GrpcGameServerManager grpcGameServerManager;

    @Override
    public void reqGrpcCloseServer(Empty request, StreamObserver<Empty> responseObserver) {
        grpcGameServerManager.stopServer();
        responseObserver.onNext(null);
        responseObserver.onCompleted();
        log.info("使用GRPC的方式关闭服务器！");
        // 执行完关闭逻辑，等待1秒再退出
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("reqGrpcCloseServer error", e);
        }
        System.exit(0);
    }

}
