package my.netty.Authoritative.guide.chapter10.json;

import io.netty.handler.codec.http.FullHttpResponse;  

public class HttpJsonResponse {  
      
    private FullHttpResponse httpResponse;  
    private Object result;  
      
    public HttpJsonResponse(FullHttpResponse httpResponse, Object result) {  
        super();  
        this.httpResponse = httpResponse;  
        this.result = result;  
    }  
    public FullHttpResponse getHttpResponse() {  
        return httpResponse;  
    }  
    public void setHttpResponse(FullHttpResponse httpResponse) {  
        this.httpResponse = httpResponse;  
    }  
    public Object getResult() {  
        return result;  
    }  
    public void setResult(Object result) {  
        this.result = result;  
    }  
}  
