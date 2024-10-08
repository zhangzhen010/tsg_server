package com.game.netty.handler;

import com.game.netty.INettyServer;
import com.game.utils.CtxUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.log4j.Log4j2;

/**
 * websocket消息处理
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/14 15:56
 */
@Log4j2
public class NettyWebSocketProHandler extends ChannelInboundHandlerAdapter {

    private final INettyServer server;

    public NettyWebSocketProHandler(INettyServer server) {
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        final WebSocketFrame frame = (WebSocketFrame) msg;
        ByteBuf content = frame.content();
        try {
            if (frame instanceof BinaryWebSocketFrame) {
                if (frame.isFinalFragment()) {
                    while (content.isReadable()) {
                        if (content.readableBytes() < 4) {
                            break;
                        }
//						log.info("缓存子节数=" + content.readableBytes());
                        content.markReaderIndex();
                        // (这里采用大端读取数据)
                        int length = content.readInt();
                        if (content.readableBytes() < length + 4) {
                            content.resetReaderIndex();
                            break;
                        }
                        byte[] data = new byte[length + 4];
//						log.info("length=" + length + " 剩余长度=" + content.readableBytes());
                        content.readBytes(data);
                        ByteBuf buf = Unpooled.wrappedBuffer(data);
                        try {
                            this.server.doCommand(ctx, buf);
                        } finally {
                            ReferenceCountUtil.release(buf);
                        }
                    }
                }
            } else if (frame instanceof TextWebSocketFrame) {
                // 测试
                TextWebSocketFrame text = (TextWebSocketFrame) frame;
                TextWebSocketFrame f = new TextWebSocketFrame("服务器收到返回：" + text.text());
                ctx.writeAndFlush(f);
            } else if (frame instanceof CloseWebSocketFrame) {
                // 操作码8意味着关闭。
                CtxUtil.closeCtx(ctx, "WebSocket客户端发送关闭！");
            }
        } catch (Exception e) {
            log.error("websocket解析异常：", e);
        } finally {
            ReferenceCountUtil.release(content);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        server.userEventTriggered(ctx, evt);
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
