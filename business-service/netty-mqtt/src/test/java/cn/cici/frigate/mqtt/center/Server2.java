package cn.cici.frigate.mqtt.center;

import cn.cici.frigate.mqtt.service.normal.JsonEchoMessageEventListener;
import cn.cici.frigate.mqtt.service.server.Server;

/**
 *
 * @date 2019/1/7 22:31
 */
public class Server2 {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.setPort(8010);
        server.setCheckHeartbeat(false);
        server.setCenterAddr("127.0.0.1:9000,127.0.0.1:9010");
        server.addEventListener(new JsonEchoMessageEventListener());
        server.bind();
    }
}
