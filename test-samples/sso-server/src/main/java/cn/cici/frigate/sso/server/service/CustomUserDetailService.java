package cn.cici.frigate.sso.server.service;

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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetail(2L, username, "$2a$10$ZgrZfBjHIr4JCEj0vIK1EuQFZYUKRxvsdsk5NdNDRAHkbJtmFec9m", new ArrayList<>());
    }

    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        return new CustomUserDetail(2L, mobile, "$2a$10$ZgrZfBjHIr4JCEj0vIK1EuQFZYUKRxvsdsk5NdNDRAHkbJtmFec9m", new ArrayList<>());
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return null;
    }
}
