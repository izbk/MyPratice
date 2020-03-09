package my.netty.Authoritative.guide.chapter7;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

	public SubReqClientHandler() {

	}

	/**
	 * 客户端和服务端建立TCP连接成功，Netty的Nio线程会调用channelActive方法，发送查询时间的指令给服务端。
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 10; i++) {
			ctx.write(subReq(i));
		}
		ctx.flush();
	}

	private SubscribeReq subReq(int i) {
		SubscribeReq req = new SubscribeReq();
		req.setAddress("南京");
		req.setPhoneNumber("123467783");
		req.setProductName("Netty 权威指南");
		req.setSubReqID(i);
		req.setUserName("Lilinfeng");
		return req;
	}

	/**
	 * 服务端返回应答消息时channelRead被调用
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Receive server response:[" + msg + "]");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	/**
	 * 发生异常时，释放客户端资源
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext arg0, Throwable arg1) throws Exception {
		arg0.close();

	}

}
