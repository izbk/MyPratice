package my.netty.demo.restserver.demo.resource;


import java.util.List;

import my.netty.demo.restserver.demo.entity.User;
import my.netty.demo.restserver.demo.service.UserService;
import my.netty.demo.restserver.nettyrest.ApiProtocol;
import my.netty.demo.restserver.nettyrest.BaseResource;
import my.netty.demo.restserver.nettyrest.response.ListInfo;
import my.netty.demo.restserver.nettyrest.response.ListResult;
import my.netty.demo.restserver.nettyrest.response.Result;

public class UsersResource extends BaseResource {

    public UsersResource(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }
    @SuppressWarnings("rawtypes")
    public Result get() {

        ListInfo   info       = new ListInfo();
        ListResult listResult = new ListResult(info);

        UserService userService = new UserService(apiProtocol);

        List<User> list = userService.list();

        info.setNum(list.size());
        listResult.setItem(list);

        return listResult;
    }
}
