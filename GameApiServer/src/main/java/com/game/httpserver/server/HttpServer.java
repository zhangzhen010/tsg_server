package com.game.httpserver.server;

import com.game.httpserver.netty.HttpNettyServer;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.log4j.Log4j2;

/**
 * http服务器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/5 9:15
 */
@Log4j2
public class HttpServer {
    /**
     * 单例锁
     */
    private static final Object lock = new Object();
    /**
     * http服务
     */
    private static HttpServer httpServer;
    /**
     * http端口
     */
    private int httpPort;

    public int getHttpPort() {
        return httpPort;
    }

    /**
     * 获取httpServer单例对象
     */
    public static HttpServer getInstance() {
        try {
            if (httpServer == null) {
                synchronized (lock) {
                    if (httpServer == null) {
                        httpServer = new HttpServer();
                    }
                }
            }
            return httpServer;
        } catch (Exception e) {
            log.error("获取httpServer单例对象异常：", e);
            return null;
        }
    }

    /**
     * http服务器启动前初始化
     */
    private void init() {
        try {

        } catch (Exception e) {
            log.error("http服务器启动前初始化", e);
        }
    }

    /**
     * 启动http服务
     *
     * @param httpPort
     */
    public void start(int httpPort) {
        try {
            // 获取端口
            this.httpPort = httpPort;
            // 启动前初始化
            init();
            // 启动NettyServer
            new Thread(new HttpNettyServer()).start();
        } catch (Exception e) {
            log.error("启动http服务", e);
        }
    }

    /**
     * http返回
     */
    public void response(ChannelHandlerContext ctx, String content) {
        response(ctx, content, "text/html; charset=UTF-8");
    }

    /**
     * http返回
     */
    public void response(ChannelHandlerContext ctx, String content, String contentType) {
        try {
            FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));
            resp.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
            resp.headers().set(HttpHeaderNames.CONTENT_LENGTH, resp.content().readableBytes());
            resp.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            // 允许跨域访问
            resp.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            resp.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, "*");// 允许headers自定义
            // OPTIONS 跨域方法
            resp.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST,OPTIONS, PUT,DELETE");
            resp.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            // 发送 注意必须在使用完之后，close channel
            ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
        } catch (Exception e) {
            log.error("http返回", e);
        }
    }

}
