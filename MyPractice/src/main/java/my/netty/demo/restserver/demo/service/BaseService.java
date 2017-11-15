package my.netty.demo.restserver.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.netty.demo.restserver.nettyrest.ApiProtocol;

public class BaseService {

    protected ApiProtocol apiProtocol;
    protected Logger logger;

    public BaseService(ApiProtocol apiProtocol) {
        this.apiProtocol = apiProtocol;
        logger = LoggerFactory.getLogger(this.getClass());
    }
}
