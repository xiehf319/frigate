package cn.cici.frigate.mqtt.starter.service.server;

import cn.cici.frigate.mqtt.starter.service.EventDispatcher;
import cn.cici.frigate.mqtt.starter.service.WrappedChannel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServerDispatchHandler extends ChannelHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ServerDispatchHandler.class);

    protected EventDispatcher eventDispatcher;

    public ServerDispatchHandler(EventDispatcher eventDispatcher) {
        if (eventDispatcher == null) {
            throw new IllegalArgumentException("eventDispatcher cannot be null.");
        }

        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        if (logger.isDebugEnabled()) {
            logger.debug("Message received from channel '{} : '{}'.", channelId, msg);
        }
        WrappedChannel channel = ((Server) eventDispatcher.getService()).getChannel(channelId);
        eventDispatcher.dispatchMessageEvent(ctx, channel, msg);
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        WrappedChannel channel = new WrappedChannel(ctx.channel());
        String channelId = channel.id().asShortText();
        if (logger.isDebugEnabled()) {
            logger.debug("Connected on channel '{}'.", channelId);
        }
        logger.info("有新连接接入: {}, address: {}", channelId, channel.remoteAddress().toString());
        ((Server) eventDispatcher.getService()).getChannels().put(channelId, channel);
        eventDispatcher.dispatchChannelEvent(ctx, channel);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        closeChannel(ctx);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        WrappedChannel channel = ((Server) eventDispatcher.getService()).getChannel(channelId);
        if (channel != null) {
            eventDispatcher.dispatchExceptionCaught(ctx, channel, cause);
        }

        // 处理IOException，主动关闭channel
        if (cause instanceof IOException) {
            ctx.close();
            closeChannel(ctx);
        }
    }

    private void closeChannel(ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asShortText();
        WrappedChannel channel = ((Server) eventDispatcher.getService()).getChannels().remove(channelId);
        if (channel != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Channel '{}' was closed.", channelId);
            }

            eventDispatcher.dispatchChannelEvent(ctx, channel);
        }
        ctx.close();
    }

}