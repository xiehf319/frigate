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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 自定义用户名密码校验方式(可以只支持加盐的匹配)
//                .authenticationProvider(new UsernamePasswordAuthenticationProvider(userServiceDetail, passwordEncoder()))
                .userDetailsService(userServiceDetail)
                .passwordEncoder(passwordEncoder());
    }


    /**
     * 配置oauth2自带的接口访问
     * 如果需要配置其他登陆的页面也在这里配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // 只处理 /oauth/** 的接口，也就是该filterChain只会处理符合该规则的接口
        http
                .formLogin()
                .loginPage("/login")
                .and()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**", "/login").permitAll();
    }

}
