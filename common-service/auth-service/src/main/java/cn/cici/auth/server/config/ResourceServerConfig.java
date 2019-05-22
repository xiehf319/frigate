package cn.cici.auth.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * @description:
 * @createDate:2019/5/22$14:50$
 * @author: Heyfan Xie
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .anonymous()
                .disable()
                .requestMatchers()
                .antMatchers("/api/**")
                .and()
                .authorizeRequests()
                .antMatchers("/api/**")
                .fullyAuthenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
