package my.netty.demo.restserver.demo.entity;


import my.netty.demo.restserver.demo.bo.Star;
import my.netty.demo.restserver.nettyrest.BaseEntity;

public class Album extends BaseEntity{
    private int    id;
    private User   user;
    private String cover;
    private Star   star;

    public Album(int id, User user, String cover, Star star) {
        this.id = id;
        this.user = user;
        this.cover = cover;
        this.star = star;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Star getStar() {
        return star;
    }

    public void setStar(Star star) {
        this.star = star;
    }
}
