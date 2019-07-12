package cn.cici.frigate.gateway.config;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.web.client.RestOperations;

import cn.cici.frigate.gateway.filter.CustomRemoteTokenServices;
import cn.cici.frigate.gateway.properties.PermitAllProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/12 14:47
 * @author: Heyfan Xie
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Autowired
    private PermitAllProperties permitAllProperties;

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
        CustomRemoteTokenServices customRemoteTokenServices = new CustomRemoteTokenServices();
        customRemoteTokenServices.setCheckTokenEndpointUrl(resourceServerProperties.getTokenInfoUri());
        customRemoteTokenServices.setClientId(resourceServerProperties.getClientId());
        customRemoteTokenServices.setClientSecret(resourceServerProperties.getClientSecret());
        customRemoteTokenServices.setLoadBalancerClient(loadBalancerClient);
        customRemoteTokenServices.setTokenName("");

        resources.tokenServices(customRemoteTokenServices);
    }
}
