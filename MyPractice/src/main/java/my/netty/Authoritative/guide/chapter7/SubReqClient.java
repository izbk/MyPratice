package my.netty.Authoritative.guide.chapter7;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class SubReqClient {
	public void connect(int port, String host) throws Exception {
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel arg0) throws Exception {
							arg0.pipeline()
									.addLast(new ObjectDecoder(1024,
											// 禁止对类加载器进行缓存，它在基于OSGI的动态模块化编程中经常使用
											ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
							arg0.pipeline().addLast(new ObjectEncoder());
							arg0.pipeline().addLast(new SubReqClientHandler());
						}

					});

			ChannelFuture f = b.connect(host, port).sync();// 发起异步操作连接

			f.channel().closeFuture().sync();// 等待客户端链路关闭
		} finally {
			group.shutdownGracefully();
		}

	}

	public static void main(String[] args) throws Exception {
		int port = 8080;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (Exception e) {
			}
		}
		new SubReqClient().connect(port, "127.0.0.1");
	}

}
