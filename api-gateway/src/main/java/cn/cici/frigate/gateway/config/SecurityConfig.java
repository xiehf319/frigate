package cn.cici.frigate.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @description:
 * @createDate:2019/4/30$14:15$
 * @author: Heyfan Xie
 */
@Configuration
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .anyExchange()
                .authenticated();

        return http.build();
    }
}
