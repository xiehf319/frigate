package cn.cici.frigate.gateway.config;

import cn.cici.frigate.gateway.filter.global.AuthorizationFilter;
import cn.cici.frigate.gateway.http.HeaderEnhancerFilter;
import cn.cici.frigate.gateway.properties.GatewayLimitProperties;
import cn.cici.frigate.gateway.properties.PermitAllUrlProperties;
import cn.cici.frigate.gateway.properties.ResourceServerProperties;
import cn.cici.frigate.gateway.security.CustomRemoteTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


/**
 * @author xiehf
 * @date 2019/5/3 14:53
 * @concat 370693739@qq.com
 **/
@Configuration
@EnableConfigurationProperties
@RibbonClient(name = "auth-server")
public class ServiceConfig {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private ResourceServerProperties serverProperties;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Order(100)
    public CustomRemoteTokenService customRemoteTokenService() {
        CustomRemoteTokenService remoteTokenService = new CustomRemoteTokenService(restTemplate());
        remoteTokenService.setCheckTokenEndpointUrl(serverProperties.getResource().getTokenInfoUri());
        remoteTokenService.setClientId(serverProperties.getClient().getClientId());
        remoteTokenService.setClientSecret(serverProperties.getClient().getClientSecret());
        remoteTokenService.setLoadBalancerClient(loadBalancerClient);
        return remoteTokenService;
    }

    @Bean
    public HeaderEnhancerFilter headerEnhancerFilter() {
        return new HeaderEnhancerFilter();
    }


    @Bean
    public AuthorizationFilter authorizationFilter(CustomRemoteTokenService customRemoteTokenService,
                                                   HeaderEnhancerFilter headerEnhancerFilter,
                                                   PermitAllUrlProperties permitAllUrlProperties) {
        return new AuthorizationFilter(customRemoteTokenService, headerEnhancerFilter, permitAllUrlProperties);
    }


    @Bean
    @Primary
    public RedisRateLimiter redisRateLimiter(GatewayLimitProperties gatewayLimitProperties) {
        GatewayLimitProperties.RedisRate redisRate = gatewayLimitProperties.getRedisRate();
        if (Objects.isNull(redisRate)) {
            throw new RuntimeException("Properties not Initial");
        }
        return new RedisRateLimiter(redisRate.getReplenishRate(), redisRate.getBurstCapacity());
    }

}
