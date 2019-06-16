package cn.cici.frigate.mqtt.pojo;

/**
 * @author xiehf
 * @date 2019/6/16 10:48
 * @concat 370693739@qq.com
 **/
public class Heartbeat extends BaseMessage {

    private static final long serialVersionUID = 1L;

    public static final byte[] BYTES = new byte[0];

    private static Heartbeat instance = new Heartbeat();

    public static Heartbeat getSingleton() {
        return instance;
    }

    private Heartbeat() {
    }
}
