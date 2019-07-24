package cn.cici.frigate.sso.forth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/23 11:23
 * @author: Heyfan Xie
 */
@SpringBootApplication
public class SsoForthApplication {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(SsoForthApplication.class, args);
    }
}
