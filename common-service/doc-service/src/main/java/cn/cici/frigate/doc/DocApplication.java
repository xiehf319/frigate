package cn.cici.frigate.doc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @description:
 * @createDate:2019/5/9$16:07$
 * @author: Heyfan Xie
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableOAuth2Sso
public class DocApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocApplication.class, args);
    }
}
