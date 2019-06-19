package cn.cici.frigate.rbac.service.propagation.requirednew;

import cn.cici.frigate.rbac.dao.entity.User2;
import cn.cici.frigate.rbac.jpa.User2Repository;
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
public class RequiredNewUser2Service {

    @Autowired
    private User2Repository user2Repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequriedNew(String method) {
        User2 user = new User2();
        user.setName(method + "李四");
        user2Repository.save(user);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequriedNewException(String method) {
        User2 user = new User2();
        user.setName(method + "王五");
        user2Repository.save(user);
        throw new RuntimeException();
    }
}
