package com.game.netty;

import com.game.message.struct.MsgId;
import com.game.netty.code.NettyDecoder;
import com.game.netty.code.NettyEncoder;
import com.game.netty.handler.NettyProHandler;
import com.game.server.Server;
import com.game.server.structs.CtxAttType;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * netty客户端
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 17:04
 */
@Component
@Log4j2
public abstract class NettyInClient extends Server implements INettyServer {

    /**
     * 本机服务器id
     */
    private @Value("${server.serverId}") int serverId;
    /**
     * 连接中心服务器ip
     */
    private String ip;
    /**
     * 连接中心服端口
     */
    private int port;
    /**
     * 连接boss线程
     */
    private Bootstrap b;

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            super.run();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    EventLoopGroup group = new NioEventLoopGroup(1);
                    try {
                        b = new Bootstrap();
                        b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline().addLast(new NettyDecoder());
                                socketChannel.pipeline().addLast(new NettyEncoder<>());
                                socketChannel.pipeline().addLast(new NettyProHandler(NettyInClient.this));
                            }
                        });
                        // 连接服务端
                        boolean connect = false;
                        while (!connect) {
                            connect = connect(ip, port, serverId);
                            if (!connect) {
                                log.error("启动首次连接" + this.getClass().getName() + "失败，等待下一次重连！");
                                Thread.sleep(5000L);
                            }
                        }
                    } catch (Exception e) {
                        log.error("启动Netty客户端异常：" + e.getMessage());
                    } finally {
                        // 需要重连，不用这个
//						group.shutdownGracefully();
                    }
                }
            }).start();
        } catch (Exception e) {
            log.error("netty客户端异常：", e);
        }
    }

    /**
     * netty客户端连接服务器
     */
    public boolean connect(String ip, int port, Integer serverId) {
        try {
            ChannelFuture f = b.connect(ip, port).sync();
            f.channel().attr(CtxAttType.SESSION_C2S_SERVERID).set(serverId);
            f.channel().attr(CtxAttType.SESSION_C2S_SERVERIP).set(ip);
            f.channel().attr(CtxAttType.SESSION_C2S_SERVERPORT).set(port);
            f.channel().attr(CtxAttType.SESSION_C2S_HEART_TIME).set(System.currentTimeMillis());
            register(f.channel());
//			f.channel().closeFuture().sync();
            return true;
        } catch (Exception e) {
            log.error("netty客户端连接服务器失败" + this.getClass().getName(), e);
            return false;
        }
    }

    /**
     * 网关服务器重新连接
     *
     * @param channel 通信通道
     */
    public void reConn(Channel channel) {
        try {
            String ip = channel.attr(CtxAttType.SESSION_C2S_SERVERIP).get();
            int port = channel.attr(CtxAttType.SESSION_C2S_SERVERPORT).get();
            Integer serverId = channel.attr(CtxAttType.SESSION_C2S_SERVERID).get();
            connect(ip, port, serverId);
//			EventLoop eventLoop = ctx.channel().eventLoop();
//			eventLoop.schedule(new Runnable() {
//				@Override
//				public void run() {
//					connect(ip, port, serverIdList);
//				}
//			}, 1L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("网关服务器重连异常", e);
        }
    }

    public abstract void register(Channel channel);

    /**
     * 发送消息到内网服务器
     *
     * @param channel
     * @param msgId
     * @param data
     */
    public static void writeData(Channel channel, MsgId msgId, byte[] data) {
        try {
            if (channel == null || !channel.isActive()) {
                return;
            }
            int length = data.length;
            CompositeByteBuf buf = channel.alloc().compositeDirectBuffer(length + 8);
            buf.writeInt(length);
            buf.writeInt(msgId.getMsgId());
            buf.writeBytes(data);
            channel.writeAndFlush(buf);
        } catch (Exception e) {
            log.error("发送消息到内网服务器异常msgId=" + msgId, e);
        }
    }

}
