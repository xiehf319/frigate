package cn.cici.frigate.gateway.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author xiehf
 * @date 2019/5/3 23:58
 * @concat 370693739@qq.com
 **/
@EnableAutoConfiguration
@Configuration
public class RouteConfiguration {


    @Bean(name = RemoteAddrKeyResolver.BEAN_NAME)
    @Primary
    public RemoteAddrKeyResolver remoteAddrKeyResolver() {
        return new RemoteAddrKeyResolver();
    }

}
