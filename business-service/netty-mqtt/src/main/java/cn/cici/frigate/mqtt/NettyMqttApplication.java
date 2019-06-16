package cn.cici.frigate.mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://github.com/daoshenzzg/socket-mqtt
 */
@SpringBootApplication
public class NettyMqttApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyMqttApplication.class, args);
    }

}
