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
        return unit.convert(Math.max(getExpiration() - System.currentTimeMillis(), 0), TimeUnit.MILLISECONDS);
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


    public Boolean cancelled() {
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

abstract class TimerTask implements Runnable {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    long delayMs;

    String name;

    TimerTaskEntry timerTaskEntry;

    public TimerTask(long delayMs, String name) {
        this.delayMs = delayMs;
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
}
