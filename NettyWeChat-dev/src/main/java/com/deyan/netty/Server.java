package com.deyan.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

	public static void main(String[] args) throws Exception {
		
		// 定义一对线程组
		// 主线程组用于接受客户端的连接, 但是不做任何处理
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// 从线程组, 老板线程组会把任务丢个他, 让手下线程组去做任务
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		// netty服务器的创建, ServerBootstrap是一个启动类
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			// 设置主从线程组
			serverBootstrap.group(bossGroup, workerGroup)
			// 设置nio双向通道
							.channel(NioServerSocketChannel.class)
			// 子处理器, 用于处理worderGroup
							.childHandler(null);
			
			// 启动server, 设置939位启动的端口号, 启动方式为同步
			ChannelFuture channelFuture = serverBootstrap.bind(939).sync();
			// 监听关闭的channel, 设置同步方式
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}

}
