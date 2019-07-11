package cn.cici.auth.server.config;

import cn.cici.auth.server.security.service.CustomUserDetailService;
import cn.cici.auth.server.security.sms.ValidateCodeFilter;
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

//import cn.cici.auth.server.security.sms.SmsCodeSecurityConfig;

/**
 * 支持表单登陆的方式
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
//
//    @Autowired
//    private SmsCodeSecurityConfig smsCodeSecurityConfig;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

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
//                .apply(smsCodeSecurityConfig)
//
//                // 设置验证码过滤器到过滤器链中
//                .and().addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//
                // 设置自定义表单登陆页面
                .formLogin().loginPage("/login.html")

                // 设置登陆验证请求地址为自定义登陆页配置
                .loginProcessingUrl("/login/form")

                // 设置默认登陆成功跳转页面
                .defaultSuccessUrl("/main.html")

                // 授权请求设置
                .and().authorizeRequests()

                // 设置不需要授权的请求
                .antMatchers("/js/**", "/code/**", "/login.html").permitAll()

                // 其他请求需要验证权限
                .anyRequest().authenticated()

                // 设置userDetailsService
                .and().userDetailsService(userServiceDetail)

                // 暂时停用csrf
                .csrf().disable();
    }

}
