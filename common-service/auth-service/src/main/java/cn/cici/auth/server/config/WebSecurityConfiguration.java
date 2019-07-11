package cn.cici.auth.server.config;

import cn.cici.auth.server.security.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * WebSecurityConfigurerAdapter 与 ResourceServerConfigurerAdapter 区别
 * https://www.jianshu.com/p/fe1194ca8ecd
 *
 * @description:
 * @createDate:2019/4/29$11:00$
 * @author: Heyfan Xie
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomUserDetailService userServiceDetail;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userServiceDetail)
                .passwordEncoder(passwordEncoder());
    }


    /**
     * 配置内置页面登陆的逻辑
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers().anyRequest()
                .and()
                .authorizeRequests()

                // 可以访问
                .antMatchers("/oauth/*").permitAll()
                .anyRequest().authenticated()
                // 暂时停用csrf
                .and().csrf().disable();
    }

}
