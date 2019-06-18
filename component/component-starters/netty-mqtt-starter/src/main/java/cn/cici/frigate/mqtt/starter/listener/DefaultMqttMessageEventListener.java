package cn.cici.frigate.mqtt.starter.listener;

import cn.cici.frigate.mqtt.starter.service.WrappedChannel;
import cn.cici.frigate.mqtt.starter.service.server.Server;
import cn.cici.frigate.mqtt.starter.service.server.ServerContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class DefaultMqttMessageEventListener implements MessageEventListener {
    private static final Logger logger = LoggerFactory.getLogger(DefaultMqttMessageEventListener.class);

    @Override
    public EventBehavior channelRead(ChannelHandlerContext ctx, WrappedChannel channel, Object msg) {
        if (msg instanceof MqttMessage) {
            MqttMessage message = (MqttMessage) msg;
            MqttMessageType messageType = message.fixedHeader().messageType();
            switch (messageType) {
                // 客户端--服务端  客户端连接服务端
                case CONNECT:
                    this.connect(channel, (MqttConnectMessage) message);
                    break;
                // 双向   发布消息
                case PUBLISH:
                    this.publish(channel, (MqttPublishMessage) message);
                    break;
                // 双向   Qos1消息发布收到确认
                case PUBACK:
                    break;
                // 双向   发布收到(保证交付第一步)
                case PUBREC:
                    break;
                // 双向   发布释放(保证交付第二步)
                case PUBREL:
                    break;
                // 双向   QoS2消息发布完成(保证交互第三步)
                case PUBCOMP:
                    break;
                // 客户端--服务端  客户端订阅请求
                case SUBSCRIBE:
                    this.subscribe(channel, (MqttSubscribeMessage) message);
                    break;
                // 客户端--服务端  客户端取消订阅请求
                case UNSUBSCRIBE:
                    this.unSubscribe(channel, (MqttUnsubscribeMessage) message);
                    break;
                // 客户端--服务端 心跳请求
                case PINGREQ:
                    this.pingReq(channel, message);
                    break;
                // 客户端--服务端 客户端断开连接
                case DISCONNECT:
                    this.disConnect(channel, message);
                    break;
                default:
                    if (logger.isDebugEnabled()) {
                        logger.debug("Nonsupport server message  type of '{}'.", messageType);
                    }
                    break;
            }
        }
        return EventBehavior.CONTINUE;
    }

    public void connect(WrappedChannel channel, MqttConnectMessage msg) {
        if (logger.isDebugEnabled()) {
            String clientId = msg.payload().clientIdentifier();
            logger.debug("MQTT CONNECT received on channel '{}', clientId is '{}'.",
                    channel.id().asShortText(), clientId);
        }

        MqttConnAckMessage okResp = (MqttConnAckMessage) MqttMessageFactory.newMessage(new MqttFixedHeader(
                        MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED), null);
        channel.writeAndFlush(okResp);
    }

    public void pingReq(WrappedChannel channel, MqttMessage msg) {
        if (logger.isDebugEnabled()) {
            logger.debug("MQTT pingReq received.");
        }

        Server server = ServerContext.getContext().getServer();
        if (server != null) {
            server.getCountInfo().getHeartbeatNum().incrementAndGet();
        }

        MqttMessage pingResp = new MqttMessage(new MqttFixedHeader(MqttMessageType.PINGRESP, false,
                MqttQoS.AT_MOST_ONCE, false, 0));
        channel.writeAndFlush(pingResp);
    }

    public void disConnect(WrappedChannel channel, MqttMessage msg) {
        if (channel.isActive()) {
            channel.close();

            if (logger.isDebugEnabled()) {
                logger.debug("MQTT channel '{}' was closed.", channel.id().asShortText());
            }
        }
    }

    public void publish(WrappedChannel channel, MqttPublishMessage msg) {
    }

    public void subscribe(WrappedChannel channel, MqttSubscribeMessage msg) {
        MqttSubAckMessage subAckMessage = (MqttSubAckMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.SUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(msg.variableHeader().messageId()),
                new MqttSubAckPayload(0));
        channel.writeAndFlush(subAckMessage);
    }

    public void unSubscribe(WrappedChannel channel, MqttUnsubscribeMessage msg) {
        MqttUnsubAckMessage unSubAckMessage = (MqttUnsubAckMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.UNSUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(msg.variableHeader().messageId()), null);
        channel.writeAndFlush(unSubAckMessage);
    }

}
