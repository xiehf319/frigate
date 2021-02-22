package com.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

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
public interface Timer {

    void add(TimerTask timerTask);

    Boolean advanceClock(Long timeoutMs);

    int size();

    void shutDown();
}

class SystemTimer implements Timer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    String executorName;

    long tickSec;

    int wheelSize;

    long startSec;

    DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();

    AtomicInteger taskCounter = new AtomicInteger(0);

    TimingWheel timingWheel;

    ExecutorService taskExecutor = Executors.newFixedThreadPool(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return KafkaThread.nonDaemon("executor-" + executorName, r);
        }
    });

    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    public SystemTimer(String executorName, long tickSec, int wheelSize, long startSec) {
        this.executorName = executorName;
        this.tickSec = tickSec;
        this.wheelSize = wheelSize;
        this.startSec = startSec;
        timingWheel = new TimingWheel(
                tickSec,
                wheelSize,
                startSec,
                taskCounter,
                delayQueue
        );
    }

    @Override
    public void add(TimerTask timerTask) {
        readLock.lock();
        try {
            logger.info("加入任务时间: {}", System.currentTimeMillis() / 1000);
            addTimerTaskEntry(new TimerTaskEntry(timerTask, timerTask.delaySec + System.currentTimeMillis() / 1000));
        } finally {
            readLock.unlock();
        }
    }

    private void addTimerTaskEntry(TimerTaskEntry timerTaskEntry) {
        if (!timingWheel.add(timerTaskEntry)) {
            if (!timerTaskEntry.cancelled()) {
                logger.info("执行一个任务 {}", timerTaskEntry.timeTask.name);
                taskExecutor.submit(timerTaskEntry.timeTask);
            }
        }
    }

    Consumer<TimerTaskEntry> reinsert = this::addTimerTaskEntry;

    @Override
    public Boolean advanceClock(Long timeoutMs) {
        try {
            TimerTaskList bucket = delayQueue.poll(timeoutMs, TimeUnit.SECONDS);
            logger.info("阻塞获取一批次");
            if (bucket != null) {
                writeLock.lock();
                try {
                    while (bucket != null) {
                        timingWheel.advanceClock(bucket.getExpiration());
                        logger.info("size: {}", delayQueue.size());
                        bucket.flush(reinsert);
                        bucket = delayQueue.poll();
                        logger.info("再获取一次: {}", bucket == null);
                    }
                } finally {
                    writeLock.unlock();
                }
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int size() {
        return taskCounter.get();
    }

    @Override
    public void shutDown() {
        taskExecutor.shutdown();
    }
}