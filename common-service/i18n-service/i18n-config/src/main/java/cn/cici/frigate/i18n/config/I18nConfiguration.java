package cn.cici.frigate.i18n.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 13:59
 * @author: Heyfan Xie
 */
@Configuration
public class I18nConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MessageResource messageResource() {
        return new JdbcMessageResources();
    }

    @Bean
    public I18nContext i18nContext() {
        return new I18nContext();
    }
}
