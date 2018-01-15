package my.netty.Authoritative.guide.chapter10.xml;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HttpXmlClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		// 给客户端发送请求消息，HttpXmlRequest包含FullHttpRequest和Order这个了类
		HttpXmlRequest request = new HttpXmlRequest(null, OrderFactory.create(123));
		ctx.writeAndFlush(request);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.out.println(cause.getMessage());
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
		HttpXmlResponse response = (HttpXmlResponse)o;
		System.out.println("The client receive response of http body is : " + response.getResult());

	}
}
