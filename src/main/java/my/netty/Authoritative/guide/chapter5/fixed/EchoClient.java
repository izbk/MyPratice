package my.netty.Authoritative.guide.chapter5.fixed;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {
	public void connect(int port, String host) throws Exception {
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel arg0) throws Exception {
							ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
							arg0.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
							arg0.pipeline().addLast(new StringDecoder());
							arg0.pipeline().addLast(new EchoClientHandler());
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
		new EchoClient().connect(port, "127.0.0.1");
	}

}
