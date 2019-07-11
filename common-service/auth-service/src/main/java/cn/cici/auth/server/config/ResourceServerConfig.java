package cn.cici.auth.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author heyfan.xie
 * @description: 资源服务配置,
 * @date
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 配置需要拦截的自定义的接口
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 只处理 /user/**的接口，也就是该filterChain只会处理符合该规则的接口
                .requestMatchers().antMatchers("/user/**")
                .and()
                // 再上面的基础上，做更细的权限控制，比如 hasRole 等操作
                .authorizeRequests()
                .antMatchers("/user/**").authenticated();
    }
}
