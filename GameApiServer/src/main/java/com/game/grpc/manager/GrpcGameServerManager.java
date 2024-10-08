package com.game.grpc.manager;

import com.game.server.Server;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * grpc服务器管理类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/9/27 17:49
 */
@Component
@Log4j2
@Getter
@Setter
public class GrpcGameServerManager {

    /**
     * 当前进程服务列表
     */
    private List<Server> serverList = new ArrayList<>();

    public void addServer(Server server) {
        serverList.add(server);
    }

    public void stopServer() {
        for (Server server : serverList) {
            log.info("Grpc Start Server Stop --->" + server.getClass().getSimpleName());
            server.stop();
            log.info("Grpc End Server Stop --->" + server.getClass().getSimpleName());
        }
    }

}
