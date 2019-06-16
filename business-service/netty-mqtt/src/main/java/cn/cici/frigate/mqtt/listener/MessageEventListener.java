package cn.cici.frigate.mqtt.listener;

import cn.cici.frigate.mqtt.service.WrappedChannel;
import io.netty.channel.ChannelHandlerContext;

import java.util.EventListener;

/**
 * 消息监听器
 *
 */
public interface MessageEventListener extends EventListener {
    /**
     * 接收消息
     *
     * @param ctx
     * @param channel
     * @param msg
     * @return
     */
    EventBehavior channelRead(ChannelHandlerContext ctx, WrappedChannel channel, Object msg);
}
