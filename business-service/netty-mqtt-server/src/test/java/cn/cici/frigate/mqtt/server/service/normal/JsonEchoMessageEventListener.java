package cn.cici.frigate.mqtt.server.service.normal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.cici.frigate.mqtt.starter.listener.EventBehavior;
import cn.cici.frigate.mqtt.starter.listener.MessageEventListener;
import cn.cici.frigate.mqtt.starter.pojo.Request;
import cn.cici.frigate.mqtt.starter.pojo.Response;
import cn.cici.frigate.mqtt.starter.service.WrappedChannel;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * @date 2019/1/3 21:45
 */
public class JsonEchoMessageEventListener implements MessageEventListener {
    @Override
    public EventBehavior channelRead(ChannelHandlerContext ctx, WrappedChannel channel, Object msg) {

        if (msg instanceof Request) {
            Request request = (Request) msg;
            if (request.getMessage() != null) {
                Response response = new Response();
                response.setSequence(request.getSequence());
                response.setCode(0);
                JSONObject data = JSON.parseObject(request.getMessage().toString());
                response.setResult(data.getString("message").toUpperCase());

                channel.writeAndFlush(response);
            }
        }

        return EventBehavior.CONTINUE;
    }
}
