package my.netty.demo.restserver.demo.resource;

import my.netty.demo.restserver.demo.bo.AlbumInfo;
import my.netty.demo.restserver.demo.service.AlbumService;
import my.netty.demo.restserver.nettyrest.ApiProtocol;
import my.netty.demo.restserver.nettyrest.BaseResource;
import my.netty.demo.restserver.nettyrest.response.Info;
import my.netty.demo.restserver.nettyrest.response.Result;

public class AlbumResource extends BaseResource {

    public AlbumResource(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }
    @SuppressWarnings("rawtypes")
    public Result get() {

        int uid, aid;

        Object uidCheck = parameterIntCheck(apiProtocol, "uid");
        if (uidCheck instanceof Result) {
            return (Result) uidCheck;
        } else {
            uid = (int) uidCheck;
        }

        Object aidCheck = parameterIntCheck(apiProtocol,"aid");
        if (aidCheck instanceof Result){
            return (Result) aidCheck;
        }else {
            aid = (int) aidCheck;
        }

        AlbumService albumService = new AlbumService(apiProtocol);

        return new Result<Info>(new AlbumInfo(albumService.get(aid,uid)));
    }
}
