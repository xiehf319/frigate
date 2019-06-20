package cn.cici.frigate.mqtt.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://github.com/daoshenzzg/socket-mqtt
 * http://www.tongxinmao.com/txm/webmqtt.php
 */
@SpringBootApplication
@Slf4j
public class NettyMqttRouteApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyMqttRouteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
