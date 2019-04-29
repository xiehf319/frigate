package cn.cici.frigate.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @description:
 * @createDate:2019/4/24$10:25$
 * @author: Heyfan Xie
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
