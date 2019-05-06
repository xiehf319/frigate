package cn.cici.frigate.gateway.config;

import cn.cici.frigate.gateway.filter.AuthorizationFilter;
import cn.cici.frigate.gateway.filter.OAuth2ResourceFilter;
import cn.cici.frigate.gateway.http.HeaderEnhanceFilter;
import cn.cici.frigate.gateway.properties.ResourceServerProperties;
import cn.cici.frigate.gateway.security.CustomRemoteTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiehf
 * @date 2019/5/4 17:52
 * @concat 370693739@qq.com
 **/
@Configuration
@Slf4j
public class GlobalFilterConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private ResourceServerProperties properties;

    @Bean
    public CustomRemoteTokenService customRemoteTokenService(RestTemplate restTemplate) {
        CustomRemoteTokenService customRemoteTokenService = new CustomRemoteTokenService(restTemplate);
        customRemoteTokenService.setLoadBalancerClient(loadBalancerClient);
        customRemoteTokenService.setResourceServerProperties(properties);
        return customRemoteTokenService;
    }

    @Bean
    public HeaderEnhanceFilter headerEnhanceFilter() {
        return new HeaderEnhanceFilter();
    }

    @Bean
    public AuthorizationFilter authorizationFilter(CustomRemoteTokenService customRemoteTokenService,
                                                   HeaderEnhanceFilter headerEnhanceFilter) {

        return new AuthorizationFilter(customRemoteTokenService, headerEnhanceFilter, properties);
    }

    @Bean
    public OAuth2ResourceFilter oAuth2ResourceFilter() {
        return new OAuth2ResourceFilter(properties);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("read_body", r -> r
                        .readBody(Object.class, requestBody -> {
                            log.info(requestBody.toString());
                            return true;
                        })
                        .and()
                        .path("/oauth/**")
                        .filters(f -> {
                            f.filter(oAuth2ResourceFilter());
                            return f;
                        })
                        .uri("lb://auth-server"))
                .build();

    }
}
