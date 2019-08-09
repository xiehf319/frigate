package cn.cici.frigate.rbac;

import cn.cici.frigate.component.annotation.EnableLogCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.cici.frigate.rbac.config"})
@EnableLogCollector(basePackages = {"cn.cici.frigate.rbac.controller"})
public class RbacCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacCenterApplication.class, args);
    }

}
