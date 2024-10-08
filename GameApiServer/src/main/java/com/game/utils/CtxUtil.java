package com.game.utils;

import com.game.server.structs.CtxAttType;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ctx工具
 * 
 * @author zhangzhen
 * @time 2022年9月28日
 */
@Log4j2
public class CtxUtil {

	private static final Logger closelog = LogManager.getLogger("SESSIONCLOSE");

	public static void closeCtx(ChannelHandlerContext ctx, String reason) {
		closeCtx(ctx, reason, false);
	}

	public static void closeCtx(ChannelHandlerContext ctx, String reason, boolean isGameLog) {
		ctx.channel().attr(CtxAttType.SESSION_CLOSE_INFO).set(reason);
		ctx.close();
		if (isGameLog) {
			log.error(ctx + "[服务器主动踢人]---->" + reason);
		} else {
			closelog.error(ctx + "[服务器主动踢人]---->" + reason);
		}
	}

}