package cn.cici.frigate.mqtt.starter.pojo;

import lombok.Data;

/**
 * @author xiehf
 * @date 2019/6/16 10:38
 * @concat 370693739@qq.com
 **/
@Data
public class Request extends BaseMessage {

    private static final long serialVersionUID = 1L;

    private Object message;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request [sequence=");
        sb.append(sequence);
        sb.append(", message=");
        sb.append(message);
        sb.append("]");
        return sb.toString();
    }
}
