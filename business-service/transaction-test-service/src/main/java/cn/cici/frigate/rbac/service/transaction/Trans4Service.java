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
 * @createDate:2019/5/9$13:50$
 * @author: Heyfan Xie
 */
@Service
public class Trans4Service implements ITrans4Service {

    @Autowired
    private ITrans4Service trans4Service;

    @Autowired
    private User1Repository userRepository;

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
        User1 user3 = new User1();
        user3.setName("user3");

        userRepository.save(user3);

        User1 user4 = new User1();
        user4.setName("user4");

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
