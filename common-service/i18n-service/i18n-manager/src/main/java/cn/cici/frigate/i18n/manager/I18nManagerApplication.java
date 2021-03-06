package cn.cici.frigate.i18n.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiehf
 * @description: 类介绍:
 * @date 2019/7/28 14:14
 * @concat 370693739@qq.com
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class I18nManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(I18nManagerApplication.class, args);
    }
}
