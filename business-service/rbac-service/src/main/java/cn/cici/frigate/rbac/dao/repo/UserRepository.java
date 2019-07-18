package cn.cici.frigate.rbac.dao.repo;

import cn.cici.frigate.rbac.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:
 * @createDate:2019/7/9$14:19$
 * @author: Heyfan Xie
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 根据邮箱查询
     * @param email
     * @return
     */
    User findByEmail(String email);
}
