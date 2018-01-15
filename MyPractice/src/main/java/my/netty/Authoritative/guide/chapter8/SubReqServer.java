package my.netty.Authoritative.guide.chapter8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SubReqServer {

	public void bind(int port) throws Exception {
		// 配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();// NIO服务端的辅助启动类
			b.group(bossGroup, workerGroup)// 设置Reactor线程组
					.channel(NioServerSocketChannel.class)// 设置Channel类型
					.option(ChannelOption.SO_BACKLOG, 1024)// 设置TCP参数backlog
					.handler(new LoggingHandler(LogLevel.INFO))// 绑定日志事件处理类
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// ProtobufVarint32FrameDecoder主要用于半包处理
							ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
							// ProtobufDecoder解码器，不支持读半包。因此在ProtobufDecoder前一定要有能够处理读半包的解码器
							//1.使用Netty提供的ProtobufVarint32FrameDecoder；
							//2.继承Netty提供的通用半包解码器ProtobufFieldBasedFrameDecoder
							//3.继承BytetoMessageDecoder类，自己处理半包消息
							ch.pipeline().addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));
							// 业务逻辑处理Handler
							ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
							ch.pipeline().addLast(new ProtobufEncoder());
							ch.pipeline().addLast(new SubReqServerHandler());
						}
					});
			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(port).sync();
			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} finally {
			// 优雅退出
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		new SubReqServer().bind(port);

	}

}
