package cn.cici.frigate.rbac.service;

import cn.cici.frigate.rbac.dao.entity.Role;
import cn.cici.frigate.rbac.dao.entity.User;
import cn.cici.frigate.rbac.jpa.RoleRepository;
import cn.cici.frigate.rbac.jpa.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void add() {
        addUser();
        addRole();
    }

    @Transactional(rollbackFor = Exception.class)
    void addUser() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("pwd1");

        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("pwd2");

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
