package cn.cici.frigate.rbac.service.propagation.requirednew;

import cn.cici.frigate.rbac.dao.entity.User1;
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
public class RequiredNewUser1Service {

    @Autowired
    private User1Repository user1Repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequriedNew(String method) {
        User1 user = new User1();
        user.setName(method + "张三");
        user1Repository.save(user);
    }
}
