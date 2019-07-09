package cn.cici.frigate.rbac.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author xiehf
 * @date 2019/6/15 23:39
 * @concat 370693739@qq.com
 **/
@Repository
public class User1JdbcTemplate{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void save(String method) {
        String name = method + "张三";
        jdbcTemplate.execute("insert into user1(name) values(" + name + ")");
    }
}
