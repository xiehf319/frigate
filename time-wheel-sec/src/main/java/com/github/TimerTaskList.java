package com.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class TimerTaskList implements Delayed {

    private final TimerTaskEntry root;

    AtomicLong expiration = new AtomicLong(-1);

    private final AtomicInteger taskCounter;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public TimerTaskList(AtomicInteger taskCounter) {
        this.taskCounter = taskCounter;
        this.root = new TimerTaskEntry(null, -1);
        this.root.next = root;
        this.root.prev = root;
    }

    public Boolean setExpiration(Long expirationMs) {
        return expiration.getAndSet(expirationMs) != expirationMs;
    }

    public Long getExpiration() {
        return expiration.get();
    }

    public synchronized void foreach(Consumer<TimerTask> taskConsumer) {
        TimerTaskEntry entry = root.next;
        while (!entry.equals(root)) {
            TimerTaskEntry nextEntry = entry.next;
            if (!entry.cancelled()) {
                taskConsumer.accept(entry.timeTask);
            }
            entry = nextEntry;
        }
    }


    public synchronized void add(TimerTaskEntry timerTaskEntry) {
        boolean done = false;
        while (!done) {
            timerTaskEntry.remove();
            synchronized (this) {
                if (timerTaskEntry.list == null) {
                    TimerTaskEntry tail = root.prev;
                    timerTaskEntry.next = root;
                    timerTaskEntry.prev = tail;
                    timerTaskEntry.list = this;
                    tail.next = timerTaskEntry;
                    root.prev = timerTaskEntry;
                    taskCounter.incrementAndGet();
                    done = true;
                }

            }
        }
    }

    public synchronized void remove(TimerTaskEntry timerTaskEntry) {
        synchronized (this) {
            if (timerTaskEntry.list.compareTo(this) == 0) {
                timerTaskEntry.next.prev = timerTaskEntry.prev;
                timerTaskEntry.prev.next = timerTaskEntry.next;
                timerTaskEntry.next = null;
                timerTaskEntry.prev = null;
                timerTaskEntry.list = null;
                taskCounter.decrementAndGet();
            }
        }
    }

    public synchronized void flush(Consumer<TimerTaskEntry> consumer) {
        TimerTaskEntry head = root.next;
        while (!head.equals(root)) {
            remove(head);
            consumer.accept(head);
            head = root.next;
        }
        expiration.set(-1L);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(Math.max(getExpiration() - System.currentTimeMillis() / 1000, 0), TimeUnit.SECONDS);
    }

    @Override
    public int compareTo(Delayed d) {
        TimerTaskList other = (TimerTaskList) d;
        if (getExpiration() < other.getExpiration()) {
            return -1;
        } else if (getExpiration() > other.getExpiration()) {
            return 1;
        }
        return 0;
    }

}


class TimerTaskEntry implements Comparable<TimerTaskEntry> {

    TimerTask timeTask;

    volatile TimerTaskList list = null;

    TimerTaskEntry next = null;

    TimerTaskEntry prev = null;

    /**
     * 过期时间
     */
    Long expirationMs;

    public TimerTaskEntry(TimerTask timeTask, long expirationMs) {
        this.timeTask = timeTask;
        this.expirationMs = expirationMs;
        if (timeTask != null) {
            timeTask.setTimerTaskEntry(this);
        }
    }


    public boolean cancelled() {
        return timeTask.getTimerTaskEntry() != this;
    }

    public void remove() {
        TimerTaskList currentList = list;
        while (currentList != null) {
            currentList.remove(this);
            currentList = list;
        }
    }

    @Override
    public int compareTo(TimerTaskEntry that) {
        return this.expirationMs.compareTo(that.expirationMs);
    }
}

class TimerTask implements Runnable {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    long delaySec;

    String name;

    TimerTaskEntry timerTaskEntry;

    public TimerTask(long delaySec, String name) {
        this.delaySec = delaySec;
        this.name = name;
    }

    public void cancel() {
        synchronized (this) {
            if (timerTaskEntry != null) {
                timerTaskEntry.remove();
            }
            timerTaskEntry = null;
        }
    }

    public void setTimerTaskEntry(TimerTaskEntry entry) {
        synchronized (this) {
            if (timerTaskEntry != null && timerTaskEntry != entry) {
                timerTaskEntry.remove();
            }
            timerTaskEntry = entry;
        }
    }

    public TimerTaskEntry getTimerTaskEntry() {
        return timerTaskEntry;
    }

    @Override
    public void run() {
        long now = System.currentTimeMillis() / 1000;
        logger.info("" +
                "指定时间: 【{}】 " +
                "计划延迟s: 【{}】 " +
                "实际时间:【{}】, " +
                "偏差s: 【{}】 " +
                "任务【{}】.",
                timerTaskEntry.expirationMs,
                delaySec,
                now,
                (timerTaskEntry.expirationMs - now),
                name);
    }
}
