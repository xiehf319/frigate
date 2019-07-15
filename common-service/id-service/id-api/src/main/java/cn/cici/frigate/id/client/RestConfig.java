package cn.cici.frigate.id.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @createDate:2019/7/9$11:45$
 * @author: Heyfan Xie
 */
@Configuration
@ConditionalOnMissingBean(RestTemplate.class)
public class RestConfig {

    @Bean
    @ConditionalOnMissingBean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
