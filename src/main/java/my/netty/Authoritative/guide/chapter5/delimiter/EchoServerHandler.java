package my.netty.Authoritative.guide.chapter5.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 对网络事件进行读写
 * 
 * @author F
 *
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	private int counter;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("This is " + ++counter + " times receive client: [" + body + "]");
		body += "$_";
		ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
		ctx.writeAndFlush(echo);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();// 将消息发送队列中的消息写入到SocketChannel中发送给对方
	}

	/**
	 * 发生异常时，释放ctx相关的资源
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}
}
