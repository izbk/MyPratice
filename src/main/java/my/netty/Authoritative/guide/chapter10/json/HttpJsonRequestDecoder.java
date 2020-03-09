package my.netty.Authoritative.guide.chapter10.json;

import java.util.List;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;  
  
public class HttpJsonRequestDecoder extends AbsHttpJsonDecoder<FullHttpRequest> {  
  
    public HttpJsonRequestDecoder(Class<?> clazz) {  
        super(clazz);  
    }  
  
    @Override  
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest request, List<Object> out) throws Exception {  
        HttpJsonRequest req = null;  
  
        if (!request.decoderResult().isSuccess()) {  
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);  
            return;  
        }  
        try {  
            req = new HttpJsonRequest(request, decode(ctx, request.content()));  
        } catch (Exception e) {  
            if (e.getMessage() != null) {  
                req = new HttpJsonRequest(request, e.getMessage());  
            } else {  
                req = new HttpJsonRequest(request, e.getCause());  
            }  
        }  
        out.add(req);  
    }  
  
    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {  
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));  
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");  
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);  
    }  
  
} 
