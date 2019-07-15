package cn.cici.frigate.rbac;

import cn.cici.frigate.component.annotation.EnableLogCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableLogCollector(basePackages = {"cn.cici.frigate.rbac.controller"})
public class RbacCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacCenterApplication.class, args);
    }

}
