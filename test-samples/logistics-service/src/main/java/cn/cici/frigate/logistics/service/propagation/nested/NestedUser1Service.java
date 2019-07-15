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
@Service
public class NestedUser1Service {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.NESTED)
    public void addNested(String method) {
        String name = method + "张三";
        jdbcTemplate.execute("insert into user1(name) values('" + name + "')");
        System.out.println("USER1 插入成功");
    }
}
