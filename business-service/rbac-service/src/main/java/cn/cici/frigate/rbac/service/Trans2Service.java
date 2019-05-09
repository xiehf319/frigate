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
public class Trans2Service {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void add() {
        addUser();
        addRole();
    }

    private void addUser() {
        User user5 = new User();
        user5.setUsername("user5");
        user5.setPassword("pwd5");

        userRepository.save(user5);

        User user6 = new User();
        user6.setUsername("user6");
        user6.setPassword("pwd6");

        userRepository.save(user6);
    }

    private void addRole() {
        Role role5 = new Role();
        role5.setName("role5");
        roleRepository.save(role5);

        Role role6 = new Role();
        role6.setName("role6");
        roleRepository.save(role6);
        throw new RuntimeException("抛出异常");
    }
}
