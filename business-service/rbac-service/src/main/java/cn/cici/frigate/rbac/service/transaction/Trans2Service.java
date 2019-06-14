package cn.cici.frigate.rbac.service.transaction;

import cn.cici.frigate.rbac.dao.entity.Role;
import cn.cici.frigate.rbac.dao.entity.User1;
import cn.cici.frigate.rbac.jpa.RoleRepository;
import cn.cici.frigate.rbac.jpa.User1Repository;
import cn.cici.frigate.rbac.jpa.User2Repository;
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
public class Trans2Service {

    @Autowired
    private User1Repository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void add() {
        addUser();
        addRole();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addUser() {
        User1 user5 = new User1();
        user5.setName("user5");

        userRepository.save(user5);

        User1 user6 = new User1();
        user6.setName("user6");

        userRepository.save(user6);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRole() {
        Role role5 = new Role();
        role5.setName("role5");
        roleRepository.save(role5);

        Role role6 = new Role();
        role6.setName("role6");
        roleRepository.save(role6);
        throw new RuntimeException("抛出异常");
    }
}
