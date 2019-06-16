package cn.cici.frigate.mqtt.service;

import cn.cici.frigate.mqtt.listener.ChannelEventListener;
import cn.cici.frigate.mqtt.listener.EventBehavior;
import cn.cici.frigate.mqtt.listener.ExceptionEventListener;
import cn.cici.frigate.mqtt.listener.MessageEventListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.EventListener;

/**
 * @author xiehf
 * @date 2019/6/16 15:00
 * @concat 370693739@qq.com
 **/
@Slf4j
public class EventDispatcher {

    private Service service;

    public EventDispatcher(Service service) {
        if (service == null) {
            throw new IllegalArgumentException("service is null.");
        }
        this.service = service;
    }

    private void doMessageEvent(final ChannelHandlerContext ctx, final WrappedChannel channel, final Object msg) {
        try {
            for (EventListener listener : service.getEventListeners()) {
                if (listener instanceof MessageEventListener) {
                    EventBehavior eventBehavior = ((MessageEventListener) listener).channelRead(ctx, channel, msg);
                    if (EventBehavior.BREAK.equals(eventBehavior)) {
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            dispatchExceptionCaught(ctx, channel, ex);
        } finally {
        }
    }

    private void doChannelEvent(final ChannelHandlerContext ctx, final WrappedChannel channel) {
        final boolean isConnected = (channel != null && channel.isActive());
        try {
            for (EventListener listener : service.getEventListeners()) {
                if (listener instanceof ChannelEventListener) {
                    ChannelEventListener channelEventListener = (ChannelEventListener) listener;

                    EventBehavior eventBehavior;
                    if (isConnected) {
                        eventBehavior = channelEventListener.channelActive(ctx, channel);
                    } else {
                        eventBehavior = channelEventListener.channelInactive(ctx, channel);
                    }

                    if (EventBehavior.BREAK.equals(eventBehavior)) {
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            dispatchExceptionCaught(ctx, channel, ex);
        }
    }

    private void doExceptionEvent(final ChannelHandlerContext ctx, final WrappedChannel channel, final Throwable cause) {
        try {
            for (EventListener listener : service.getEventListeners()) {
                if (listener instanceof ExceptionEventListener) {
                    ExceptionEventListener exEventListener = (ExceptionEventListener) listener;

                    EventBehavior eventBehavior = exEventListener.exceptionCaught(ctx, channel, cause);
                    if (EventBehavior.BREAK.equals(eventBehavior)) {
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Failed to dispatch exception event on channel '{}'.", channel.id().asShortText(), ex);
        }
    }

    public void dispatchMessageEvent(final ChannelHandlerContext ctx, final WrappedChannel channel, final Object msg) {
        if (service.isOpenExecutor()) {
            service.messageExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    doMessageEvent(ctx, channel, msg);
                }
            });
        } else {
            doMessageEvent(ctx, channel, msg);
        }
    }

    public void dispatchChannelEvent(final ChannelHandlerContext ctx, final WrappedChannel channel) {
        // 只处理通道Connected事件
        if (!channel.isActive()) {
            return;
        }
        if (service.isOpenExecutor()) {
            service.channelExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    doChannelEvent(ctx, channel);
                }
            });
        } else {
            doChannelEvent(ctx, channel);
        }
    }

    public void dispatchExceptionCaught(final ChannelHandlerContext ctx, final WrappedChannel channel, final Throwable cause) {
        if (service.isOpenExecutor()) {
            service.exceptionExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    doExceptionEvent(ctx, channel, cause);
                }
            });
        } else {
            doExceptionEvent(ctx, channel, cause);
        }
    }

    public Service getService() {
        return service;
    }

}
