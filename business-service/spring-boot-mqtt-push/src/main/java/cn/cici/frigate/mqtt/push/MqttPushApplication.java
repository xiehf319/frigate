package cn.cici.frigate.mqtt.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @description:
 * @createDate:2019/6/18$11:12$
 * @author: Heyfan Xie
 */
@SpringBootApplication
public class MqttPushApplication {

    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(MqttPushApplication.class, args);
    }
}
