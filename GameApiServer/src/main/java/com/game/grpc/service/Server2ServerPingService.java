package com.game.grpc.service;

import com.game.grpc.proto.Server2ServerPingServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * 判断服务器之间rpc连接状态ping rpc service
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/2/28 11:53
 */
@Component
@Log4j2
public class Server2ServerPingService extends Server2ServerPingServiceGrpc.Server2ServerPingServiceImplBase {

    @Override
    public void reqPing(Empty request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(null);
        responseObserver.onCompleted();
    }

}
