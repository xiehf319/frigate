package cn.cici.frigate.rbac.jpa;

import cn.cici.frigate.rbac.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:
 * @createDate:2019/5/9$13:38$
 * @author: Heyfan Xie
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
