package cn.cici.frigate.mqtt.push.client;

import cn.cici.frigate.component.context.SpringBeanContext;
import cn.cici.frigate.mqtt.push.config.MqttConfiguration;
import cn.cici.frigate.mqtt.push.pojo.PushPayload;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @description:
 * @createDate:2019/6/18$11:19$
 * @author: Heyfan Xie
 */
@Slf4j
public class MqttPushClient {


    private MqttClient mqttClient;

    private MqttConfiguration config = SpringBeanContext.getBean(MqttConfiguration.class);
    private static volatile MqttPushClient mqttPushClient = null;


    public static MqttPushClient getInstance() {

        if (null == mqttPushClient) {
            synchronized (MqttPushClient.class) {
                if (null == mqttPushClient) {
                    mqttPushClient = new MqttPushClient();
                }
            }

        }
        return mqttPushClient;
    }

    private MqttPushClient() {
        connect();
    }

    private void connect() {
        try {
            mqttClient = new MqttClient(config.getHost(), config.getClientId(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setUserName(config.getUsername());
            options.setPassword(config.getPassword().toCharArray());
            options.setConnectionTimeout(config.getTimeout());
            options.setKeepAliveInterval(config.getKeepAlive());
            try {
                mqttClient.setCallback(new PushCallback());
                mqttClient.connect(options);
            } catch (Exception e) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, PushPayload payload) {
        publish(0, false, topic, payload);
    }

    public void publish(int qos, boolean retaied, String topic, PushPayload payload) {

        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retaied);
        message.setPayload(payload.toString().getBytes());
        MqttTopic mqttTopic = mqttClient.getTopic(topic);

        if (null == mqttTopic) {
            log.error("topic not exist.");
            return;
        }
        MqttDeliveryToken token;
        try {
            token = mqttTopic.publish(message);
            token.waitForCompletion();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic) {
        subscribe(topic, 0);
    }

    public void subscribe(String topic, int qos) {
        try {
            mqttClient.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
