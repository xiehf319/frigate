package cn.cici.frigate.mqtt.push.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @createDate:2019/6/18$11:15$
 * @author: Heyfan Xie
 */
@Component
@ConfigurationProperties(prefix = "com.mqtt")
@Data
public class MqttConfiguration {

    private String host;

    private String clientId;

    private String topic;

    private String username;

    private String password;

    private int timeout;

    private int keepAlive;

}
