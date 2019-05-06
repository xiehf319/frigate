package cn.cici.frigate.gateway.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @description:
 * @createDate:2019/5/6$12:01$
 * @author: Heyfan Xie
 */
@Configuration
@EnableOAuth2Sso
public class GatewayConfiguration extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/oauth/token").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
