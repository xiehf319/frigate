package cn.cici.frigate.gateway.filter.route;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @createDate:2019/4/30$10:45$
 * @author: Heyfan Xie
 */
@Component
public class HostAddrKeyResolver implements KeyResolver {

    /**
     * 根据ip做限流
     *
     * @param exchange
     * @return
     */
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
