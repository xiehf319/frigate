package cn.cici.frigate.mqtt.count;

import cn.cici.frigate.mqtt.service.server.Server;
import cn.cici.frigate.mqtt.service.server.ServerContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xiehf
 * @date 2019/6/16 17:56
 * @concat 370693739@qq.com
 **/
@ChannelHandler.Sharable
public class CountHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server server = ServerContext.getContext().getServer();
        server.getCountInfo().setCurChannelNum(server.getChannels().size());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Server server = ServerContext.getContext().getServer();
        server.getCountInfo().setCurChannelNum(server.getChannels().size());
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CountInfo countInfo = ServerContext.getContext().getServer().getCountInfo();
        countInfo.getReceiveNum().incrementAndGet();
        countInfo.setLastReceive(System.currentTimeMillis());
        super.channelRead(ctx, msg);
    }
}
