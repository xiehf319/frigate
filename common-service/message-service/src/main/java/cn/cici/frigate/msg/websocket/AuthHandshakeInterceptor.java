package cn.cici.frigate.msg.websocket;

import cn.cici.frigate.component.context.SpringContextUtils;
import cn.cici.frigate.msg.pojo.User;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @description: 类介绍：
 * 实现需要登陆了才能连接的功能
 * @createDate: 2019/7/12 9:56
 * @author: Heyfan Xie
 */
@Component
@Slf4j
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {

        log.info("客户端连接服务器");
        HttpSession session = SpringContextUtils.getSession();
        User user = (User) session.getAttribute(WsConstants.SESSION_USER);
        log.info("连接成功 {}", JSONObject.toJSONString(user));
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
