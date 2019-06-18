package cn.cici.frigate.mqtt.push.client;


import cn.cici.frigate.mqtt.push.pojo.PushPayload;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @createDate:2019/6/18$11:42$
 * @author: Heyfan Xie
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MqttPushClientTest {



    @Test
    public void publish() {
        String topic = "good";

        PushPayload message = PushPayload.builder().mobile("1234456")
                .content("hello world")
                .build();
        MqttPushClient.getInstance().publish(topic, message);
    }
}