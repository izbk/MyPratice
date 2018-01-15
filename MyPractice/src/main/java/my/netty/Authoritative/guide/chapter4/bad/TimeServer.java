package my.netty.Authoritative.guide.chapter4.bad;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

	public void bind(int port) throws Exception{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();//NIO服务端的辅助启动类
			b.group(bossGroup, workerGroup)//设置Reactor线程组
			.channel(NioServerSocketChannel.class)//设置Channel类型
			.option(ChannelOption.SO_BACKLOG, 1024)//设置TCP参数backlog
			.childHandler(new ChildChannelHandler());//绑定I/O事件处理类
			
			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();//等待服务器关闭后main函数才退出
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			arg0.pipeline().addLast(new TimeServerHandler());
			
		}
		
	}
	public static void main(String[] args) throws Exception {
		int port;
		if(args.length > 0){
			port = Integer.parseInt(args[0]);
		}else{
			port = 8080;
		}
		new TimeServer().bind(port);

	}

}
