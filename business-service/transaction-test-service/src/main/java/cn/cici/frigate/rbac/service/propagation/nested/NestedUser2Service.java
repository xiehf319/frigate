package cn.cici.frigate.rbac.service.propagation.nested;

import cn.cici.frigate.rbac.jpa.User2JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
    private User2JdbcTemplate user2JdbcTemplate;

    @Transactional(propagation = Propagation.NESTED)
    public void addNested(String method) {
        user2JdbcTemplate.save(method);
    }


    @Transactional(propagation = Propagation.NESTED)
    public void addNestedException(String method) {
        user2JdbcTemplate.save(method);
        throw new RuntimeException();
    }
}
