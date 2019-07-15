package cn.cici.frigate.msg.websocket;

import cn.cici.frigate.component.context.SpringContextUtils;
import cn.cici.frigate.msg.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * @description: 类介绍：
 * 生成自定义的Principal
 * @createDate: 2019/7/12 10:13
 * @author: Heyfan Xie
 */
@Component
@Slf4j
public class WsHandshakeHandler extends DefaultHandshakeHandler {


    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        HttpSession session = SpringContextUtils.getSession();
        User user = (User) session.getAttribute(WsConstants.SESSION_USER);
        if (user != null) {
            log.info("websocket连接开始创建Principal, 用户： {}", user.getUsername());
            return new WsPrincipal(user.getUsername());
        } else {
            log.info("未登陆系统，禁止连接webscoket");
            return null;
        }
    }
}
