package cn.cici.frigate.rbac.service.transaction;

import cn.cici.frigate.rbac.dao.entity.Role;
import cn.cici.frigate.rbac.dao.entity.User1;
import cn.cici.frigate.rbac.jpa.RoleRepository;
import cn.cici.frigate.rbac.jpa.User1Repository;
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
    private User1Repository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public void addUser() {
        User1 user7 = new User1();
        user7.setName("user7");

        userRepository.save(user7);

        User1 user8 = new User1();
        user8.setName("user8");

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
