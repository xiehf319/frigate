package cn.cici.frigate.mqtt.codec;

import cn.cici.frigate.mqtt.pojo.Heartbeat;
import cn.cici.frigate.mqtt.pojo.Request;
import cn.cici.frigate.mqtt.pojo.Response;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author xiehf
 * @date 2019/6/16 10:31
 * @concat 370693739@qq.com
 **/
@ChannelHandler.Sharable
public class JsonDecoder extends MessageToMessageDecoder<ByteBuf> {

    private static final String REQUEST = "request";

    private static final String RESPONSE = "response";

    private static final String HEARTBEAT = "heartbeat";

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            byte[] tmp = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(tmp);
            String jsonString = new String(tmp);

            JSONObject json = JSON.parseObject(jsonString);
            String type = json.getString("type");
            if (REQUEST.equals(type)) {
                Request request = new Request();
                request.setSequence(json.getIntValue("sequence"));
                request.setMessage(json.getString("message"));
                list.add(request);
            }else if (RESPONSE.equals(type)) {
                Response response = new Response();
                response.setSequence(json.getIntValue("sequence"));
                response.setCode(json.getIntValue("code"));
                response.setResult(json.get("result"));
                list.add(response);
            } else if (HEARTBEAT.equals(type)) {
                list.add(Heartbeat.getSingleton());
            }
        } catch (Exception ex) {
            throw new CodecException();
        }
    }
}
