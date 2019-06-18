package cn.cici.frigate.mqtt.server.server;

import cn.cici.frigate.mqtt.server.config.ServerConfig;
import cn.cici.frigate.mqtt.server.listener.EchoMessageEventListener;
import cn.cici.frigate.mqtt.starter.pojo.MqttRequest;
import cn.cici.frigate.mqtt.starter.service.WrappedChannel;
import cn.cici.frigate.mqtt.starter.service.server.Server;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @createDate:2019/6/18$12:02$
 * @author: Heyfan Xie
 */
@Slf4j
@Component
public class MqttServer{

    @Autowired
    private ServerConfig serverConfig;

    @PostConstruct
    public void init() throws Exception{
        Server server = new Server();
        server.setPort(serverConfig.getPort());
        server.setOpenCount(true);
        server.setCheckHeartbeat(true);
        server.setOpenStatus(true);
        server.setStatusPort(serverConfig.getStatusPort());
        server.setServiceName(serverConfig.getServiceName());
        server.setCenterAddr(serverConfig.getCenterAddr());
        server.addEventListener(new EchoMessageEventListener());
        server.setSocketType(serverConfig.getSocketType());
        server.bind();

        //模拟推送
        JSONObject message = new JSONObject();
        message.put("action", "echo");
        message.put("message", "this is yb push message!");

        MqttRequest mqttRequest = new MqttRequest((message.toString().getBytes()));
        while (true) {
            if (server.getChannels().size() > 0) {
                log.info("模拟推送消息, {}", server.getCountInfo());
                for (WrappedChannel channel : server.getChannels().values()) {
                    server.send(channel, "yb/notice/", mqttRequest);
                }
            }
            Thread.sleep(1000L);
        }
    }
}
