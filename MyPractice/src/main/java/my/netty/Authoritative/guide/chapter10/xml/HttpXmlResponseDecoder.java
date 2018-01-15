package my.netty.Authoritative.guide.chapter10.xml;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

@SuppressWarnings("rawtypes")
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<FullHttpResponse> {

    public HttpXmlResponseDecoder(Class clazz) {
        this(clazz, false);
    }
	public HttpXmlResponseDecoder(Class clazz, boolean isPrintlog) {
        super(clazz, isPrintlog);
    }

	@Override
    protected void decode(ChannelHandlerContext ctx,FullHttpResponse response, List<Object> out) throws Exception {
        HttpXmlResponse resHttpXmlResponse = new HttpXmlResponse(response, decode0(
                ctx, response.content()));
        out.add(resHttpXmlResponse);
    }
}
