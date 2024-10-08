package com.game.message;

import com.game.command.ICommand;
import com.game.player.structs.Player;
import io.netty.channel.ChannelHandlerContext;

/**
 * 处理类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/5/13 11:41
 */
public abstract class Handler implements ICommand {

	/**
	 * 通信频道
	 */
	private ChannelHandlerContext ctx;
	/**
	 * proto数据
	 */
	private Object proto;
	/**
	 * 执行对象
	 */
	private Player executor;
	/**
	 * 创建时间
	 */
	private long createTime;

	/**
	 * 获取线程队列
	 * 
	 * @return
	 */
	public abstract String getQueue();

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public Object getProto() {
		return proto;
	}

	public void setProto(Object proto) {
		this.proto = proto;
	}

	public Player getExecutor() {
		return executor;
	}

	public void setExecutor(Player executor) {
		this.executor = executor;
	}

	public long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}