package cn.cici.frigate.mqtt.center;

import cn.cici.frigate.mqtt.starter.listener.EventBehavior;
import cn.cici.frigate.mqtt.starter.listener.MessageEventListener;
import cn.cici.frigate.mqtt.starter.pojo.Request;
import cn.cici.frigate.mqtt.starter.pojo.Response;
import cn.cici.frigate.mqtt.starter.service.WrappedChannel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @date 2019/6/16 14:48
 */
@Slf4j
public class CenterMockMessageEventListener implements MessageEventListener {
    private static final Logger logger = LoggerFactory.getLogger(CenterMockMessageEventListener.class);

    ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    @Override
    public EventBehavior channelRead(ChannelHandlerContext ctx, WrappedChannel channel, Object msg) {
        if (msg instanceof Request) {
            Request request = (Request) msg;

            if (logger.isDebugEnabled()) {
                logger.debug("Message received: '{}'.", request.toString());
            }

            Response response = new Response();
            response.setSequence(request.getSequence());
            response.setCode(0);

            if (request.getMessage() != null) {
                JSONObject jsonObject = JSON.parseObject(request.getMessage().toString());
                String action = jsonObject.getString("action");
                if (action.equalsIgnoreCase("getServerInfo")) {
                    List<Map<String, Object>> list = new ArrayList<>();
                    channelMap.forEach((key, value) -> {
                        Map<String, Object> map = new HashMap<>(4);
                        map.put("channelId", key);
                        map.put("clientId", channel.getChannel().attr(AttributeKey.valueOf("clientId")));
                        map.put("address", channel.remoteAddress());
                        list.add(map);
                    });
                    response.setResult(list);
                } else if (action.equalsIgnoreCase("updateConnects")) {
                   log.info("update connects from ip {}, port {}", jsonObject.getString("ip"), jsonObject.getString("port"));
                } else if (action.equalsIgnoreCase("register")) {

                }
                channelMap.put(channel.id().asLongText(), channel);
            }
            channel.writeAndFlush(response);
        }
        return EventBehavior.CONTINUE;
    }
}
