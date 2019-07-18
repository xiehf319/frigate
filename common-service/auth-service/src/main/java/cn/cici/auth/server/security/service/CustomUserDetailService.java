package cn.cici.auth.server.security.service;

import cn.cici.auth.server.client.UserClient;
import cn.cici.auth.server.constant.BizResponseEnum;
import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.component.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @description:
 * @createDate:2019/5/6$10:26$
 * @author: Heyfan Xie
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        R<UserInfo> result = userClient.findByUsername(username);
        if (result.getCode() != HttpStatus.OK.value()) {
            throw new UsernameNotFoundException("调用服务异常");
        }
        UserInfo data = result.getData();
        BizResponseEnum.USERNAME_NOT_FOUND.assertNotNull(data);
        return new CustomUserDetail(data.getId(), username, data.getPassword(), new ArrayList<>());
    }

    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        return new CustomUserDetail(2L, mobile, "$2a$10$ZgrZfBjHIr4JCEj0vIK1EuQFZYUKRxvsdsk5NdNDRAHkbJtmFec9m", new ArrayList<>());
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return null;
    }
}
