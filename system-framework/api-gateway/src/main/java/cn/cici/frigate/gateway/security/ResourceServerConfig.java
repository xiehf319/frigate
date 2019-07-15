package cn.cici.frigate.gateway.security;

import cn.cici.frigate.gateway.properties.PermitAllProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
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
        http
                // 只会处理/**匹配的地址,假设该值是/aa/**, 则/bb/**的是不会被处理的
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                // 设置不需要鉴权的url匹配规则
                .antMatchers(permitAllProperties.getPermitAllPatterns()).permitAll()
                // 其他的地址全部需要鉴权
                .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        CustomCheckTokenServices customCheckTokenServices = new CustomCheckTokenServices(restTemplate);
//        customCheckTokenServices.setCheckTokenEndpointUrl(resourceServerProperties.getTokenInfoUri());
//        customCheckTokenServices.setClientId(resourceServerProperties.getClientId());
//        customCheckTokenServices.setClientSecret(resourceServerProperties.getClientSecret());
//        resources.tokenServices(customCheckTokenServices);
        CustomUserInfoServices customUserInfoServices = new CustomUserInfoServices(restTemplate);
        // 获取token用户信息的uri
        customUserInfoServices.setUserInfoEndpointUrl(resourceServerProperties.getUserInfoUri());
        // header增强
        customUserInfoServices.setHeaderEnhancer(headerEnhancer);
        // 检查token 获取client信息 只能获取到简单的 client_id 和 username 无法获取更加详细的信息
        resources.tokenServices(customUserInfoServices);
    }
}
