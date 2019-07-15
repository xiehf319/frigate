package cn.cici.frigate.logistics.service.propagation.nested;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @createDate:2019/6/14$13:55$
 * @author: Heyfan Xie
 */
@SuppressWarnings("ALL")
@Service
public class NestedUser2Service {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.NESTED)
    public void addNested(String method) {
        String name = method + "李四";
        jdbcTemplate.execute("insert into user2(name) values('" + name + "')");
        System.out.println("USER2 插入成功");

    }


    @Transactional(propagation = Propagation.NESTED)
    public void addNestedException(String method) {
        String name = method + "李四";
        jdbcTemplate.execute("insert into user2(name) values('" + name + "')");
        System.out.println("USER2 插入成功");
        throw new RuntimeException();
    }
}
