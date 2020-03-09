package my.netty.Authoritative.guide.chapter10.json;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

public class HttpJsonServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
		HttpJsonRequest xmlRequest = (HttpJsonRequest)msg;
        User user = (User) xmlRequest.getBody();
        System.out.println("Http server receive request : " + user);
        user.setName("大肥牛");
        ctx.writeAndFlush(new HttpJsonResponse(null,user));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		// 在链路没有关闭并且出现异常的时候发送给客户端错误信息
		if (ctx.channel().isActive()) {
			sendError(ctx, INTERNAL_SERVER_ERROR);
		}
	}

	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status,
				Unpooled.copiedBuffer("失败: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

}

