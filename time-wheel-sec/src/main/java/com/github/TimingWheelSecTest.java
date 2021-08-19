package com.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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
public class TimingWheelSecTest {

    private static final Logger logger = LoggerFactory.getLogger(TimingWheelSecTest.class);

    public static void main(String[] args) {
        SystemTimer systemTimer = new SystemTimer("timing-wheel", 1, 20, System.currentTimeMillis() / 1000);
        new DelayedOperationPurgatory(systemTimer);
        for (int i = 0; i < 10; i++) {
            int delaySec = new Random().nextInt(100);
            String name = "任务" + i;
            logger.info("创建任务: {} 延迟 {}s", name, delaySec);
            TimerTask timerTask = new TimerTask(delaySec, name);
            systemTimer.add(timerTask);
        }
    }
}
