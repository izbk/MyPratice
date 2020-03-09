package my.netty.Authoritative.guide.chapter10.json;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HttpJsonClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		// 给客户端发送请求消息，HttpXmlRequest包含FullHttpRequest和User这个了类
		User user = new User();
		user.setName("肥牛");
		HttpJsonRequest request = new HttpJsonRequest(null, user);
		ctx.writeAndFlush(request);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.out.println(cause.getMessage());
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
		HttpJsonResponse response = (HttpJsonResponse)o;
		System.out.println("The client receive response of http body is : " + response.getResult());

	}
}
