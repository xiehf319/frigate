package com.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DelayedOperationPurgatory {

    Timer timeoutTimer;

    public DelayedOperationPurgatory(Timer timeoutTimer) {
        this.timeoutTimer = timeoutTimer;
        ExpiredOperationReaper expirationReaper = new ExpiredOperationReaper("ExpirationReaper");
        expirationReaper.start();
    }

    void advanceClock(long timeoutMs) {
        timeoutTimer.advanceClock(timeoutMs);
    }

    private class ExpiredOperationReaper extends ShutdownableThread {

        public ExpiredOperationReaper(String name) {
            super(name);
        }

        @Override
        public void doWork() {
            advanceClock(200L);
        }
    }
}


abstract class ShutdownableThread extends Thread {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    CountDownLatch shutdownInitiated = new CountDownLatch(1);

    CountDownLatch shutdownComplete = new CountDownLatch(1);

    public ShutdownableThread(String name) {
        super(name);
        this.setDaemon(false);
    }

    Boolean isShutdownInitiated() {
        return shutdownInitiated.getCount() == 0;
    }


    Boolean isRunning() {
        return !isShutdownInitiated();
    }

    @Override
    public void run() {
        logger.info("Starting");
        try {
            while (isRunning()) {
                doWork();
            }
        } catch (Error e) {
            shutdownInitiated.countDown();
            shutdownComplete.countDown();
            logger.info("stopped");
            System.exit(1);
        } catch (Throwable t) {
            if (isRunning()) {
                logger.error("error due to", t);
            }
        } finally {
            shutdownComplete.countDown();
        }
        logger.info("Stopped");
    }

    public abstract void doWork();
}