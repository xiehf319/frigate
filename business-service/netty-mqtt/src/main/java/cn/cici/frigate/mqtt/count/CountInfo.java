package cn.cici.frigate.mqtt.count;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xiehf
 * @date 2019/6/16 17:50
 * @concat 370693739@qq.com
 **/
@Data
public class CountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 最后次接收到消息
     */
    private long lastReceive;

    /**
     * 最后次发送消息
     */
    private long lastSend;

    /**
     * 最大连接数
     */
    private long maxChannelNum;

    /**
     * 当前连接数
     */
    private long curChannelNum;

    /**
     * 接受消息数
     */
    private AtomicLong receiveNum = new AtomicLong();

    /**
     * 发送消息数
     */
    private AtomicLong sendNum = new AtomicLong();

    /**
     * 收发心跳数
     */
    private AtomicLong heartbeatNum = new AtomicLong();


    public void setCurChannelNum(long curChannelNum) {
        this.curChannelNum = curChannelNum;
        if (this.maxChannelNum < curChannelNum) {
            this.maxChannelNum = curChannelNum;
        }
    }

    public void setLastReceive(long lastReceive) {
        if (this.lastReceive < lastReceive) {
            this.lastReceive = lastReceive;
        }
    }

    public void setLastSend(long lastSend) {
        if (this.lastSend < lastSend) {
            this.lastSend = lastSend;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StatisticInfo [lastReceive=").append(this.lastReceive);
        sb.append(", lastSent=").append(this.lastSend);
        sb.append(", receiveNum=").append(this.receiveNum);
        sb.append(", sentNum=").append(this.sendNum);
        sb.append(", heartbeatNum=").append(this.heartbeatNum);
        sb.append(", maxChannelNum=").append(this.maxChannelNum);
        sb.append(", curChannelNum=").append(this.curChannelNum);
        sb.append("]");
        return sb.toString();
    }
}
