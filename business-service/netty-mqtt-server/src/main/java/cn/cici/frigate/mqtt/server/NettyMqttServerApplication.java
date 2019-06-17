package cn.cici.frigate.mqtt.server;

import cn.cici.frigate.mqtt.starter.pojo.MqttRequest;
import cn.cici.frigate.mqtt.server.listener.EchoMessageEventListener;
import cn.cici.frigate.mqtt.starter.service.SocketType;
import cn.cici.frigate.mqtt.starter.service.WrappedChannel;
import cn.cici.frigate.mqtt.starter.service.server.Server;
import com.alibaba.fastjson.JSONObject;
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
public class NettyMqttServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyMqttServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Server server = new Server();
        server.setPort(8002);
        server.setOpenCount(true);
        server.setCheckHeartbeat(true);
        server.setOpenStatus(true);
        server.setStatusPort(8003);
        server.setServiceName("mqtt-server");
//        server.setCenterAddr("localhost:40002");
        server.addEventListener(new EchoMessageEventListener());
        server.setSocketType(SocketType.MQTT);
        server.bind();

        //模拟推送
        JSONObject message = new JSONObject();
        message.put("action", "echo");
        message.put("message", "this is yb push message!");

        MqttRequest mqttRequest = new MqttRequest(message.toString().getBytes());
        while (true) {
            if (server.getChannels().size() > 0) {
                log.info("模拟推送消息");
                for (WrappedChannel channel : server.getChannels().values()) {
                    server.send(channel, "yb/notice/", mqttRequest);
                }
            }
            Thread.sleep(1000L);
        }
    }
}
