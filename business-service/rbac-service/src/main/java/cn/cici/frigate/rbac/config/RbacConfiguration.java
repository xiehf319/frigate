package cn.cici.frigate.rbac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/18 11:00
 * @author: Heyfan Xie
 */
@Configuration
public class RbacConfiguration {


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
