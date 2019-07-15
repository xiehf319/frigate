package cn.cici.frigate.gateway.security;

import cn.cici.frigate.gateway.properties.PermitAllProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.client.RestTemplate;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/12 14:47
 * @author: Heyfan Xie
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Autowired
    private PermitAllProperties permitAllProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HeaderEnhancer headerEnhancer;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // 只处理 /oauth/** 的接口，也就是该filterChain只会处理符合该规则的接口
        http.requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(permitAllProperties.getPermitAllPatterns()).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        CustomUserInfoServices customUserInfoServices = new CustomUserInfoServices(restTemplate);
        customUserInfoServices.setUserInfoEndpointUrl(resourceServerProperties.getUserInfoUri());
        customUserInfoServices.setHeaderEnhancer(headerEnhancer);
        // 检查token 获取client信息 只能获取到简单的 client_id 和 username 无法获取更加详细的信息
        resources.tokenServices(customUserInfoServices);
    }

    @Bean
    public RedisTokenStore redisTokenStore(@Autowired RedisConnectionFactory factory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(factory);
        redisTokenStore.setPrefix("FRIGATE-AUTH:");
        return redisTokenStore;
    }
}
