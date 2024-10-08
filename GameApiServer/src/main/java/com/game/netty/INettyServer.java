package com.game.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * netty服务器接口
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/12 11:35
 */
public interface INettyServer {

    public void doCommand(ChannelHandlerContext ctx, ByteBuf buf);

    public void ctxCreate(ChannelHandlerContext ctx);

    public void ctxOpened(ChannelHandlerContext ctx);

    public void ctxClosed(ChannelHandlerContext ctx);

    public void ctxCancel(ChannelHandlerContext ctx);

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable paramThrowable);

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt);

}