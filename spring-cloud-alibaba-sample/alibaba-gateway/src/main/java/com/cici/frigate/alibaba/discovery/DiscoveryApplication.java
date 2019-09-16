package com.cici.frigate.alibaba.discovery;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryApplication {


    public static void main(String[] args) {
        SpringApplication.run(DiscoveryApplication.class, args);
    }

    @RestController
    @RefreshScope
    public static class TestController {

        @Value("${message}")
        private String message;

        @RequestMapping("/test")
        public String test() {
            return "test: " + message;
        }

    }
}
