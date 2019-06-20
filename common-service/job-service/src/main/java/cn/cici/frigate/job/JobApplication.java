package cn.cici.frigate.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @createDate:2019/6/19$16:13$
 * @author: Heyfan Xie
 */
@SpringBootApplication
@EnableDiscoveryClient
public class JobApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}
