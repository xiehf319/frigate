package cn.cici.frigate.mqtt.center;

import cn.cici.frigate.mqtt.starter.listener.EventBehavior;
import cn.cici.frigate.mqtt.starter.listener.MessageEventListener;
import cn.cici.frigate.mqtt.starter.pojo.Request;
import cn.cici.frigate.mqtt.starter.pojo.Response;
import cn.cici.frigate.mqtt.starter.service.WrappedChannel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date 2019/6/16 14:48
 */
@Slf4j
public class CenterMockMessageEventListener implements MessageEventListener {
    private static final Logger logger = LoggerFactory.getLogger(CenterMockMessageEventListener.class);

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
                    JSONObject json = new JSONObject();
                    JSONArray ret = new JSONArray();
                    JSONObject row1 = new JSONObject();
                    row1.put("ip", "127.0.0.1");
                    row1.put("port", 8000);
                    JSONObject row2 = new JSONObject();
                    row2.put("ip", "127.0.0.1");
                    row2.put("port", 8010);
                    ret.add(row1);
                    ret.add(row2);
                    json.put("server_list", ret);
                    response.setResult(json.toString());
                } else if (action.equalsIgnoreCase("updateConnects")) {
                   log.info("update connects from ip {}, port {}", jsonObject.getString("ip"), jsonObject.getString("port"));
                } else if (action.equalsIgnoreCase("register")) {

                }
            }
            channel.writeAndFlush(response);
        }
        return EventBehavior.CONTINUE;
    }
}
