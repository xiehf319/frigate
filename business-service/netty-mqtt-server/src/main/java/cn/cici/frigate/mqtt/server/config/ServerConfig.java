package cn.cici.frigate.mqtt.server.config;

import cn.cici.frigate.mqtt.starter.service.SocketType;
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
@ConfigurationProperties("mqtt.server")
public class ServerConfig {


    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 服务端端口
     */
    private int port;

    /**
     * 服务状态端口
     */
    private int statusPort;

    /**
     * 注册中心地址
     */
    private String centerAddr;

    /**
     * 心跳时间
     */
    private int heartbeatTime;

    /**
     * 超时时间
     */
    private int connectTimeout;

    /**
     * socketType
     */
    private SocketType socketType;
}
