package my.netty.Authoritative.guide.chapter10.xml;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;  
  
public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse> {  
  
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> out) throws Exception {  
        ByteBuf body = encode0(ctx, msg.getResult());  
        FullHttpResponse response = msg.getHttpResponse();  
        if (response == null) {  
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, body);  
        } else {  
            response = new DefaultFullHttpResponse(msg.getHttpResponse().protocolVersion(),  
                    msg.getHttpResponse().status(), body);  
        }  
        response.headers().set(CONTENT_TYPE, "text/xml");  
        response.headers().setInt(CONTENT_LENGTH, body.readableBytes());  
        out.add(response);  
    }  
}  
