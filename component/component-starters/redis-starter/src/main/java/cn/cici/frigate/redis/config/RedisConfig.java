package cn.cici.frigate.redis.config;

import cn.cici.frigate.redis.services.RedisBitServices;
import cn.cici.frigate.redis.services.RedisHashServices;
import cn.cici.frigate.redis.services.RedisHyperLogLogServices;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @description:
 * @createDate:2019/5/9$11:15$
 * @author: Heyfan Xie
 */
@Configuration
@ConditionalOnBean(RedisConnectionFactory.class)
public class RedisConfig {

    @Bean(name = "stringRedisTemplate")
    @Primary
    RedisTemplate<String, String> stringRedisTemplate(@Autowired RedisConnectionFactory factory) {
        return new RedisTemplate<>();
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        // 忽略不存在的字段报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory factory,
            Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // 字符串方式序列化KEY
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // JSON方式序列化VALUE
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @Bean
    public RedisHyperLogLogServices redisHyperLogLogServices() {
        return new RedisHyperLogLogServices(redisTemplate);
    }


    @Bean
    public RedisBitServices redisBitMapServices() {
        return new RedisBitServices(stringRedisTemplate);
    }


    @Bean
    public RedisHashServices redisHashServices() {
        return new RedisHashServices(stringRedisTemplate);
    }

}
