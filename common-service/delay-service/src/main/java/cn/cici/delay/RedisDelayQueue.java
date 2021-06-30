package cn.cici.delay;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RedisDelayQueue {

    String queueName;

    String targetQueueName;

    String transferLockKey;

    List<String> originQueueName;

    AtomicInteger WORKER_COUNT = new AtomicInteger(0);

    Integer DELAY_WORKER_SIZE = 100;

    public RedisDelayQueue(String queueName, int queueSize) {
        if (WORKER_COUNT.incrementAndGet() >= DELAY_WORKER_SIZE) {

        }
        this.queueName = queueName;
    }
}
