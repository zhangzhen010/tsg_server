package com.game.grpc.service;

import com.game.gm.manager.GMManager;
import com.game.grpc.proto.GrpcProto;
import com.game.grpc.proto.Server2ServerGmServiceGrpc;
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

    private @Resource GMManager gmManager;

    @Override
    public void reqGrpcGm(GrpcProto.GMReqCommand request, StreamObserver<GrpcProto.GMResCommand> responseObserver) {
        try {
            // 执行转发gm命令
            GrpcProto.GMResCommand execGMCommRpc = gmManager.execGMCommRpc(responseObserver, request);
            if (execGMCommRpc != null) {
                // onNext()方法向后台客户端返回结果
                responseObserver.onNext(execGMCommRpc);
                // 告诉客户端这次调用已经完成
                responseObserver.onCompleted();
            }
        } catch (Exception e) {
            log.error("后台-向其他服务器发送Gm命令rpc service异常：", e);
        }
    }

}
