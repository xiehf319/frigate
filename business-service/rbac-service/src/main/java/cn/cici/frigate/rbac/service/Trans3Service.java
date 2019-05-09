package cn.cici.frigate.rbac.service;

import cn.cici.frigate.rbac.dao.entity.Role;
import cn.cici.frigate.rbac.dao.entity.User;
import cn.cici.frigate.rbac.jpa.RoleRepository;
import cn.cici.frigate.rbac.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @createDate:2019/5/9$13:37$
 * @author: Heyfan Xie
 */
@Service
public class Trans3Service {

    @Autowired
    private SaveService saveService;

    public void add() {

        saveService.addUser();
        saveService.addRole();
    }

}
