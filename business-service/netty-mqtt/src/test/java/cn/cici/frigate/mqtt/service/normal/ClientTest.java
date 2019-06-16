package cn.cici.frigate.mqtt.service.normal;

import cn.cici.frigate.mqtt.codec.JsonDecoder;
import cn.cici.frigate.mqtt.codec.JsonEncoder;
import cn.cici.frigate.mqtt.service.client.Client;
import com.alibaba.fastjson.JSONObject;
import cn.cici.frigate.mqtt.codec.JsonDecoder;
import cn.cici.frigate.mqtt.codec.JsonEncoder;
import cn.cici.frigate.mqtt.pojo.Request;
import cn.cici.frigate.mqtt.pojo.Response;
import cn.cici.frigate.mqtt.service.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @date 2018/12/30 18:43
 */
public class ClientTest {
    private static final Logger logger = LoggerFactory.getLogger(ClientTest.class);

    public static void main(String[] args) throws InterruptedException {

        Client client = new Client();
        client.setIp("127.0.0.1");
        client.setPort(8000);
        client.setConnectTimeout(10000);
        client.setCheckHeartbeat(false);
        client.addChannelHandler("decoder", new JsonDecoder());
        client.addChannelHandler("encoder", new JsonEncoder());
        client.connect();

        for (int i = 0; i < 2; i++) {
            JSONObject message = new JSONObject();
            message.put("action", "echo");
            message.put("message", "hello world!");

            Request request = new Request();
            request.setSequence(i);
            request.setMessage(message);
            Response response = (Response) client.sendWithSync(request, 3000);

            logger.info("成功接收到同步的返回: '{}'.", response);
        }

        //client.shutdown();
    }
}
