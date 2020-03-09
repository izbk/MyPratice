package my.netty.Authoritative.guide.chapter10.xml;

import io.netty.handler.codec.http.FullHttpRequest;
/**
 * 用于实现和协议栈的解耦
 * @author F
 *
 */
public class HttpXmlRequest {
	
	private FullHttpRequest request;
	private Object body;

	public HttpXmlRequest(FullHttpRequest request, Object body) {
		this.request = request;
		this.body = body;
	}

	public final FullHttpRequest getRequest() {
		return request;
	}

	public final void setRequest(FullHttpRequest request) {
		this.request = request;
	}

	public final Object getBody() {
		return body;
	}

	public final void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "HttpXmlRequest [request=" + request + ", body =" + body + "]";
	}
}

