package cn.cici.frigate.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author xiehf
 * @date 2019/5/3 23:56
 * @concat 370693739@qq.com
 **/
public class RemoteAddrKeyResolver implements KeyResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteAddrKeyResolver.class);

    public static final String BEAN_NAME = "remoteAddrKeyResolver";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        String hostAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        LOGGER.debug("token limit for ip: {}", hostAddress);
        return Mono.just(hostAddress);
    }
}
