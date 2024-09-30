package com.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 中心服启动
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/29 14:27
 */
@SpringBootApplication
public class CenterMain {

	public static void main(String[] args) {
		// 忽略slf4j-simpler（第三方jar使用）实现的info及以下的日志
		System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "warn");
		SpringApplication.run(CenterMain.class, args);
	}

}
