package my.netty.Authoritative.guide.chapter10.json;

import java.nio.charset.Charset;

import com.google.gson.Gson;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;  
  
public abstract class AbsHttpJsonEncoder<T> extends MessageToMessageEncoder<T> {  
  
    private final static String CHARSET_NAME = "UTF-8";  
    private static Charset UTF_8 = Charset.forName(CHARSET_NAME);  
  
    protected ByteBuf encode(ChannelHandlerContext ctx, Object body) throws Exception {  
          
        String jsonStr = new Gson().toJson(body);  
        ByteBuf encodeBuf = Unpooled.copiedBuffer(jsonStr, UTF_8);  
        return encodeBuf;  
    }  
}  
