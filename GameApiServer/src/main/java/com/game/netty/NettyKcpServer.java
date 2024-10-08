package com.game.netty;

import com.game.data.define.MyDefineConstant;
import com.game.message.struct.MsgId;
import com.game.netty.handler.NettyKcpProHandler;
import com.game.server.Server;
import io.jpower.kcp.netty.ChannelOptionHelper;
import io.jpower.kcp.netty.UkcpChannel;
import io.jpower.kcp.netty.UkcpChannelOption;
import io.jpower.kcp.netty.UkcpServerChannel;
import io.netty.bootstrap.UkcpServerBootstrap;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * netty Kcp服务器
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/7 15:42
 */
@Component
@Log4j2
public abstract class NettyKcpServer extends Server implements INettyServer {

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
        try {
            super.run();
            // 启动房间服通信
            new Thread(new Runnable() {
                @Override
                public void run() {
                    EventLoopGroup group = new NioEventLoopGroup();
                    try {
                        UkcpServerBootstrap bootstrap = new UkcpServerBootstrap();
                        bootstrap.group(group).channel(UkcpServerChannel.class).childHandler(new ChannelInitializer<UkcpChannel>() {
                            @Override
                            public void initChannel(UkcpChannel ch) throws Exception {
                                ChannelPipeline p = ch.pipeline();
                                p.addLast(new IdleStateHandler(60, 60, 60, TimeUnit.SECONDS));
                                p.addLast(new NettyKcpProHandler(NettyKcpServer.this));
                            }
                        });
                        // 不等待 延迟 快速重发
                        ChannelOptionHelper.nodelay(bootstrap, true, 20, 2, true).childOption(UkcpChannelOption.UKCP_MTU, 512);
                        ChannelFuture future = bootstrap.bind(outPort).sync();
                        log.info("Netty Server Start At Port(KCP):" + outPort);
                        // 等待关闭
                        future.channel().closeFuture().sync();
                    } catch (Exception e) {
                        log.error("Netty KCP 启动异常:", e);
                    } finally {
                        group.shutdownGracefully();
                    }
                }
            }).start();
        } catch (Exception e) {
            log.error("服务器启动通信异常：", e);
        }
    }

    @Override
    public void ctxOpened(ChannelHandlerContext ctx) {
        UkcpChannel kcpCh = (UkcpChannel) ctx.channel();
        // 服务器不需要自动，因为定义开始都为0，一致可以通信登录
        // 然后客户端设置为自动，conv值初始为0
        // 流程是，双方开始都为0，只有都为0时，自动更新才能生效，更新后固定为更新值，不能再次改变（看源码发现）
        // 服务器在客户端登录成功之后，随机一个conv值设置，返回消息给客户端时，客户端此时本地conv值为0，发下消息内收到的conv不一致后自动更新为服务器的conv值

        // 由于客户端kcp框架不支持自动更新conv值，所以改为客户端创建kcp连接的时候随机一个conv值，服务器自动更新
        kcpCh.config().setAutoSetConv(true);
        kcpCh.conv(MyDefineConstant.KCP_CONV_DEFAULT);
    }

    /**
     * 发送消息到客户端(KCP)
     *
     * @param ctx
     * @param msgId
     * @param data
     */
    public static void writeData(ChannelHandlerContext ctx, MsgId msgId, byte[] data) {
        try {
            if (ctx == null || !ctx.channel().isActive()) {
                return;
            }
            CompositeByteBuf buf = ctx.alloc().compositeDirectBuffer(data.length + 4);
            // 与客户端通信(这里采用大端读取数据)
            buf.writeInt(msgId.getMsgId());
            buf.writeBytes(data);
            ctx.channel().writeAndFlush(buf);
        } catch (Exception e) {
            log.error("发送消息到客户端(KCP)异常msgId=" + msgId.getMsgId(), e);
        }
    }

}
