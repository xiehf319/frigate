package cn.cici.frigate.mqtt.server.center;

import cn.cici.frigate.mqtt.starter.codec.JsonDecoder;
import cn.cici.frigate.mqtt.starter.codec.JsonEncoder;
import cn.cici.frigate.mqtt.starter.pojo.Request;
import cn.cici.frigate.mqtt.starter.service.client.Client;

/**
 *
 * @date 2019/1/7 22:32
 */
public class ClientTest {

    public static void main(String[] args) throws Exception {
        final Client client = new Client();
        client.setCheckHeartbeat(false);
        client.setCenterAddr("127.0.0.1:9000,127.0.0.1:9010");
        client.addChannelHandler("decoder", new JsonDecoder());
        client.addChannelHandler("encoder", new JsonEncoder());
        client.connect();

        for (int i = 0; i < 5; i++) {
            Request request = new Request();
            request.setSequence(i);
            request.setMessage("{\"action\":\"echo\",\"message\":\"hello\"}");
            client.send(request);
            Thread.sleep(5000L);
        }
    }
}
