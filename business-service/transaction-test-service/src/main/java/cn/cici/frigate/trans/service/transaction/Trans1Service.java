package cn.cici.frigate.trans.service.transaction;

import cn.cici.frigate.trans.dao.entity.Role;
import cn.cici.frigate.trans.dao.entity.User1;
import cn.cici.frigate.trans.jpa.RoleRepository;
import cn.cici.frigate.trans.jpa.User1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @createDate:2019/5/9$13:50$
 * @author: Heyfan Xie
 */
@Service
public class Trans1Service {

    @Autowired
    private Trans1Service trans1Service;

    @Autowired
    private User1Repository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void add() {
        System.out.println(this);
        trans1Service.addUser();
        trans1Service.addRole();
    }

    @Transactional(rollbackFor = Exception.class)
    public void addUser() {
        User1 user3 = new User1();
        user3.setName("user3");

        userRepository.save(user3);

        User1 user4 = new User1();
        user4.setName("user4");

        userRepository.save(user4);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addRole() {
        Role role2 = new Role();
        role2.setName("role2");
        roleRepository.save(role2);

        Role role3 = new Role();
        role2.setName("role3");
        roleRepository.save(role3);
        throw new RuntimeException("抛出异常");
    }

}
