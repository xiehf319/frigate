package com.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TimingWheel {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    Long tickSec;

    int wheelSize;

    Long startMs;

    AtomicInteger taskCounter;

    DelayQueue<TimerTaskList> queue;

    Long interval;

    Long currentTime;

    /**
     * 父级时间轮
     */
    volatile TimingWheel overflowWheel;

    TimerTaskList[] buckets;

    public TimingWheel(Long tickSec, int wheelSize, Long startMs, AtomicInteger taskCounter, DelayQueue<TimerTaskList> queue) {
        this.tickSec = tickSec;
        this.wheelSize = wheelSize;
        this.startMs = startMs;
        this.taskCounter = taskCounter;
        this.queue = queue;

        interval = tickSec * wheelSize;
        currentTime = startMs - (startMs % tickSec);
        buckets = new TimerTaskList[wheelSize];
        for (int i = 0; i < wheelSize; i++) {
            buckets[i] = new TimerTaskList(taskCounter);
        }
    }

    public synchronized void addOverflowWheel() {
        if (overflowWheel == null) {
            overflowWheel = new TimingWheel(
                interval,
                wheelSize,
                currentTime,
                taskCounter,
                queue
            );
        }
    }


    public boolean add(TimerTaskEntry timerTaskEntry) {
        long expiration = timerTaskEntry.expirationMs;

        if (timerTaskEntry.cancelled()) {
            return false;
        }
        if (expiration < currentTime + tickSec) {
            return false;
        }
        if (expiration < currentTime + interval) {
            long virtualId = expiration / tickSec;
            int index = (int) (virtualId % wheelSize);
            TimerTaskList bucket = buckets[index];
            bucket.add(timerTaskEntry);
            if (bucket.setExpiration(virtualId * tickSec)) {
                queue.offer(bucket);
            }
            return true;
        } else {
            if (overflowWheel == null) {
                addOverflowWheel();
            }
            return overflowWheel.add(timerTaskEntry);
        }
    }


    public void advanceClock(Long timeSec) {
        if (timeSec >= currentTime + tickSec) {
            currentTime = timeSec - (timeSec % tickSec);
            if (overflowWheel != null) {
                overflowWheel.advanceClock(currentTime);
            }
        }
    }
}
