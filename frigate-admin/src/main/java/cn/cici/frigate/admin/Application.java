package cn.cici.frigate.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description: 该服务有自己的配置文件，不纳入配置管理
 * @createDate:2019/4/24$10:50$
 * @author: Heyfan Xie
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.frigate.framework.admin.clients")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
