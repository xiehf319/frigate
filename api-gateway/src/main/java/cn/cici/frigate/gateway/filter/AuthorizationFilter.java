package cn.cici.frigate.gateway.filter;

import cn.cici.frigate.gateway.http.HeaderEnhanceFilter;
import cn.cici.frigate.gateway.properties.ResourceServerProperties;
import cn.cici.frigate.gateway.security.CustomRemoteTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author xiehf
 * @date 2019/5/4 15:12
 * @concat 370693739@qq.com
 **/
@Slf4j
public class AuthorizationFilter implements Ordered, GlobalFilter {

    private final ResourceServerProperties resourceServerProperties;
    private final CustomRemoteTokenService customRemoteTokenService;
    private final HeaderEnhanceFilter headerEnhanceFilter;

    public AuthorizationFilter(CustomRemoteTokenService customRemoteTokenService,
                               HeaderEnhanceFilter headerEnhanceFilter,
                               ResourceServerProperties resourceServerProperties) {
        this.resourceServerProperties = resourceServerProperties;
        this.customRemoteTokenService = customRemoteTokenService;
        this.headerEnhanceFilter = headerEnhanceFilter;
    }


    /**
     * 控制url是否是空开的，非公开的需要检查token
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String rawPath = request.getURI().getRawPath();
        log.info("请求地址" + rawPath);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -200;
    }

}
