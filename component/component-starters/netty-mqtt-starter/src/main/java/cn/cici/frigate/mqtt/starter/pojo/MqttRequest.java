package cn.cici.frigate.mqtt.starter.pojo;

import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.Data;

/**
 * @author xiehf
 * @date 2019/6/16 10:42
 * @concat 370693739@qq.com
 **/
public class MqttRequest {

    private boolean mutable = true;

    /**
     * 载荷
     */
    private byte[] payload;

    /**
     * 最多一次
     */
    private MqttQoS qos = MqttQoS.AT_MOST_ONCE;

    private boolean retained = false;

    private boolean dup = false;

    private int messageId;

    public MqttRequest() {
        this.setPayload(new byte[0]);
    }

    public MqttRequest(byte[] payload) {
        this.payload = payload;
    }

    public void clearPayload() {
        this.checkMutable();
        this.payload = new byte[0];
    }

    public void setPayload(byte[] payload) {
        this.checkMutable();
        if (payload == null) {
            throw new NullPointerException();
        } else {
            this.payload = payload;
        }
    }

    public boolean isRetained() {
        return retained;
    }

    public void setRetained(boolean retained) {
        this.checkMutable();
        this.retained = retained;
    }

    public void checkMutable()throws IllegalStateException {
        if (!this.mutable) {
            throw new IllegalStateException();
        }
    }

    public MqttQoS getQos() {
        return qos;
    }

    public boolean isMutable() {
        return mutable;
    }

    public void setMutable(boolean mutable) {
        this.mutable = mutable;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setQos(MqttQoS qos) {
        this.qos = qos;
    }

    public boolean isDup() {
        return dup;
    }

    public void setDup(boolean dup) {
        this.dup = dup;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
