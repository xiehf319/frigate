package cn.cici.frigate.trans.service.propagation.notsupport;

import cn.cici.frigate.trans.dao.entity.User1;
import cn.cici.frigate.trans.jpa.User1Repository;
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
public class NotSupportUser1Service {

    @Autowired
    private User1Repository user1Repository;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addNotSupport(String method) {
        User1 user = new User1();
        user.setName(method + "张三");
        user1Repository.save(user);
    }
}
