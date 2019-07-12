package cn.cici.frigate.msg.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Principal;

/**
 * @description: 类介绍：
 * 实现断开连接的处理
 * @createDate: 2019/7/12 10:01
 * @author: Heyfan Xie
 */
@Component
@Slf4j
public class WsChannelInterceptor implements ChannelInterceptor {

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        StompCommand command = accessor.getCommand();

        // 用户断开连接
        if (StompCommand.DISCONNECT.equals(command)) {

            String user = "";
            Principal principal = accessor.getUser();

            if (principal != null && !StringUtils.isEmpty(principal.getName())) {
                user = principal.getName();
            } else {
                user = accessor.getSessionId();
            }
            log.info("用户{}的webSocket连接已经断开", user);
        }
    }
}
