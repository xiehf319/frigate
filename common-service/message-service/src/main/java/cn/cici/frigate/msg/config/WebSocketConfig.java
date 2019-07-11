package cn.cici.frigate.msg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @description: 类介绍：
 * todo https://juejin.im/post/5bf4a7075188250c10212671#heading-8
 * @createDate: 2019/7/11 17:56
 * @author: Heyfan Xie
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 订阅地址前缀 /topic 用于广播消息
        registry.enableSimpleBroker("/topic");

        // 给指定用户发送消息的路径前缀
        registry.setUserDestinationPrefix("/user/");

        // 客户端需要把消息发送到这个路径前缀下
        registry.setApplicationDestinationPrefixes("/message");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                // 客户端连接服务端的端点  广播
                .addEndpoint("/webServer")
                // 允许的请求源
                .setAllowedOrigins("*")
                // 设置服务端websocket的类型
                .withSockJS();
        registry
                // 客户端连接服务端的端点  点对点
                .addEndpoint("/queueServer")
                // 允许的请求源
                .setAllowedOrigins("*")
                // 设置服务端websocket的类型
                .withSockJS();
    }
}
