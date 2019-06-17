package cn.cici.frigate.mqtt.starter.center;

import cn.cici.frigate.mqtt.codec.JsonDecoder;
import cn.cici.frigate.mqtt.codec.JsonEncoder;
import cn.cici.frigate.mqtt.server.service.server.Server;

/**
 *
 * @date 2019/1/7 22:18
 */
public class CenterMock2 {

    public static void main(String[] args) {

        Server server = new Server();
        server.setPort(9010);
        server.setCheckHeartbeat(false);
        server.addChannelHandler("decoder", new JsonDecoder());
        server.addChannelHandler("encoder", new JsonEncoder());
        server.addEventListener(new cn.cici.frigate.mqtt.server.center.CenterMockMessageEventListener());
        server.bind();
    }
}
