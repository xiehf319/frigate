package cn.cici.frigate.rbac.dao.repo;

import cn.cici.frigate.rbac.dao.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/18 14:16
 * @author: Heyfan Xie
 */
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    /**
     * 根据用户id和授权类型查询用户授权信息
     *
     * @param userId       用户id
     * @param identityType 授权类型
     * @return
     */
    UserAuth findByUserIdAndAndIdentityType(Long userId, String identityType);


    /**
     * 根据 授权名 和 授权类型 查找
     * @param identifier
     * @param identityType
     * @return
     */
    UserAuth findByIdentifierAndIdentityType(String identifier, String identityType);
}
