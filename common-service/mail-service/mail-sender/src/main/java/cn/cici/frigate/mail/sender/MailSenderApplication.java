package cn.cici.frigate.mail.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description: 类介绍：
 *  邮件发送服务，
 *      支持同步feign发送
 *      也可以通过异步mq的方式
 * @createDate: 2019/7/16 11:31
 * @author: Heyfan Xie
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MailSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailSenderApplication.class, args);
    }
}
