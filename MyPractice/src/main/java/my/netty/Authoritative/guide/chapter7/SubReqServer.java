package my.netty.Authoritative.guide.chapter7;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
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
							// ObjectDecoder负责对实现Serializable的POJO对象进行解码
							// weakCachingConcurrentResolver创建线程安全的WeakReferenceMap对类加载器进行缓存。
							// 为了防止异常码流和解码错位导致的内存溢出，设置字节数据的长度为1M。
							ch.pipeline().addLast(new ObjectDecoder(2014 * 1024,
									ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
							// ObjectEncoder可以在消息发送的时候自动将实现Serializable的POJO对象进行编码
							// 对象序列化和反序列化都有Netty的对象编解码器搞定。
							ch.pipeline().addLast(new ObjectEncoder());
							// 业务逻辑处理Handler
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
