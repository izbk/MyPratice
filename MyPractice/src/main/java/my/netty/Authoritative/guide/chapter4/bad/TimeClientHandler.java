package my.netty.Authoritative.guide.chapter4.bad;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
	private int counter;
	private final byte[] req;

	public TimeClientHandler() {
		req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
	}

	/**
	 * 客户端和服务端建立TCP连接成功，Netty的Nio线程会调用channelActive方法，发送查询时间的指令给服务端。
	 */
	@Override
	public void channelActive(ChannelHandlerContext arg0) throws Exception {
		ByteBuf message = null;
		for (int i = 0; i < 100; i++) {
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			arg0.writeAndFlush(message);
		}
		
	}
	/**
	 * 服务端返回应答消息时channelRead被调用
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("Now is:" + body + "; the counter is :"+ ++counter);

	}

	/**
	 * 发生异常时，释放客户端资源
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext arg0, Throwable arg1) throws Exception {
		arg0.close();

	}

}
