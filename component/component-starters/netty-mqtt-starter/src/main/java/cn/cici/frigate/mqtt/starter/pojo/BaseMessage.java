package cn.cici.frigate.mqtt.starter.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiehf
 * @date 2019/6/16 10:39
 * @concat 370693739@qq.com
 **/
@Data
public class BaseMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int sequence;

}
