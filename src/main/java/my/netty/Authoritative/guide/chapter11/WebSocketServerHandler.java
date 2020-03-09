package my.netty.Authoritative.guide.chapter11;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import my.netty.Authoritative.guide.chapter10.HttpUrlKit;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object>{
	
	private WebSocketServerHandshaker handshaker;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 传统的HTTP接入
        //第一次握手请求消息由HTTP协议承载，所以它是一个HTTP消息，执行handleHttpRequest方法来处理WebSocket握手请求。
		if(msg instanceof FullHttpRequest){
			handleHttpRequest(ctx,(FullHttpRequest)msg);
		}else{
			// WebSocket接入
	        // 客户端通过文本框提交请求消息给服务端，WebSocketServerHandler接收到的是已经解码后的WebSocketFrame消息。
			handleWebSocketFrame(ctx,(WebSocketFrame)msg);
		}
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx){
		ctx.flush();
	}
	
	private void handleHttpRequest(ChannelHandlerContext ctx,FullHttpRequest req) throws Exception{
		// 如果HTTP解码失败，返回HTTP异常
        // 首先对握手请求消息进行判断，如果消息头中没有包含Upgrade字段或者它的值不是websocket，则返回HTTP 400响应。
		if(!req.decoderResult().isSuccess()||(!"websocket".equals(req.headers().get("Upgrade")))){
			sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}
		// 构造握手响应返回
        // 握手请求简单校验通过之后，开始构造握手工厂，创建握手处理类WebSocketServerHandshaker，
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket",null,false);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			// 通过它构造握手响应消息返回给客户端，
            // 同时将WebSocket相关的编码和解码类动态添加到ChannelPipeline中，用于WebSocket消息的编解码，
            // 添加WebSocketEncoder和WebSocketDecoder之后，服务端就可以自动对WebSocket消息进行编解码了
			handshaker.handshake(ctx.channel(), req);
		}
	}
	
	private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
		// 判断是否是关闭链路的指令
        // 首先需要对控制帧进行判断，如果是关闭链路的控制消息
        // 就调用WebSocketServerHandshaker的close方法关闭WebSocket连接；
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame)frame.retain());
			return;
		}
		// 判断是否是Ping消息
        // 如果是维持链路的Ping消息，则构造Pong消息返回。
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
		}
		// 本例程仅支持文本消息，不支持二进制消息
        // WebSocket通信双方使用的都是文本消息，所以对请求消息的类型进行判断，不是文本的抛出异常。
		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
		}
		// 返回应答消息
        // 从TextWebSocketFrame中获取请求消息字符串，
		String request = ((TextWebSocketFrame)frame).text();
		// 对它处理后通过构造新的TextWebSocketFrame消息返回给客户端，
        // 由于握手应答时动态增加了TextWebSocketFrame的编码类，所以，可以直接发送TextWebSocketFrame对象。
		ctx.channel().write(new TextWebSocketFrame(request+", 欢迎使用Netty WebSocket 服务，现在时刻："+new Date().toString()));
	}
	
	private static void sendHttpResponse(ChannelHandlerContext ctx,FullHttpRequest req,FullHttpResponse res){
		// 返回应答给客户端
		if(res.status().code() != 200){
			ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(),CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			HttpUtil.setContentLength(res, res.content().readableBytes());
		}
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		// 如果不是Keep-Alive，关闭连接
		if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if (ctx.channel().isActive()) {
			HttpUrlKit.sendError(ctx, INTERNAL_SERVER_ERROR);
		}
	}

}
