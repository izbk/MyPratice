package my.netty.Authoritative.guide.chapter10.json;

import java.net.InetAddress;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;  
  
public class HttpJsonRequestEncoder extends AbsHttpJsonEncoder<HttpJsonRequest> {  
  
    @Override  
    protected void encode(ChannelHandlerContext ctx, HttpJsonRequest message, List<Object> out) throws Exception {  
  
  
        ByteBuf body = encode(ctx, message.getBody());  
        FullHttpRequest request = message.getRequest();  
  
        if (request == null) {  
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/do", body);  
            HttpHeaders headers = request.headers();  
            headers.set(HttpHeaderNames.HOST, InetAddress.getLocalHost().getHostAddress());  
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);  
            headers.set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP.toString() + "," + HttpHeaderValues.DEFLATE.toString());  
        }  
        HttpUtil.setContentLength(request, body.readableBytes());  
        out.add(request);  
    }  
  
} 
