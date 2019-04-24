package com.mantis.framework.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @createDate:2019/4/24$10:50$
 * @author: Heyfan Xie
 */
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
