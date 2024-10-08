package com.game.httpserver.pool;

import com.game.httpserver.netty.handler.HttpHandler;

import java.util.HashMap;

/**
 * @author zhangzhen
 * 
 * @version 1.0.0
 * 
 *          引用类列表
 */
public class HttpPool {

	private static HttpPool instance = new HttpPool();

	public static HttpPool getInstance() {
		return instance;
	}

	private HashMap<String, Class<? extends HttpHandler>> handlerMap = new HashMap<>();

	public void register(String name, Class<? extends HttpHandler> handlerClass) {
		handlerMap.put(name, handlerClass);
	}

	public HttpHandler createHandler(String name) {
		Class<? extends HttpHandler> handlerClass = handlerMap.get(name);
		if (handlerClass == null) {
			return null;
		}
		HttpHandler handler = null;
		try {
			handler = handlerClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return handler;
	}

	private HttpPool() {
		
	}
}
