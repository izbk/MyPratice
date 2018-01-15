package my.netty.Authoritative.guide.chapter10.json;

import java.util.List;  

import io.netty.channel.ChannelHandlerContext;  
import io.netty.handler.codec.http.FullHttpResponse;  
  
public class HttpJsonResponseDecoder extends AbsHttpJsonDecoder<FullHttpResponse> {  
  
    protected HttpJsonResponseDecoder(Class<?> clazz) {  
        super(clazz);  
    }  
  
    @Override  
    protected void decode(ChannelHandlerContext ctx, FullHttpResponse response, List<Object> out) throws Exception {  
        HttpJsonResponse res = new HttpJsonResponse(response, super.decode(ctx, response.content()));  
        out.add(res);  
    }  
  
}
