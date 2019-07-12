package cn.cici.frigate.msg.websocket;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/12 10:17
 * @author: Heyfan Xie
 */
public class WsConstants {

    /**
     * 用户再session中存储的变量名
     */
    public static final String SESSION_USER = "SESSION_USER";

    /**
     * 回调地址在session中存储的变量名
     */
    public static final String SESSION_LOGIN_REDIRECT_URL = "SESSION_LOGIN_REDIRECT_URL";

    /**
     * 用户未读websocket消息在redis中存储的变量名的前缀
     */
    public static final String REDIS_UNREAD_MSG_PREFIX = "stomp-websocket";
}
