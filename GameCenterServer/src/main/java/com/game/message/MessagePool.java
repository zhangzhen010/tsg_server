package com.game.message;

import com.game.utils.SpringBootUtils;

import java.util.HashMap;

/**
 * MessagePool
 *
 * @author zhangzhen
 */
public class MessagePool {

	private static MessagePool instance = new MessagePool();

	public static MessagePool getInstance() {
		return instance;
	}

	private HashMap<Integer, Class<? extends Handler>> id2handler = new HashMap<Integer, Class<? extends Handler>>();

	public void register(int id, Class<? extends Handler> handlerClass) {
		id2handler.put(id, handlerClass);
	}

	public Handler createHandler(Integer id) {
		Class<? extends Handler> handler = id2handler.get(id);
		if (handler == null) {
			return null;
		}
		return SpringBootUtils.getBean(handler);
	}


	private MessagePool() {
	}
}