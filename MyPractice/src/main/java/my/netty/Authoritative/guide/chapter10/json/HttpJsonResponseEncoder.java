package my.netty.Authoritative.guide.chapter10.json;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;  
  
public class HttpJsonResponseEncoder extends AbsHttpJsonEncoder<HttpJsonResponse> {  
  
    @Override  
    protected void encode(ChannelHandlerContext ctx, HttpJsonResponse message,  
                          List<Object> out) throws Exception {  
        ByteBuf body = encode(ctx, message.getResult());  
        FullHttpResponse response = message.getHttpResponse();  
        if (response == null) {  
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);  
        } else {  
            response = new DefaultFullHttpResponse(message.getHttpResponse().protocolVersion(), message.getHttpResponse().status(), body);  
        }  
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json;charset=UTF-8");  
        HttpUtil.setContentLength(response, body.readableBytes());  
        out.add(response);  
    }  
  
}  
