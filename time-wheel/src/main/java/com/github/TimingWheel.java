package com.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title: frigate
 * @Package com.github
 * @Description: (用一句话描述该文件做什么)
 * @Author: 003300
 * @Date: 2021/2/20
 * @Version V1.0
 * @Copyright: 2020 Shenzhen Hive Box Technology Co.,Ltd All rights reserved.
 * @Note: This content is limited to the internal circulation of Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
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


    public Boolean add(TimerTaskEntry timerTaskEntry) {
        long expiration = timerTaskEntry.expirationSec;

        if (timerTaskEntry.cancelled()) {
            return false;
        }
        if (expiration < currentTime + tickSec) {
            logger.info("任务过期了");
            return false;
        }
        if (expiration < currentTime + interval) {
            long virtualId = expiration / tickSec;
            int index = (int) (virtualId % wheelSize);
            TimerTaskList bucket = buckets[index];
            bucket.add(timerTaskEntry);
            if (bucket.setExpiration(virtualId * tickSec)) {
                logger.info("加入一个任务:{}", timerTaskEntry.timeTask.name);
                queue.offer(bucket);
            }
            return true;
        } else {
            logger.info("升级");
            if (overflowWheel == null) {
                addOverflowWheel();
            }
            return overflowWheel.add(timerTaskEntry);
        }
    }


    public void advanceClock(Long timeSec) {
        logger.info("timeSec {} -> currentTime {}", timeSec, currentTime);
        if (timeSec >= currentTime + tickSec) {
            currentTime = timeSec - (timeSec % tickSec);
            logger.info("currentTime {}", currentTime);
            if (overflowWheel != null) {
                overflowWheel.advanceClock(currentTime);
            }
        }
    }
}
