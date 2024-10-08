package com.game.netty.handler;

import com.game.netty.INettyServer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * kcp消息处理
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/7 16:42
 */
public class NettyKcpProHandler extends ChannelInboundHandlerAdapter {

    private final INettyServer server;

    public NettyKcpProHandler(INettyServer server) {
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        try {
            this.server.doCommand(ctx, buf);
        } finally {
            ReferenceCountUtil.release(buf);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        this.server.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        // channel注册事件
        this.server.ctxCreate(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        // channel取消注册事件
        this.server.ctxCancel(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // channel连接
        this.server.ctxOpened(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // channel断开连接
        this.server.ctxClosed(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        this.server.exceptionCaught(ctx, cause);
    }

}
