package cn.cici.frigate.component.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @createDate:2019/6/20$17:41$
 * @author: Heyfan Xie
 */
@Slf4j
public class CustomerThreadPoolExecutor {

    private ThreadPoolExecutor poolExecutor = null;

    /**
     * 假设预估任务每秒并发为500-1000
     * 每个任务耗时0.1s
     * 系统允许最大响应时间为1s
     * <p>
     * threadCount = (500-1000) * 0.1 = 50-100个线程并发，8020原则那么取 50 + 50 * 0.8 = 90
     */
    @Value("${thread.pool.corePoolSize: 90}")
    private int corePoolSize;

    /**
     * （最大任务数-队列容量）/每个线程每秒处理能力 = 最大线程数
     * (最大tasks - 队列) / (1/ 任务耗时) = 1000 - 90 / 10 = 91
     */
    @Value("${thread.pool.maxPoolSize: 91}")
    private int maxPoolSize;

    /**
     * 默认 60s
     */
    @Value("${thread.pool.keepAliveTime: 60}")
    private int keepAliveTime;

    /**
     * （corePoolSize/任务耗时）* 响应时间
     * (90 / 0.1) * 10 = 90
     * todo
     */
    @Value("${thread.pool.queueCapacity: 90}")
    private int queueCapacity;

    private BlockingQueue<Runnable> queue;

    public CustomerThreadPoolExecutor() {
    }

    @PostConstruct
    public void init() {
        queue = new LinkedBlockingQueue<>(queueCapacity);
        poolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                queue,
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler()
        );
    }

    public void destory() {
        if (poolExecutor != null) {
            poolExecutor.shutdownNow();
        }
    }

    /**
     * 获取线程池
     *
     * @return
     */
    public ExecutorService getCustomerThreadPoolExecutor() {
        return poolExecutor;
    }

    private class CustomThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = CustomerThreadPoolExecutor.class.getSimpleName() + count.addAndGet(1);
            t.setName(threadName);
            return t;
        }
    }

    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        }
    }
}
