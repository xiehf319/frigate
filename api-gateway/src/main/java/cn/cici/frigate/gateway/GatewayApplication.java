package cn.cici.frigate.gateway;

import cn.cici.frigate.gateway.clients.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.cici.resource.server.clients"})
@RestController
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }


    @Autowired
    private AuthClient authClient;

    @RequestMapping("/user")
    public Map<String, Object> user() {
        return authClient.user();
    }
}
