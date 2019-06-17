package cn.cici.frigate.mqtt.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @createDate:2019/6/17$16:47$
 * @author: Heyfan Xie
 */
@Component
@Data
@ConfigurationProperties("mqtt.server.zk")
public class ServerConfig {

    private String zkRoot;

    private String zkAddr;

    private boolean zkSwitch;

    private int serverPort;

    private String clearRouteUrl;

    private long heartbeatTime;

    private int zkConnectTimeout;
}
