package cn.cici.frigate.mqtt.starter.listener;

import cn.cici.frigate.mqtt.starter.service.WrappedChannel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class DefaultExceptionListener implements ExceptionEventListener {
    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionListener.class);

    @Override
    public EventBehavior exceptionCaught(ChannelHandlerContext ctx, WrappedChannel channel, Throwable cause) {
        if (cause != null && channel.remoteAddress() != null) {
            logger.warn("Exception caught on channel {}, caused by: '{}'.", channel.id().asShortText(), cause);
        }

        return EventBehavior.CONTINUE;
    }
}
