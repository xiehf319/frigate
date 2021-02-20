package com.github;

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
public class TimingWheelTest {

    public static void main(String[] args) {
        SystemTimer systemTimer = new SystemTimer("timing-wheel", 1 , 20, System.nanoTime() / 1000);
        new DelayedOperationPurgatory<>(systemTimer);
//        TimerTask timerTask0 = new TimerTask(12, "0--12");
//        systemTimer.add(timerTask0);
//
//        TimerTask timerTask1 = new TimerTask(58, "1--58");
//        systemTimer.add(timerTask1);
//
//        TimerTask timerTask2 = new TimerTask(190, "2--190");
//        systemTimer.add(timerTask2);
//
//        TimerTask timerTask3 = new TimerTask(621, "3--620");
//        systemTimer.add(timerTask3);

        TimerTask timerTask4 = new TimerTask(1500, "4--1500");
        systemTimer.add(timerTask4);
    }
}
