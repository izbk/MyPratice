package my.netty.demo.restserver.demo.bo;

import my.netty.demo.restserver.demo.entity.User;
import my.netty.demo.restserver.nettyrest.response.Info;

/**
 * Created by zhoumengkang on 5/1/16.
 */
public class UserInfo extends Info {
    private User user;

    public User getUser() {
        return user;
    }

    public UserInfo(User user) {
        this.user = user;
    }
}