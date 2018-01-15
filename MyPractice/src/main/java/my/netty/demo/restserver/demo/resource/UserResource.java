package my.netty.demo.restserver.demo.resource;

import my.netty.demo.restserver.demo.bo.UserCreateSuccess;
import my.netty.demo.restserver.demo.bo.UserInfo;
import my.netty.demo.restserver.demo.service.UserService;
import my.netty.demo.restserver.nettyrest.ApiProtocol;
import my.netty.demo.restserver.nettyrest.BaseResource;
import my.netty.demo.restserver.nettyrest.StatusCode;
import my.netty.demo.restserver.nettyrest.response.Info;
import my.netty.demo.restserver.nettyrest.response.Result;

public class UserResource extends BaseResource {

    public UserResource(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }
    @SuppressWarnings("rawtypes")
    public Result get() {

        int uid;

        Object uidCheck = parameterIntCheck(apiProtocol, "uid");
        if (uidCheck instanceof Result) {
            return (Result) uidCheck;
        } else {
            uid = (int) uidCheck;
        }

        UserService userService = new UserService(apiProtocol);
        UserInfo    userInfo    = new UserInfo(userService.get(uid));

        return new Result<Info>(userInfo);
    }
    @SuppressWarnings("rawtypes")
    public Result post() {
        UserCreateSuccess userCreateSuccess = new UserCreateSuccess();
        userCreateSuccess.setId(2);
        userCreateSuccess.setUrl("http://netty.restful.api.mengkang.net/user/2");
        userCreateSuccess.setCode(StatusCode.CREATED_SUCCESS);
        return new Result<>(userCreateSuccess);
    }
    @SuppressWarnings("rawtypes")
    public Result patch() {
        return success(202);
    }
    @SuppressWarnings("rawtypes")
    public Result delete() {
        return success(203);
    }

}
