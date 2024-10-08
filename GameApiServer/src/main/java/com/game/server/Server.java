package com.game.server;

import com.game.grpc.manager.GrpcGameServerManager;
import com.game.utils.SpringBootUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 服务父类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/12 11:41
 */
public abstract class Server implements Runnable {

	public Server() {

	}

	public void run() {
		// 添加到Grpc服务管理退出
		SpringBootUtils.getBean(GrpcGameServerManager.class).addServer(this);
		// 添加到钩子管理退出
		Runtime.getRuntime().addShutdownHook(new Thread(new CloseByExit()));
	}

	public abstract void stop();

	private class CloseByExit implements Runnable {

		private final Logger log = LogManager.getLogger(CloseByExit.class);

		public CloseByExit() {

		}

		public void run() {
			log.info(Server.this.getClass().getSimpleName() + " Start Server Stop!");
			Server.this.stop();
			log.info(Server.this.getClass().getSimpleName() + " End Server Stop!");
		}
	}
}
