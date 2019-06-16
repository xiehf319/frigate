package cn.cici.frigate.mqtt.service.normal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.cici.frigate.mqtt.listener.EventBehavior;
import cn.cici.frigate.mqtt.listener.MessageEventListener;
import cn.cici.frigate.mqtt.pojo.Request;
import cn.cici.frigate.mqtt.pojo.Response;
import cn.cici.frigate.mqtt.service.WrappedChannel;
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
