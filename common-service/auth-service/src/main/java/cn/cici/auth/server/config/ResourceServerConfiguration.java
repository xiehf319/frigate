package cn.cici.auth.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户保护Oauth2要开放的资源，同时主要用于client端以及token的认证,比如:/user/current
 * 由于oauth2需要对外提供用户信息，所以自身也是一个ResourceServer，默认拦截所有的请求，
 * 也可以通过重写方法自定义自己想要拦截的资源url地址
 *
 * @description:
 * @createDate:2019/5/8$10:33$
 * @author: Heyfan Xie
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    /**
     * 默认所有请求都拦截，所以也可以不需要自定义配置
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

}
