package cn.cici.frigate.user.config;

import cn.cici.frigate.user.properties.SecurityProperties;
import cn.cici.frigate.user.security.RedisTokenStore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @description:
 * @createDate:2019/5/7$13:53$
 * @author: Heyfan Xie
 */
@Configuration
public class AuthConfig {

    @Bean
    @ConfigurationProperties(prefix = "security")
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }

    @Bean
    public RedisTokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix("ADMIN:");
        return redisTokenStore;
    }

}
