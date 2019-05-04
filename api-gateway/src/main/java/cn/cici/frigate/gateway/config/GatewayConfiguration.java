package cn.cici.frigate.gateway.config;

import cn.cici.frigate.gateway.properties.GatewayLimitProperties;
import cn.cici.frigate.gateway.properties.PermitAllUrlProperties;
import cn.cici.frigate.gateway.properties.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author xiehf
 * @date 2019/5/3 23:47
 * @concat 370693739@qq.com
 **/
@Configuration
public class GatewayConfiguration {

    private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN";

    private static final String ALLOWED_METHOS = "GET,PUT,POST,DELETE,OPTIONS";

    private static final String ALLOWED_ORIGIN = "*";

    private static final String MAX_AGE = "3600";

    @Bean
    public WebFilter corsFilter() {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = exchange.getResponse();
                HttpHeaders headers = response.getHeaders();
                headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
                headers.add("Access-Control-Allow-Methods", ALLOWED_METHOS);
                headers.add("Access-Control-Max-Aga", MAX_AGE);
                headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);

                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(exchange);
        };
    }


    @Bean
    @ConfigurationProperties(prefix = "auth")
    public PermitAllUrlProperties getPermitAllUrlProperties() {
        return new PermitAllUrlProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "gateway.limit")
    public GatewayLimitProperties gatewayLimitProperties() {
        return new GatewayLimitProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2")
    public ResourceServerProperties resourceServerProperties() {
        return new ResourceServerProperties();
    }


}
