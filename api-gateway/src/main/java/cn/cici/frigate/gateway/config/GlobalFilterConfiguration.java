package cn.cici.frigate.gateway.config;

import cn.cici.frigate.gateway.filter.AuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * @author xiehf
 * @date 2019/5/4 17:52
 * @concat 370693739@qq.com
 **/
@Configuration
@Slf4j
public class GlobalFilterConfiguration {

    @Bean
    public AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter();
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("auth", "/oauth/token").uri("lb://auth-server")).build();
    }
}
