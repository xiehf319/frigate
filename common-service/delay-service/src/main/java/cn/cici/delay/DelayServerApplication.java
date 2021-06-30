package cn.cici.delay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DelayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DelayServerApplication.class, args);
    }
}
