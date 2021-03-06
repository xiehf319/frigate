package com.github;

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

    public static void main(String[] args) {
        SystemTimer systemTimer = new SystemTimer("timing-wheel", 1, 20, System.currentTimeMillis() / 1000);
        new DelayedOperationPurgatory(systemTimer);
        for (int i = 0; i < 10; i++) {
            TimerTask timerTask = new TimerTask((long)new Random().nextInt(100), i + "");
            systemTimer.add(timerTask);
        }
    }
}
