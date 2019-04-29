package cn.cici.frigate.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

/**
 * @description:
 * @createDate:2019/4/29$17:35$
 * @author: Heyfan Xie
 */
@Configuration
public class RedisConfig {

    @Bean(name = {"redisTemplate", "stringRedisTemplate"})
    public ReactiveStringRedisTemplate stringRedisTemplate(ReactiveRedisConnectionFactory factory) {
        ReactiveStringRedisTemplate redisTemplate = new ReactiveStringRedisTemplate(factory);
        return redisTemplate;
    }
}
