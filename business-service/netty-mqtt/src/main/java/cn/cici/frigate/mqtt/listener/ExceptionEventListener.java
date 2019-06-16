package cn.cici.frigate.mqtt.listener;

import cn.cici.frigate.mqtt.service.WrappedChannel;
import io.netty.channel.ChannelHandlerContext;

import java.util.EventListener;

/**
 * 异常监听器
 */
public interface ExceptionEventListener extends EventListener {
    /**
     * 异常捕获
     *
     * @param ctx
     * @param channel
     * @param cause
     * @return
     */
    EventBehavior exceptionCaught(ChannelHandlerContext ctx, WrappedChannel channel, Throwable cause);
}