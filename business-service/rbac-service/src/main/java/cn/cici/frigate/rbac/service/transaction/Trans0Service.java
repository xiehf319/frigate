package cn.cici.frigate.rbac.service.transaction;

import cn.cici.frigate.rbac.dao.entity.Role;
import cn.cici.frigate.rbac.dao.entity.User1;
import cn.cici.frigate.rbac.jpa.RoleRepository;
import cn.cici.frigate.rbac.jpa.User1Repository;
import cn.cici.frigate.rbac.jpa.User2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @createDate:2019/5/9$13:37$
 * @author: Heyfan Xie
 */
@Service
public class Trans0Service {

    @Autowired
    private User1Repository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void add() {
        addUser();
        addRole();
    }

    @Transactional(rollbackFor = Exception.class)
    void addUser() {
        User1 user1 = new User1();
        user1.setName("user1");

        userRepository.save(user1);

        User1 user2 = new User1();
        user2.setName("user2");

        userRepository.save(user2);
    }

    @Transactional(rollbackFor = Exception.class)
    void addRole() {
        Role role1 = new Role();
        role1.setName("role1");
        roleRepository.save(role1);

        Role role2= new Role();
        role2.setName("role2");
        roleRepository.save(role2);
        throw new RuntimeException("抛出异常");
    }
}
