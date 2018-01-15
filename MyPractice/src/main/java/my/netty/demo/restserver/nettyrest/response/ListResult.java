package my.netty.demo.restserver.nettyrest.response;

import java.util.List;

import my.netty.demo.restserver.nettyrest.BaseEntity;

@SuppressWarnings("rawtypes")
public class ListResult extends Result {

    protected List<? extends BaseEntity> item;

    public List<? extends BaseEntity> getItem() {
        return item;
    }

    public void setItem(List<? extends BaseEntity> item) {
        this.item = item;
    }

    @SuppressWarnings("unchecked")
	public ListResult(Info info) {
        super(info);
    }
}
