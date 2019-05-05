package cn.cici.frigate.gateway.filter;

import cn.cici.frigate.gateway.properties.ResourceServerProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @description:
 * @createDate:2019/5/5$10:12$
 * @author: Heyfan Xie
 */
@Slf4j
public class OAuth2ResourceFilter implements GatewayFilter, Ordered {

    public OAuth2ResourceFilter() { }

    private ResourceServerProperties properties;

    private static final String LOGIN_URI = "/oauth/login";


    private static final String CACHE_REQUEST_BODY_OBJECT_KEY = "cachedRequestBodyObject";

    public OAuth2ResourceFilter(ResourceServerProperties properties) {
        this.properties = properties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest rawRequest = exchange.getRequest();
        String rawPath = rawRequest.getURI().getRawPath();
        if (LOGIN_URI.equals(rawPath)) {
            return handleParameter(exchange, chain);
        }
        Object attribute = exchange.getAttribute(CACHE_REQUEST_BODY_OBJECT_KEY);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)attribute;
        log.info(JSONObject.toJSONString(map));
        return chain.filter(exchange);
    }

    private Mono<Void> handleParameter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = request.getMethod();
        String contentType = request.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);

        if (method == HttpMethod.POST
                && (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(contentType)
                || MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType))) {

            // 获取post请求体
            Object attribute = exchange.getAttribute(CACHE_REQUEST_BODY_OBJECT_KEY);
            if (attribute == null) {
                log.error("请求异常 {} POST请求必须传递参数", request.getURI().getRawPath());
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                return response.setComplete();
            }
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)attribute;
            log.info(JSONObject.toJSONString(map));
            String requestPath = getRequestPath(map);
            log.info("redirect uri = {}", requestPath);
            ServerHttpRequest newRequest = exchange.getRequest().mutate().headers(httpHeaders -> {
                httpHeaders.setBasicAuth(properties.getClient().getClientId(), properties.getClient().getClientSecret());
                httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            }).method(HttpMethod.POST).path(requestPath).build();

            return chain.filter(exchange.mutate().request(newRequest).build());
        }
        return chain.filter(exchange);
    }


    private String getRequestPath(LinkedHashMap<String, Object> map) {
        return properties.getClient().getAccessTokenUri() + "?username=" +
                map.get("username") +
                "&password=" +
                map.get("password") +
                "&grant_type=" +
                properties.getClient().getGrantType() +
                "&scope=" +
                properties.getClient().getScope();
    }


    @Override
    public int getOrder() {
        return -100;
    }

}
