package cn.cici.frigate.rbac.service;

import cn.cici.frigate.rbac.constant.RbacResponseEnum;
import cn.cici.frigate.rbac.dao.entity.User;
import cn.cici.frigate.rbac.dao.entity.UserAuth;
import cn.cici.frigate.rbac.dao.repo.UserAuthRepository;
import cn.cici.frigate.rbac.dao.repo.UserRepository;
import cn.cici.frigate.sms.api.SmsCodeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/18 14:36
 * @author: Heyfan Xie
 */
@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SmsCodeClient smsCodeClient;

    /**
     * 新增用户(注册操作)
     *
     * @param username 用户名
     * @param password 密码
     */
    public void systemRegister(String username, String password) {
        UserAuth currentUser = userAuthRepository.findByIdentifierAndIdentityType(username, "SYSTEM");
        RbacResponseEnum.USERNAME_IS_EXISTS.assertNull(currentUser);
        String encodePwd = bCryptPasswordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setMobile("");
        user.setEmail("");
        user.setRemark("");
        user.setAvatarUrl("");
        user.setLocked(false);
        user.setEnabled(true);
        userRepository.save(user);
        UserAuth userAuth = new UserAuth();
        userAuth.setIfVerified(true);
        userAuth.setIdentifier(username);
        userAuth.setCredential(encodePwd);
        userAuth.setUserId(user.getId());
        userAuth.setIdentityType("SYSTEM");
        userAuthRepository.save(userAuth);
    }

    /**
     * 邮箱注册
     *
     * @param email     邮箱号
     * @param emailCode 邮箱验证码
     * @param password  密码
     */
    public void emailRegister(String email, String emailCode, String password) {
        // todo 校验邮箱验证码

        UserAuth currentUser = userAuthRepository.findByIdentifierAndIdentityType(email, "EMAIL");
        RbacResponseEnum.USERNAME_IS_EXISTS.assertNull(currentUser);

        String encodePwd = bCryptPasswordEncoder.encode(password);
        User user = new User();
        user.setUsername(email);
        user.setMobile("");
        user.setEmail(email);
        user.setRemark("");
        user.setAvatarUrl("");
        user.setLocked(false);
        user.setEnabled(true);
        userRepository.save(user);

        UserAuth userAuth = new UserAuth();
        userAuth.setIfVerified(true);
        userAuth.setIdentifier(email);
        userAuth.setCredential(encodePwd);
        userAuth.setIdentityType("EMAIL");
        userAuthRepository.save(userAuth);
    }

    /**
     * 手机号注册
     *
     * @param mobile   手机号
     * @param smsCode  手机验证码
     * @param password 密码
     */
    public void mobileRegister(String mobile, String smsCode, String password) {
        // 校验验证码
        smsCodeClient.verify(mobile, smsCode);
        UserAuth currentUser = userAuthRepository.findByIdentifierAndIdentityType(mobile, "MOBILE");
        RbacResponseEnum.USERNAME_IS_EXISTS.assertNull(currentUser);
        String encodePwd = bCryptPasswordEncoder.encode(password);
        UserAuth userAuth = new UserAuth();
        userAuth.setIfVerified(true);
        userAuth.setIdentifier(mobile);
        userAuth.setCredential(encodePwd);
        userAuth.setIdentityType("MOBILE");
        userAuthRepository.save(userAuth);
    }
}
