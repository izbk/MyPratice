package my.netty.demo.restserver.demo.dao;

import java.util.List;

import my.netty.demo.restserver.demo.entity.UserLite;
import my.netty.demo.restserver.nettyrest.mysql.MySelect;
import my.netty.demo.restserver.nettyrest.mysql.Mysql;

public class UserDao {

    /**
     * 单独获取用户的名称
     * 面向过程的方式
     * 仅仅为演示其 Mysql 类的使用方便，不推荐这种方式来获取结果
     *
     * @param id
     * @return
     */
    public String getName(int id){
        String sql = "select name from user where id=?";
        return Mysql.getValue(sql, id);
    }

    @SuppressWarnings("rawtypes")
	public UserLite get(int id){
        MySelect mySelect = new MySelect<>(new UserLite());
        String sql = "select id,name,icon,birthday from user where id=?";
        return (UserLite) mySelect.get(sql, id);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<UserLite> list(){
        MySelect mySelect = new MySelect<>(new UserLite());
        String sql = "select id,name,icon,birthday from user limit 10";
        return mySelect.list(sql);
    }

}
