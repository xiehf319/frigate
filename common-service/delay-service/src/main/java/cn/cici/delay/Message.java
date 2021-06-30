package cn.cici.delay;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Message implements Delayed {

    String body;
    long executeTime;

    public String getBody() {
        return body;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public Message(String body, long delayTime) {
        this.body = body;
        this.executeTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.executeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        Message msg = (Message) o;
        return this.body.compareTo(msg.body);
    }
}
