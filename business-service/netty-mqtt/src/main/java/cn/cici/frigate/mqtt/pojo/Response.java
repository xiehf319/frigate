package cn.cici.frigate.mqtt.pojo;

import lombok.Data;

/**
 * @author xiehf
 * @date 2019/6/16 10:38
 * @concat 370693739@qq.com
 **/
@Data
public class Response extends BaseMessage{

    private static final long serialVersionUID = 1L;

    private static final int EXCEPTION = -1;

    public static final int SUCCESS = 0;

    private int code;

    private Object result;

    private Throwable cause;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Response [sequence=");
        sb.append(sequence);
        sb.append(", code=");
        sb.append(code);
        sb.append(", result=");
        sb.append(result);
        sb.append("]");
        return sb.toString();
    }
}
