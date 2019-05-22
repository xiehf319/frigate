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
 * @createDate:2019/5/9$13:50$
 * @author: Heyfan Xie
 */
@Service
public class Trans4Service implements ITrans4Service {

    @Autowired
    private ITrans4Service trans4Service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void add() {
        System.out.println(this);
        trans4Service.addUser();
        trans4Service.addRole();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser() {
        User user3 = new User();
        user3.setUsername("user3");
        user3.setPassword("pwd3");

        userRepository.save(user3);

        User user4 = new User();
        user4.setUsername("user4");
        user4.setPassword("pwd4");

        userRepository.save(user4);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole() {
        Role role2 = new Role();
        role2.setName("role2");
        roleRepository.save(role2);

        Role role3= new Role();
        role2.setName("role3");
        roleRepository.save(role3);
        throw new RuntimeException("抛出异常");
    }

}
