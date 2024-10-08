package com.game.httpserver.netty;

import com.game.httpserver.netty.handler.HttpServerHandler;
import com.game.httpserver.server.HttpServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * netty服务器
 * 
 * @author zhangzhen
 * @time 2020年4月26日
 */
public class HttpNettyServer implements Runnable {

	private Logger log = LogManager.getLogger(HttpNettyServer.class);

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
							// 注意，下方代码是解析http ssl验证，但是一般这里直接使用nginx来处理，这段代码注释保留，但是用不上
							// server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
							// socketChannel.pipeline().addLast(new HttpResponseEncoder());
							// server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
							// socketChannel.pipeline().addLast(new HttpRequestDecoder());
//							if (!PayServer.IS_WIN_OS) {
//								char[] passArray = "i2ad57ijn765".toCharArray(); // jks密码
//								SSLContext sslContext = SSLContext.getInstance("TLSv1");
//								KeyStore ks = KeyStore.getInstance("JKS");
//								// 加载keytool 生成的文件
//								FileInputStream inputStream = new FileInputStream(
//										"mdtscyzh-android-1-center.mdtgame.com.jks");
//								ks.load(inputStream, passArray);
//								KeyManagerFactory kmf = KeyManagerFactory
//										.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//								kmf.init(ks, passArray);
//								sslContext.init(kmf.getKeyManagers(), null, null);
//								inputStream.close();
//								SSLEngine sslEngine = sslContext.createSSLEngine();
//								sslEngine.setUseClientMode(false);
//								socketChannel.pipeline().addLast(new SslHandler(sslEngine));
//								log.info("开启 out ssl 验证...!");
//							}
							// http消息编解码器
							socketChannel.pipeline().addLast(new HttpServerCodec());
							// http整合器
							socketChannel.pipeline().addLast(new HttpObjectAggregator(1024 * 1024));
							// 增加自定义实现的Handler
							socketChannel.pipeline().addLast(new HttpServerHandler());
						}
					})
					/**
					 * 对于ChannelOption.SO_BACKLOG的解释：
					 * 服务器端TCP内核维护有两个队列，我们称之为A、B队列。客户端向服务器端connect时，会发送带有SYN标志的包（第一次握手），服务器端
					 * 接收到客户端发送的SYN时，向客户端发送SYN ACK确认（第二次握手），此时TCP内核模块把客户端连接加入到A队列中，然后服务器接收到
					 * 客户端发送的ACK时（第三次握手），TCP内核模块把客户端连接从A队列移动到B队列，连接完成，应用程序的accept会返回。也就是说accept
					 * 从B队列中取出完成了三次握手的连接。
					 * A队列和B队列的长度之和就是backlog。当A、B队列的长度之和大于ChannelOption.SO_BACKLOG时，新的连接将会被TCP内核拒绝。
					 * 所以，如果backlog过小，可能会出现accept速度跟不上，A、B队列满了，导致新的客户端无法连接。要注意的是，backlog对程序支持的
					 * 连接数并无影响，backlog影响的只是还没有被accept取出的连接
					 */
					.option(ChannelOption.SO_BACKLOG, 1024 * 1024) // 设置缓冲区
					.option(ChannelOption.SO_RCVBUF, 1024 * 1024) // 设置接受数据缓冲大小
//					.option(ChannelOption.SO_SNDBUF, 1024 * 1024) // 设置发送数据缓冲大小
					.childOption(ChannelOption.SO_KEEPALIVE, true); // 保持连接
			ChannelFuture future = bootstrap.bind(HttpServer.getInstance().getHttpPort()).sync();
			log.info("HttpServer启动端口：" + HttpServer.getInstance().getHttpPort());
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			log.error("HttpServer启动异常", e);
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
