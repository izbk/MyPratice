package my.netty.Authoritative.guide.chapter10.xml;

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
import io.netty.handler.codec.http.HttpVersion;  
  
public class HttpXmlRequestEncoder extends AbstractHttpXmlEncoder<HttpXmlRequest> {  
  
    @Override  
    protected void encode(ChannelHandlerContext ctx, HttpXmlRequest msg, List<Object> out) throws Exception {  
        // 调用父类的encode0方法将Order对象转换为xml字符串，并将其封装为ByteBuf  
        ByteBuf body = encode0(ctx, msg.getBody());  
        FullHttpRequest request = msg.getRequest();  
        // 如request为空，则新建一个FullHttpRequest对象，并将设置消息头  
        if (request == null) {  
            // 在构造方法中，将body设置为请求消息体  
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/do", body);  
            HttpHeaders headers = request.headers();  
            headers.set(HttpHeaderNames.HOST, InetAddress.getLocalHost().getHostAddress());  
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);  
            headers.set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP.toString() + "," + HttpHeaderValues.DEFLATE.toString());  
        }  
        // 由于此处没有使用chunk方式，所以要设置消息头中设置消息体的CONTENT_LENGTH  
        request.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, body.readableBytes());  
        // 将请求消息添加进out中，待后面的编码器对消息进行编码  
        out.add(request);  
    }  
  
}  
