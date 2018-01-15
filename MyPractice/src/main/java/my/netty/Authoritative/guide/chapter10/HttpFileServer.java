package my.netty.Authoritative.guide.chapter10;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {
	private static final String DEFAULT_URL = "/src/main/java/org/test/Authoritative/guide/";
	
	public void run(final String host,final int port,final String url) throws Exception {
		// 配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();// NIO服务端的辅助启动类
			b.group(bossGroup, workerGroup)// 设置Reactor线程组
					.channel(NioServerSocketChannel.class)// 设置Channel类型
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							//HTTP请求消息解码器
							ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
							//HttpObjectAggregator解码器，其作用是将多个消息转换为单一的FullHttpRequest或者FullHttpResponse，
							//原因是HTTP解码器在每个HTTP消息中会生成多个消息对象。1.HttpRequest/HttpResponse.2.HttpContent.3.LastHttpContent.
							ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
							//Http响应编码器，对Http响应消息进行编码。
							ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
							//Chunked handler主要作用是支持异步发送大的码流，但不占用过多的内存，防止发生Java内存溢出错误。
							ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
							//文件服务器的业务逻辑处理
							ch.pipeline().addLast("fileServerHandler",new HttpFileServerHandler(url));
						}
					});
			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(host,port).sync();
			System.out.println("HTTP 文件目录服务器启动，网址是："+"http://"+host+":"+port+url);
			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} finally {
			// 优雅退出
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		String host = "localhost";
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		new HttpFileServer().run(host,port,DEFAULT_URL);

	}

}
