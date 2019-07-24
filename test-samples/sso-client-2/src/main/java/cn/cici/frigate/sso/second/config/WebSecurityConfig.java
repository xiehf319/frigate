package cn.cici.frigate.sso.second.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.env.EnvironmentUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/23 11:37
 * @author: Heyfan Xie
 */
@EnableOAuth2Sso
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private EnvironmentUtils environmentUtils;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/bootstrap/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll();

        http
                .logout().logoutSuccessUrl("http://localhost:4000/logout")
                .and()
                .authorizeRequests()
//                .antMatchers("/**/login/").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();

    }
}
