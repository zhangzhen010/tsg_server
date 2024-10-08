package com.game.httpserver.netty.handler;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

public abstract class HttpHandler {

	/**
	 * 通信频道
	 */
	private ChannelHandlerContext ctx;
	/**
	 * 内容
	 */
	private Map<String, String> attMap;

	/**
	 * 执行动作
	 */
	public abstract void handle();

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public Map<String, String> getAttMap() {
		return attMap;
	}

	public void setAttMap(Map<String, String> attMap) {
		this.attMap = attMap;
	}

}
