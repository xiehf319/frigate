package cn.cici.frigate.redis.config;

import cn.cici.frigate.redis.services.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 16:26
 * @author: Heyfan Xie
 */
@Configuration
@Import({
        RedisBitServices.class,
        RedisHashServices.class,
        RedisSetServices.class,
        RedisSortedSetServices.class,
        RedisStringServices.class,
        RedisListServices.class,
        RedisHyperLogLogServices.class
})
public class RedisServiceConfig {

}
