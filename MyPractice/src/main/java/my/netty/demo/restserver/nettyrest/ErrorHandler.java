package my.netty.demo.restserver.nettyrest;

import my.netty.demo.restserver.nettyrest.response.Info;
import my.netty.demo.restserver.nettyrest.response.Result;


public class ErrorHandler {

    @SuppressWarnings("rawtypes")
    public static Result error(int errorCode) {
        Result result = new Result<>(new Info());
        result.getInfo().setCode(errorCode).setCodeMessage(StatusCode.codeMap.get(errorCode));
        return result;
    }
    @SuppressWarnings("rawtypes")
    public static Result error(int errorCode,String parameter) {
        Result result = new Result<>(new Info());
        result.getInfo()
                .setCode(errorCode)
                .setCodeMessage(String.format(StatusCode.codeMap.get(errorCode), parameter));
        return result;
    }
}
