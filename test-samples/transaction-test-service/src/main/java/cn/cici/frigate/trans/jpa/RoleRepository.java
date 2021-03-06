package cn.cici.frigate.trans.jpa;

import cn.cici.frigate.trans.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:
 * @createDate:2019/5/9$13:40$
 * @author: Heyfan Xie
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

}
