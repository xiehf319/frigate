package cn.cici.frigate.rbac.service.propagation.nested;

import cn.cici.frigate.rbac.dao.entity.User1;
import cn.cici.frigate.rbac.jpa.User1JdbcTemplate;
import cn.cici.frigate.rbac.jpa.User1Repository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private User1JdbcTemplate user1JdbcTemplate;

    @Transactional(propagation = Propagation.NESTED)
    public void addNested(String method) {
        user1JdbcTemplate.save(method);
    }
}
