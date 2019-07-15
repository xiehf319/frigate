package cn.cici.frigate.msg.websocket;

import java.security.Principal;

/**
 * @description: 类介绍：
 * 自定义的Principal
 * @createDate: 2019/7/12 10:23
 * @author: Heyfan Xie
 */
public class WsPrincipal implements Principal {

    private String username;

    public WsPrincipal(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
}
