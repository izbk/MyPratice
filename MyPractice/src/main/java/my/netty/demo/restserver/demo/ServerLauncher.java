package my.netty.demo.restserver.demo;

import my.netty.demo.restserver.nettyrest.netty.NettyRestServer;

/**
 * Created by zhoumengkang on 7/1/16.
 */
public class ServerLauncher {
    public static void main(String[] args) {
        NettyRestServer nettyRestServer = new NettyRestServer();
        nettyRestServer.start();
    }
}
