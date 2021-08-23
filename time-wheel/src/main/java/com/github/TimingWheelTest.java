package com.github;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class TimingWheelTest {

    public static void main(String[] args) {
        Timer systemTimer = new SystemTimer("timing-wheel", 1, 20, System.currentTimeMillis());
        new DelayedOperationPurgatory(systemTimer);
        for (int i = 0; i < 20; i++) {
            TimerTask timerTask = new TimerTask(new Random().nextInt(5000), i + "") {
                @Override
                public void run() {
                    long now = System.currentTimeMillis();
                    logger.info("" +
                                    "指定时间: 【{}】 " +
                                    "计划延迟ms: 【{}】 " +
                                    "实际时间:【{}】, " +
                                    "偏差ms: 【{}】 " +
                                    "任务【{}】.",
                            timerTaskEntry.expirationMs,
                            delayMs,
                            now,
                            (timerTaskEntry.expirationMs - now),
                            name);
                }
            };
            systemTimer.add(timerTask);
        }
    }
}
