package cn.cici.auth.server.security.service;

import cn.cici.auth.server.client.UserClient;
import cn.cici.auth.server.client.vo.UserVo;
import cn.cici.frigate.component.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        R<UserVo> result = userClient.findByUsername(s);
        return new User(s, "$2a$10$ZgrZfBjHIr4JCEj0vIK1EuQFZYUKRxvsdsk5NdNDRAHkbJtmFec9m", new ArrayList<>());
    }

    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException  {
        return new User(mobile, "$2a$10$ZgrZfBjHIr4JCEj0vIK1EuQFZYUKRxvsdsk5NdNDRAHkbJtmFec9m", new ArrayList<>());
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException  {
        return null;
    }
}
