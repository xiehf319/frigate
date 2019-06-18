package cn.cici.frigate.mqtt.push.client;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @description:
 * @createDate:2019/6/18$11:38$
 * @author: Heyfan Xie
 */
@Slf4j
public class PushCallback implements MqttCallback {


    @Override
    public void connectionLost(Throwable throwable) {
        log.info("连接断开");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("消息接口主题: {}", topic);
        log.info("消息接口Qos: {}", mqttMessage.getQos());
        log.info("消息接口内容: {}", new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("deliveryComplete {}", iMqttDeliveryToken.isComplete());
    }
}
