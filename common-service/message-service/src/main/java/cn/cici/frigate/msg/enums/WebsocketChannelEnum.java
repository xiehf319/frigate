package cn.cici.frigate.msg.enums;

/**
 * @author xiehf
 * @description: 类介绍:
 * @date 2019/7/11 23:40
 * @concat 370693739@qq.com
 **/
public enum WebsocketChannelEnum {

    /**
     * 点对点聊天
     */
    CHAT("", "/topic/reply"),

    ;


    /**
     * 描述
     */
    private String descrition;
    /**
     * 客户端订阅的URL
     */
    private String subscribeUrl;

    WebsocketChannelEnum(String descrition, String subscribeUrl) {
        this.descrition = descrition;
        this.subscribeUrl = subscribeUrl;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public String getSubscribeUrl() {
        return subscribeUrl;
    }

    public void setSubscribeUrl(String subscribeUrl) {
        this.subscribeUrl = subscribeUrl;
    }
}
