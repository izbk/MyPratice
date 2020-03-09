package my.netty.Authoritative.guide.chapter10;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpUtil.setContentLength;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpChunkedInput;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;

/**
 * 对网络事件进行读写
 * 
 * @author F
 *
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private final String url;

	public HttpFileServerHandler(String url) {
		super();
		this.url = url;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		// 如果出现编码错误，返回400错误
		if (!request.decoderResult().isSuccess()) {
			HttpUrlKit.sendError(ctx, BAD_REQUEST);
			return;
		}
		// 如果不是浏览器或者表单发送get请求，跳转405错误
		if (request.method() != GET) {
			HttpUrlKit.sendError(ctx, METHOD_NOT_ALLOWED);
			return;
		}
		final String uri = request.uri();
		// 对具体的包装具体的url路径
		final String path = HttpUrlKit.sanitizeUri(uri, url);
		if (path == null) {
			HttpUrlKit.sendError(ctx, FORBIDDEN);
			return;
		}
		// 获取文件对象
		File file = new File(path);
		// 文件属于隐藏文件或者不存在
		if (file.isHidden() || !file.exists()) {
			HttpUrlKit.sendError(ctx, NOT_FOUND);
			return;
		}
		// 查看路径名表示的是否是一个目录，如果是，则发送目录的链接给客户端
		if (file.isDirectory()) {
			if (uri.endsWith("/")) {
				HttpUrlKit.sendListing(ctx, file);
			} else {
				HttpUrlKit.sendRedirect(ctx, uri + "/");
			}
			return;
		}
		// 查看路径名表示的文件是否是一个标准文件
		if (!file.isFile()) {
			HttpUrlKit.sendError(ctx, FORBIDDEN);
			return;
		}
		// 构建随机访问文件的读取和写入
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e) {
			HttpUrlKit.sendError(ctx, NOT_FOUND);
			return;
		}

		long fileLength = raf.length();  
		  
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);  
        setContentLength(response, fileLength);  
        HttpUrlKit.setContentTypeHeader(response, file);  
        if (isKeepAlive(request)) {  
            response.headers().set(CONNECTION, "keep-alive");
        }  
		// Write the initial line and the header.  
        ctx.write(response);  
  
        // Write the content.  
        ChannelFuture sendFileFuture;  
        ChannelFuture lastContentFuture;  
        if (ctx.pipeline().get(SslHandler.class) == null) {  
            sendFileFuture =  
                    ctx.write(new DefaultFileRegion(raf.getChannel(), 0, fileLength), ctx.newProgressivePromise());  
            // Write the end marker.  
            lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);  
        } else {  
            sendFileFuture =  
                    ctx.writeAndFlush(new HttpChunkedInput(new ChunkedFile(raf, 0, fileLength, 8192)),  
                            ctx.newProgressivePromise());  
            // HttpChunkedInput will write the end marker (LastHttpContent) for us.  
            lastContentFuture = sendFileFuture;  
        }  
  
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override  
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {  
                if (total < 0) { // total unknown  
                    System.err.println(future.channel() + " Transfer progress: " + progress);  
                } else {  
                    System.err.println(future.channel() + " Transfer progress: " + progress + " / " + total);  
                }  
            }  
  
            @Override  
            public void operationComplete(ChannelProgressiveFuture future) {  
                System.err.println(future.channel() + " Transfer complete.");  
            }  
        });  
  
        // Decide whether to close the connection or not.  
        if (!isKeepAlive(request)) {
            // Close the connection when the whole content is written out.  
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);  
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
