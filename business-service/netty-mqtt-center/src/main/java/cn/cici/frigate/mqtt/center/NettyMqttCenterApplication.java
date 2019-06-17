package cn.cici.frigate.mqtt.center;

import cn.cici.frigate.mqtt.starter.codec.JsonDecoder;
import cn.cici.frigate.mqtt.starter.codec.JsonEncoder;
import cn.cici.frigate.mqtt.starter.service.server.Server;
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
public class NettyMqttCenterApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyMqttCenterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Server server = new Server();
        server.setPort(9010);
        server.setCheckHeartbeat(false);
        server.addChannelHandler("decoder", new JsonDecoder());
        server.addChannelHandler("encoder", new JsonEncoder());
        server.addEventListener(new CenterMockMessageEventListener());
        server.bind();
    }
}
