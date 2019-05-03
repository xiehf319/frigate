package cn.cici.frigate.gateway.config;

import cn.cici.frigate.gateway.handler.FallbackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author xiehf
 * @date 2019/5/2 22:53
 * @concat 370693739@qq.com
 **/
@Configuration
@EnableWebFlux
public class WebfluxRouteConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(FallbackHandler fallbackHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/fallbackcontroller")
                        , fallbackHandler::fall)
                .andRoute(RequestPredicates.POST("/fallbackcontroller"), fallbackHandler::fall);
    }
}
