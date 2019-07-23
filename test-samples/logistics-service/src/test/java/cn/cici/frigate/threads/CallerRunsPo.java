package cn.cici.frigate.threads;

import java.util.concurrent.*;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/22 14:44
 * @author: Heyfan Xie
 */
public class CallerRunsPo {

    public static void main(String[] args) {
        BlockingQueue<Runnable> workingQueue = new LinkedBlockingQueue<Runnable>(10);
        RejectedExecutionHandler rejectedExecutionHandler =
                new ThreadPoolExecutor.CallerRunsPolicy();
        ExecutorService threadPool = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
                workingQueue, rejectedExecutionHandler);

        for (int i = 0; i < 100; i++) {
            final int threadNo = i + 1;
            threadPool.submit(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    System.out.println("thread " + String.valueOf(threadNo) + " is called");
                    Thread.sleep(10000);
                    System.out.println("thread " + String.valueOf(threadNo) + " is awake");
                    throw new Exception();
                }

            });
        }
        threadPool.shutdown();
    }
}
