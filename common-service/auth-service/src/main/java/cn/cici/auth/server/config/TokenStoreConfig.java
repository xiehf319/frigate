package cn.cici.auth.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/11 11:16
 * @author: Heyfan Xie
 */
@Configuration
public class TokenStoreConfig {

    /**
     * tokenStore 单独一个配置类注入bean，
     * 防止放到AuthorizationServerConfiguration 之中，endpoint.setTokenStore的时候，由于redisConnectionFactory未能创建bean造成空指针
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTokenStore redisTokenStore(@Autowired RedisConnectionFactory redisConnectionFactory) {
        RedisTokenStore customRedisTokenStore = new RedisTokenStore(redisConnectionFactory);
        customRedisTokenStore.setPrefix("FRIGATE-AUTH:");
        return customRedisTokenStore;
    }
}
