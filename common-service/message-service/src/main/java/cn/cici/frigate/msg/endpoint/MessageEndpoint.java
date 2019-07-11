package cn.cici.frigate.msg.endpoint;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/11 18:00
 * @author: Heyfan Xie
 */
@Controller
public class MessageEndpoint {

    // api得治
    @MessageMapping("/hello")
    // 发送到指定的频道
    @SendTo("/topic/greetings")
    public Object hello() {
        return "aaa";
    }
}
