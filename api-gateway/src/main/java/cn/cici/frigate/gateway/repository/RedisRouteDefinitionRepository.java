package cn.cici.frigate.gateway.repository;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @description:
 * @createDate:2019/4/29$17:39$
 * @author: Heyfan Xie
 */
@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    public static final String GATEWAY_ROUTES = "gateway_routes";

    @Resource
    private ReactiveStringRedisTemplate redisTemplate;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return redisTemplate.opsForHash().values(GATEWAY_ROUTES).map(obj-> JSON.parseObject(obj.toString(), RouteDefinition.class));
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {

        return Mono.empty();
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }
}
