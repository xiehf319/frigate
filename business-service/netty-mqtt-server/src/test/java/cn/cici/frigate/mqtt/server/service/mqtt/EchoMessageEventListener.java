package cn.cici.frigate.mqtt.server.service.mqtt;

import cn.cici.frigate.mqtt.starter.listener.DefaultMqttMessageEventListener;
import cn.cici.frigate.mqtt.starter.service.WrappedChannel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @date 2019/6/16 15:10
 */
public class EchoMessageEventListener extends DefaultMqttMessageEventListener {
    private static final Logger logger = LoggerFactory.getLogger(EchoMessageEventListener.class);

    @Override
    public void publish(WrappedChannel channel, MqttPublishMessage msg) {
        String topic = msg.variableHeader().topicName();
        ByteBuf buf = msg.content().duplicate();
        byte[] tmp = new byte[buf.readableBytes()];
        buf.readBytes(tmp);
        String content = new String(tmp);

        MqttPublishMessage sendMessage = (MqttPublishMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBLISH, false, MqttQoS.AT_MOST_ONCE, false, 0),
                new MqttPublishVariableHeader(topic, 0),
                Unpooled.buffer().writeBytes(new String(content.toUpperCase()).getBytes()));
        channel.writeAndFlush(sendMessage);
    }
}
