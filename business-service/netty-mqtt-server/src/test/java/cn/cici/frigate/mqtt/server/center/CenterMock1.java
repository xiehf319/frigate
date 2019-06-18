package cn.cici.frigate.mqtt.server.center;

import cn.cici.frigate.mqtt.starter.codec.JsonDecoder;
import cn.cici.frigate.mqtt.starter.codec.JsonEncoder;
import cn.cici.frigate.mqtt.starter.service.server.Server;

/**
 *
 * @date 2019/6/16 14:47
 */
public class CenterMock1 {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.setPort(9000);
        server.setCheckHeartbeat(false);
        server.addChannelHandler("decoder", new JsonDecoder());
        server.addChannelHandler("encoder", new JsonEncoder());
        server.addEventListener(new CenterMockMessageEventListener());
        server.bind();
    }
}
