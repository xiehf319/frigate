package cn.cici.frigate.sms.api;

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
public class RestConfig {

    @Bean
    @ConditionalOnMissingBean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public SmsCodeClient smsCodeClient() {
        return new SmsCodeClient();
    }
}
