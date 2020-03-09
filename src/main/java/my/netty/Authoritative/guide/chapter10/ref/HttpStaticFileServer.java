package my.netty.Authoritative.guide.chapter10.ref;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;  
  
public final class HttpStaticFileServer {  
  
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap b = new ServerBootstrap();  
            b.group(bossGroup, workerGroup)  
             .channel(NioServerSocketChannel.class)  
             .handler(new LoggingHandler(LogLevel.INFO))  
             .childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
						ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
						ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
						ch.pipeline().addLast("fileServerHandler",new HttpStaticFileServerHandler());
					}
				});
  
            Channel ch = b.bind(8080).sync().channel();  
            ch.closeFuture().sync();  
        } finally {  
            bossGroup.shutdownGracefully();  
            workerGroup.shutdownGracefully();  
        }  
    }  
}  
