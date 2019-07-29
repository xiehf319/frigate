package cn.cici.frigate.rbac.service;

import cn.cici.frigate.rbac.dao.entity.User;
import cn.cici.frigate.rbac.dao.entity.UserAuth;
import cn.cici.frigate.rbac.dao.repo.UserAuthRepository;
import cn.cici.frigate.rbac.dao.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @description:
 *  用户服务
 * @createDate:2019/7/9$14:16$
 * @author: Heyfan Xie
 */
@Service
public class UserService {

    @Autowired
    UserRepository  userRepository;

    @Autowired
    UserAuthRepository userAuthRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 根据用户名查找用户
     * @param username  用户名
     * @return
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    /**
     * 根据用户id和授权类型查询用户授权信息
     *
     * @param userId       用户id
     * @param type 授权类型
     * @return
     */
    public UserAuth findByUserIdAndType(Long userId, String type) {
        return userAuthRepository.findByUserIdAndAndIdentityType(userId, type);
    }
}
