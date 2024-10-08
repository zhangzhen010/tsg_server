package com.game.httpserver.netty.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.game.httpserver.pool.HttpPool;
import com.game.httpserver.server.HttpServer;
import com.game.server.structs.CtxAttType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 消息处理
 * 
 * @author zhangzhen
 * @time 2018年9月10日
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

	Logger log = LogManager.getLogger(HttpServerHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {
			FullHttpRequest req = (FullHttpRequest) msg;
			// 设置客户端ip
			String clientIp = req.headers().get("X-Real-IP");
			ctx.channel().attr(CtxAttType.CTX_CLIENT_IP).set(clientIp);
			try {
				// 获取请数据
				String uri = req.uri();
				// 获取sdk类型
				String methodName = uri;
				// 根据method，确定不同的逻辑
				if (req.method().equals(HttpMethod.GET)) {
					if (uri.equals("/favicon.ico")) {
						return;
					}
					if (uri.equals("/")) {
						HttpServer.getInstance().response(ctx, "");
						return;
					}
					int index = uri.indexOf("?");
					if (index != -1) {
						methodName = uri.substring(1, index);
					}
					methodName = methodName.replace("/", "");
					log.info("get methodName=" + methodName);
					Map<String, String> attMap = new HashMap<>();
					QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, CharsetUtil.UTF_8);
					Map<String, List<String>> uriAttributes = queryDecoder.parameters();
					for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
						for (String attrVal : attr.getValue()) {
							attMap.put(attr.getKey(), attrVal);
						}
					}
					// 处理
					HttpHandler handler = HttpPool.getInstance().createHandler(methodName);
					if (handler != null) {
						handler.setCtx(ctx);
						handler.setAttMap(attMap);
						handler.handle();
					} else {
						log.error("收到http请求方法名错误methodName=" + methodName);
					}
				} else if (req.method().equals(HttpMethod.POST)) {
//					methodName = uri.substring(1, methodName.length()).replace("/", "");
					methodName = uri.replace("/", "");
					log.info("post methodName=" + methodName);
					String contentType = req.headers().get("Content-Type");
					if (contentType == null) {
						// 黑鲨sdk请求没有传contentType
						contentType = "application/json";
					} else {
						String typeStr = req.headers().get("Content-Type").toString();
						contentType = typeStr.split(";")[0];
					}
					if (contentType.equals("application/json")) {
						String jsonStr = req.content().toString(CharsetUtil.UTF_8);
						JSONObject json = JSON.parseObject(jsonStr);
						Map<String, String> attMap = new HashMap<>();
						for (Entry<String, Object> entry : json.entrySet()) {
							attMap.put(entry.getKey(), String.valueOf(entry.getValue()));
						}
						// 处理
						HttpHandler handler = HttpPool.getInstance().createHandler(methodName);
						if (handler != null) {
							handler.setCtx(ctx);
							handler.setAttMap(attMap);
							handler.handle();
						} else {
							log.error("收到http请求方法名错误methodName=" + methodName);
						}
					} else if (contentType.equals("application/x-www-form-urlencoded")) {
						Map<String, String> attMap = new HashMap<>();
						String str = req.content().toString(CharsetUtil.UTF_8);
						QueryStringDecoder queryDecoder = new QueryStringDecoder(str, false);
						Map<String, List<String>> uriAttributes = queryDecoder.parameters();
						for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
							for (String attrVal : attr.getValue()) {
								attMap.put(attr.getKey(), attrVal);
							}
						}
						// 处理
						HttpHandler handler = HttpPool.getInstance().createHandler(methodName);
						if (handler != null) {
							handler.setCtx(ctx);
							handler.setAttMap(attMap);
							handler.handle();
						} else {
							log.error("收到http请求方法名错误methodName=" + methodName);
						}
					} else if (contentType.equals("application/x-tar")) {
						// 这个格式的post，只在一次对接htc的sdk充值回调中，他们使用了这种方式，其实跟application/x-www-form-urlencoded差不多，
						Map<String, String> attMap = new HashMap<>();
						String str = req.content().toString(CharsetUtil.UTF_8);
						QueryStringDecoder queryDecoder = new QueryStringDecoder(str, false);
						Map<String, List<String>> uriAttributes = queryDecoder.parameters();
						for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
							for (String attrVal : attr.getValue()) {
								attMap.put(attr.getKey(), attrVal);
							}
						}
						// 处理
						HttpHandler handler = HttpPool.getInstance().createHandler(methodName);
						if (handler != null) {
							handler.setCtx(ctx);
							handler.setAttMap(attMap);
							handler.handle();
						} else {
							log.error("收到http请求方法名错误methodName=" + methodName);
						}
					} else {
						log.error("暂不支持的http post方式：contentType=" + contentType);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("处理http消息处理消息异常：", e);
			} finally {
				req.release();
			}
		} else {
			log.error("msg=" + msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
//		log.error("收到成功连接消息:" + ctx.toString());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
//		log.error("收到断开消息:" + ctx.toString());
	}

}
