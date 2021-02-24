package com.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

//abstract class DelayedOperation extends TimerTask {
//
//    AtomicBoolean completed = new AtomicBoolean(false);
//    AtomicBoolean tryCompletedPending = new AtomicBoolean(false);
//
//    Lock lock = new ReentrantLock();
//
//
//    public DelayedOperation(Long delayMs, Lock lockOpt) {
//        if (lockOpt != null) {
//            this.lock = lock;
//        }
//    }
//
//    public Boolean isCompleted() {
//        return completed.get();
//    }
//
//    abstract void onExpiration();
//
//    abstract void onComplete();
//
//    abstract Boolean tryComplete();
//
//    public Boolean forceComplete() {
//        if (completed.compareAndSet(false, true)) {
//            cancel();
//            onComplete();
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public Boolean maybeTryComplete() {
//        boolean retry = false;
//        boolean done = false;
//        do {
//            if (lock.tryLock()) {
//                try {
//                    tryCompletedPending.set(false);
//                    done = tryComplete();
//                } finally {
//                    lock.unlock();
//                }
//                retry = tryCompletedPending.get();
//            }
//        } while (!isCompleted() && retry);
//        return done;
//    }
//
//    @Override
//    public void run() {
//        if (forceComplete()) {
//            onExpiration();
//        }
//    }
//}

public class DelayedOperationPurgatory {

    Timer timeoutTimer;

    public DelayedOperationPurgatory(Timer timeoutTimer) {
        this.timeoutTimer = timeoutTimer;
        ExpiredOperationReaper expirationReaper = new ExpiredOperationReaper("ExpirationReaper", false);
        expirationReaper.start();
    }

    void advanceClock(long timeoutMs) {
        timeoutTimer.advanceClock(timeoutMs);
    }

    private class ExpiredOperationReaper extends ShutdownableThread {

        public ExpiredOperationReaper(String name, Boolean isInterruptible) {
            super(name, isInterruptible);
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

    Boolean isInterruptible = true;

    volatile private Boolean isStarted = false;

    public ShutdownableThread(String name, Boolean isInterruptible) {
        super(name);
        this.setDaemon(false);
        if (isInterruptible != null) {
            this.isInterruptible = isInterruptible;
        }
    }

    Boolean isShutdownInitiated() {
        return shutdownInitiated.getCount() == 0;
    }

//    Boolean isShutdownComplete() {
//        return shutdownComplete.getCount() == 0;
//    }

    Boolean isRunning() {
        return !isShutdownInitiated();
    }

//    public void shutdown() {
//        initiateShutdown();
//        awaitShutdown();
//    }

//    public Boolean isThreadFailed() {
//        return isShutdownComplete() && !isShutdownInitiated();
//    }
//
//    public Boolean initiateShutdown() {
//        synchronized (this) {
//            if (isRunning()) {
//                logger.info("shutting down");
//                shutdownInitiated.countDown();
//                if (isInterruptible) {
//                    interrupt();
//                }
//                return true;
//            }
//            return false;
//        }
//    }

//    public void awaitShutdown() {
//        if (!isShutdownInitiated()) {
//            throw new IllegalStateException("initiateShutdown was not called before awaitShutdown");
//        } else {
//            if (isStarted) {
//                try {
//                    shutdownComplete.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            logger.info("shutdown completed");
//        }
//    }

//    public void pause(Long timeout, TimeUnit unit) {
//        try {
//            if (shutdownInitiated.await(timeout, unit)) {
//                logger.trace("shutdownInitiated latch count reached zero. shutdown called");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void run() {
        isStarted = true;
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