package cn.cici.frigate.id;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiehf
 * @date 2019/5/14 22:58
 * @concat 370693739@qq.com
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"cn.cici.frigate.id.worker.dao"})
public class IdServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdServiceApplication.class, args);
    }

}
