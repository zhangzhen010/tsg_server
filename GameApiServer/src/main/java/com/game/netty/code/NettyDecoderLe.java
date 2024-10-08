package com.game.netty.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码器(小端)
 * 
 * @author zhangzhen
 * @time 2020年4月26日
 */
public class NettyDecoderLe extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		while (true) {
			if (in.readableBytes() < 4) {
				break;
			}
			in.markReaderIndex();
			// 长度只包含pb长度，不包含长度本身长度4+消息id长度4
			int length = in.readIntLE();
			if (in.readableBytes() < length + 4) {
				in.resetReaderIndex();
				break;
			}
			byte[] data = new byte[length + 4];
			in.readBytes(data);
			out.add(data);
			if (!in.isReadable()) {
				break;
			}
		}
	}

}
