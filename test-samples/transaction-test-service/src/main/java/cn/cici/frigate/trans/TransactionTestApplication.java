package cn.cici.frigate.trans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TransactionTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionTestApplication.class, args);
    }

}
