package cn.cici.frigate.mqtt.starter.listener;

import cn.cici.frigate.mqtt.starter.service.WrappedChannel;
import io.netty.channel.ChannelHandlerContext;

import java.util.EventListener;

/**
 * 通道事件监听器
 *
 */
public interface ChannelEventListener extends EventListener {

    /**
     * 通道连接
     *
     * @param ctx
     * @param channel
     * @return
     */
    EventBehavior channelActive(ChannelHandlerContext ctx, WrappedChannel channel);

    /**
     * 通道关闭
     *
     * @param ctx
     * @param channel
     * @return
     */
    EventBehavior channelInactive(ChannelHandlerContext ctx, WrappedChannel channel);
}
