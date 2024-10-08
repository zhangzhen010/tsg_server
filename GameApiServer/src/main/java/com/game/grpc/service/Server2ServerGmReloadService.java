package com.game.grpc.service;

import com.alibaba.fastjson2.JSON;
import com.game.data.define.MyDefineConstant;
import com.game.data.manager.ReloadDataManager;
import com.game.gm.structs.GMComm;
import com.game.grpc.proto.GrpcProto;
import com.game.grpc.proto.Server2ServerGmReloadServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 重新加载配置表gm命令service
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/2/28 11:53
 */
@Component
@Log4j2
public class Server2ServerGmReloadService extends Server2ServerGmReloadServiceGrpc.Server2ServerGmReloadServiceImplBase {

    /**
     * 当前服务器id
     */
    private @Value("${server.serverId}") int serverId;
    private @Resource ReloadDataManager reloadDataManager;

    @Override
    public void reqGrpcGmReload(GrpcProto.GMReqCommand reqCommand, StreamObserver<GrpcProto.GMResCommand> responseObserver) {
        // 注意：这里目前只处理重新加载配置表
        GrpcProto.GMResCommand.Builder builder = GrpcProto.GMResCommand.newBuilder();
        builder.setId(reqCommand.getId());
        builder.setState(0);
        builder.setCommand(reqCommand.getCommand());
        try {
            // 内容
            if (!reqCommand.getVer().equals(MyDefineConstant.GM_VERSION)) {
                log.error("在使用老版本GM工具发送gm命令，time=" + System.currentTimeMillis());
                builder.setState(1);
                builder.setMsg("执行gm命令：[" + reqCommand.getCommand() + "]异常：" + "在使用老版本GM工具发送gm命令，time=" + System.currentTimeMillis());
                responseObserver.onNext(builder.build());
                // 告诉客户端这次调用已经完成
                responseObserver.onCompleted();
                return;
            }
            if (reqCommand.getCommand().equals(GMComm.T_RELOADDATA.getValue())) {
                // 重新加载配置表
                String tableName = reqCommand.getParameter(0);
                // 执行转发gm命令
                boolean is = reloadDataManager.reLoad(tableName);
                if (is) {
                    // onNext()方法向后台客户端返回结果
                    builder.setMsg("服务器[" + serverId + "]执行gm命令：[" + tableName + "]加载配置表成功！");
                } else {
                    // onNext()方法向后台客户端返回结果
                    builder.setState(1);
                    builder.setMsg("服务器[" + serverId + "]执行gm命令：[" + tableName + "]加载配置表失败！");
                }
            } else {
                builder.setState(2);
                builder.setMsg("服务器[" + serverId + "]没有此命令command=" + reqCommand.getCommand());
            }
            responseObserver.onNext(builder.build());
            // 告诉客户端这次调用已经完成
            responseObserver.onCompleted();
        } catch (Exception e) {
            String errJson = JSON.toJSONString(reqCommand);
            log.error("收到grpc发来的gm命令service异常", e);
            builder.setState(2);
            builder.setMsg("gm异常->参数:" + errJson + " msg:" + e.getMessage());
            responseObserver.onNext(builder.build());
            // 告诉客户端这次调用已经完成
            responseObserver.onCompleted();
        }
    }

}
