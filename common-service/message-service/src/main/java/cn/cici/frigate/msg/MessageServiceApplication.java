package cn.cici.frigate.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/11 17:32
 * @author: Heyfan Xie
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MessageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageServiceApplication.class, args);
    }
}
