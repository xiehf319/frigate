package cn.cici.frigate.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author xiehf
 * @date 2019/5/4 15:12
 * @concat 370693739@qq.com
 **/
@Slf4j
public class AuthorizationFilter implements Ordered, GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("AuthorizationFilter ");

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
