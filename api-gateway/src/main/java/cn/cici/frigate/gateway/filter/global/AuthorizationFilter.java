package cn.cici.frigate.gateway.filter.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;


/**
 * https://github.com/keets2012/microservice-integration.git
 *
 * @description:
 * @createDate:2019/4/30$10:30$
 * @author: Heyfan Xie
 */
@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);


//    private final CustomRemoteTokenServices customRemoteTokenServices;
//
//    private final HeaderEnhanceFilter headerEnhanceFilter;
//
//    private PermitAllUrlProperties permitAllUrlProperties;
//
//    public AuthorizationFilter(CustomRemoteTokenServices customRemoteTokenServices, HeaderEnhanceFilter headerEnhanceFilter, PermitAllUrlProperties permitAllUrlProperties) {
//        this.customRemoteTokenServices = customRemoteTokenServices;
//        this.headerEnhanceFilter = headerEnhanceFilter;
//        this.permitAllUrlProperties = permitAllUrlProperties;
//    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        if (predicate(exchange)) {
//            request = headerEnhanceFilter.doFilter(request);
//            String accessToken = extractHeaderToken(request);
//            customRemoteTokenServices.loadAuthentication(accessToken);
            logger.info("success auth token and permission");
        }
        return chain.filter(exchange);
    }

    public Boolean predicate(ServerWebExchange serverWebExchange) {
        URI uri = serverWebExchange.getRequest().getURI();
        String requestUri = uri.getPath();
        String authorization = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (isPermitUrl(requestUri) &&
                (StringUtils.isEmpty(authorization))) {
        }
        return false;
    }

    private boolean isLogoutUrl(String url) {
        return url.contains("/login/logout");
    }

    private boolean isPermitUrl(String url) {
//        return permitAllUrlProperties.isPermitAllUrl(url) || url.contains("/login/oauth");
        return true;
    }

    protected String extractHeaderToken(ServerHttpRequest request) {
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.isEmpty(authorization)) { // typically there is only one (most servers enforce that)
            if ((authorization.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = authorization.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
                // Add this here for the auth details later. Would be better to change the signature of this method.
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }

        return null;
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
