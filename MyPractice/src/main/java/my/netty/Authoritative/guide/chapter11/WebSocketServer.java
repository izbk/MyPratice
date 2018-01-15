package my.netty.Authoritative.guide.chapter11;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
  
public class WebSocketServer {  
    public void run(final int port) throws Exception {  
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {  
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)  
                    .childHandler(new ChannelInitializer<SocketChannel>() {  
                        @Override  
                        protected void initChannel(SocketChannel ch) throws Exception {
                        	//首先添加HttpServerCodec，将请求和应答消息编码或者解码为HTTP消息；
                            ch.pipeline().addLast("http-codec", new HttpServerCodec());
                            //增加HttpObjectAggregator，它的目的是将HTTP消息的多个部分组合成一条完整的HTTP消息；
                            ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
                            //添加ChunkedWriteHandler，来向客户端发送HTML5文件，
                            //它主要用于支持浏览器和服务端进行WebSocket通信；
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            //增加WebSocket服务端handler。
                            ch.pipeline().addLast("handler", new WebSocketServerHandler());
                        }  
                    });
            ChannelFuture future = b.bind(new InetSocketAddress(port)).sync();
            System.out.println("WebSocket服务器启动，地址是 :  " + "ws://localhost:" + port);
            future.channel().closeFuture().sync();
        } finally {  
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }  
    }  
  
    public static void main(String[] args) throws Exception {  
        int port = 8080;
        if (args.length > 0) {  
            try {  
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {  
                e.printStackTrace();
            }  
        }  
        new WebSocketServer().run(port);
    }  
}
