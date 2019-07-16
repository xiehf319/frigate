package cn.cici.frigate.mail.api;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/16 11:32
 * @author: Heyfan Xie
 */
@Configuration
public class RestConfig {

    @Bean
    @ConditionalOnMissingBean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MailSenderClient mailSenderClient() {
        return new MailSenderClient();
    }
}
