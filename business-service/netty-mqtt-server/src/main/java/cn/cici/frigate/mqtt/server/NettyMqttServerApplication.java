package cn.cici.frigate.mqtt.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://github.com/daoshenzzg/socket-mqtt
 * http://www.tongxinmao.com/txm/webmqtt.php
 */
@SpringBootApplication
@Slf4j
public class NettyMqttServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyMqttServerApplication.class, args);
    }

}
