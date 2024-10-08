package com.game.netty;

import com.game.data.define.MyDefineConstant;
import com.game.data.define.MyDefineServerRunState;
import com.game.message.struct.MsgId;
import com.game.netty.code.NettyDecoder;
import com.game.netty.code.NettyEncoder;
import com.game.netty.handler.NettyProHandler;
import com.game.server.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * netty外网服务器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/12 11:11
 */
@Component
@Log4j2
public abstract class NettyOutServer extends Server implements INettyServer {

    /**
     * tcp外部端口
     */
    private @Value("${server.outPort}") int outPort;
    /**
     * tcp内部端口
     */
    private @Value("${server.inPort}") int inPort;
    /**
     * http外部端口
     */
    private @Value("${server.httpPort}") int httpPort;
    /**
     * 服务器id
     */
    private @Value("${server.serverId}") int serverId;

    @Override
    public void run() {
        super.run();
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventLoopGroup bossGroup = new NioEventLoopGroup(1); // 用于处理服务器端接收客户端连接,1个线程即可
                EventLoopGroup workerGroup = new NioEventLoopGroup(); // 进行网络通信（读写）默认cpu核心数2倍
                try {
                    ServerBootstrap bootstrap = new ServerBootstrap(); // 辅助工具类，用于服务器通道的一系列配置
                    bootstrap.group(bossGroup, workerGroup) // 绑定两个线程组
                            .channel(NioServerSocketChannel.class) // 指定NIO的模式
                            .childHandler(new ChannelInitializer<SocketChannel>() { // 配置具体的数据处理方式
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
									/*
									* @param maxFrameLength  帧的最大长度
										    @param lengthFieldOffset length字段偏移的地址
										    @param lengthFieldLength length字段所占的字节长
										    @param lengthAdjustment 修改帧数据长度字段中定义的值，可以为负数 因为有时候我们习惯把头部记入长度,若为负数,则说明要推后多少个字段
										    @param initialBytesToStrip 解析时候跳过多少个长度
										    @param failFast 为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异
											socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(
													ByteOrder.LITTLE_ENDIAN, Integer.MAX_VALUE, 0, 4, 2, 4, true));
									* */
                                    socketChannel.pipeline().addLast(new IdleStateHandler(60, 60, 60, TimeUnit.SECONDS));
                                    socketChannel.pipeline().addLast(new NettyDecoder());
                                    socketChannel.pipeline().addLast(new NettyEncoder<>());
                                    socketChannel.pipeline().addLast(new NettyProHandler(NettyOutServer.this));
                                }
                            })
                            /*
                             * 对于ChannelOption.SO_BACKLOG的解释：
                             * 服务器端TCP内核维护有两个队列，我们称之为A、B队列。客户端向服务器端connect时，会发送带有SYN标志的包（第一次握手），服务器端
                             * 接收到客户端发送的SYN时，向客户端发送SYN ACK确认（第二次握手），此时TCP内核模块把客户端连接加入到A队列中，然后服务器接收到
                             * 客户端发送的ACK时（第三次握手），TCP内核模块把客户端连接从A队列移动到B队列，连接完成，应用程序的accept会返回。也就是说accept
                             * 从B队列中取出完成了三次握手的连接。
                             * A队列和B队列的长度之和就是backlog。当A、B队列的长度之和大于ChannelOption.SO_BACKLOG时，新的连接将会被TCP内核拒绝。
                             * 所以，如果backlog过小，可能会出现accept速度跟不上，A、B队列满了，导致新的客户端无法连接。要注意的是，backlog对程序支持的
                             * 连接数并无影响，backlog影响的只是还没有被accept取出的连接
                             */.option(ChannelOption.SO_BACKLOG, 1024 * 1024) // 设置TCP缓冲区
                            .option(ChannelOption.SO_RCVBUF, 1024 * 1024) // 设置接受数据缓冲大小
//								.option(ChannelOption.SO_SNDBUF, 5 * 1024) // 设置发送数据缓冲大小
                            .childOption(ChannelOption.SO_KEEPALIVE, true); // 保持连接
                    ChannelFuture future = bootstrap.bind(outPort).sync();
                    log.info("Netty Server Start At Port(TCP):" + outPort);
                    future.channel().closeFuture().sync();
                } catch (Exception e) {
                    log.error("netty启动异常", e);
                } finally {
                    workerGroup.shutdownGracefully();
                    bossGroup.shutdownGracefully();
                }
            }
        }).start();
    }

    @Override
    public void ctxOpened(ChannelHandlerContext ctx) {

    }

    /**
     * 发送消息到客户端(TCP)
     *
     * @param ctx
     * @param msgId
     * @param data
     */
    public static void writeData(ChannelHandlerContext ctx, MsgId msgId, byte[] data) {
        try {
            // GameServer.closeCtx PlayerManager.exitServerSaveAllPlayer deadlock.
            if (MyDefineConstant.serverState == MyDefineServerRunState.STOP || ctx == null || !ctx.channel().isActive()) {
                return;
            }
            int length = data.length;
            CompositeByteBuf buf = ctx.alloc().compositeDirectBuffer(length + 8);
            // 与客户端通信(这里采用大端读取数据)
            buf.writeInt(length);
            buf.writeInt(msgId.getMsgId());
            buf.writeBytes(data);
            ctx.channel().writeAndFlush(buf);
        } catch (Exception e) {
            log.error("发送消息到客户端(TCP)异常msgId=" + msgId.getMsgId(), e);
        }
    }

}
