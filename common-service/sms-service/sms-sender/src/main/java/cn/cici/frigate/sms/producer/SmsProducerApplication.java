package cn.cici.frigate.sms.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/15 14:52
 * @author: Heyfan Xie
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SmsProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsProducerApplication.class, args);
    }
}
