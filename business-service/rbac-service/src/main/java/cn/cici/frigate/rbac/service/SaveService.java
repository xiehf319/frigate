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
 * @createDate:2019/7/9$14:02$
 * @author: Heyfan Xie
 */
@Service
public class SaveService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public void addUser() {
        User user7 = new User();
        user7.setUsername("user7");
        user7.setPassword("pwd7");

        userRepository.save(user7);

        User user8 = new User();
        user8.setUsername("user8");
        user8.setPassword("pwd8");

        userRepository.save(user8);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addRole() {
        Role role7 = new Role();
        role7.setName("role7");
        roleRepository.save(role7);

        Role role8= new Role();
        role8.setName("role8");
        roleRepository.save(role8);
        throw new RuntimeException("抛出异常");
    }
}
