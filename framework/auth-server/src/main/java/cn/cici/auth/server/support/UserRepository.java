package cn.cici.auth.server.support;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:
 * @createDate:2019/5/6$10:52$
 * @author: Heyfan Xie
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
