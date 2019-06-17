package cn.cici.frigate.mqtt.starter.codec;

import cn.cici.frigate.mqtt.starter.pojo.BaseMessage;
import cn.cici.frigate.mqtt.starter.pojo.Heartbeat;
import cn.cici.frigate.mqtt.starter.pojo.Request;
import cn.cici.frigate.mqtt.starter.pojo.Response;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author xiehf
 * @date 2019/6/16 10:51
 * @concat 370693739@qq.com
 **/
@ChannelHandler.Sharable
public class JsonEncoder extends MessageToMessageEncoder<BaseMessage> {

    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";
    private static final String HEARTBEAT = "heartbeat";

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BaseMessage message, List<Object> list) throws Exception {
        if (message instanceof Request) {
            Request request = (Request) message;
            JSONObject json = new JSONObject();
            json.put("type", REQUEST);
            json.put("message", request.getMessage());
            json.put("sequence", request.getSequence());
            ByteBuf buf = Unpooled.copiedBuffer(json.toString().getBytes());
            list.add(buf);
        } else if (message instanceof Response) {
            Response response = (Response) message;
            JSONObject json = new JSONObject();
            json.put("type", RESPONSE);
            json.put("code", response.getCode());
            json.put("result", response.getResult());
            json.put("sequence", response.getSequence());
            ByteBuf buf = Unpooled.copiedBuffer(json.toString().getBytes());
            list.add(buf);
        } else if (message instanceof Heartbeat) {
            JSONObject json = new JSONObject();
            json.put("type", HEARTBEAT);
            ByteBuf buf = Unpooled.copiedBuffer(json.toString().getBytes());
            list.add(buf);
        } else {
            throw new CodecException("unknown message type: " + message);
        }
    }
}
