package cn.cici.frigate.mqtt.service;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Data;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author xiehf
 * @date 2019/6/16 14:47
 * @concat 370693739@qq.com
 **/
@Data
public abstract class Service {

    /**
     * socket 类型
     */
    protected SocketType socketType = SocketType.NORMAL;

    /**
     * 绑定的端口
     */
    protected int port = 8000;

    /**
     * 多ip情况下绑定指定的IP(可以不设置)
     */
    protected String ip;

    /**
     * 是否启用keepAlive
     */
    protected boolean keepAlive = true;

    /**
     * 是否启用tcpNoDelay
     */
    protected boolean tcpNoDelay = true;

    /**
     * 工作线程池大小
     */
    protected int workerCount;

    /**
     * 是否开启业务处理线程池
     */
    protected boolean openExecutor = false;

    /**
     * 消息事件业务处理线程池
     */
    protected ExecutorService messageExecutor;

    /**
     * 通道事件业务处理线程池
     */
    protected ExecutorService channelExecutor;

    /**
     * 异常事情业务处理线程池
     */
    protected ExecutorService exceptionExecutor;

    /**
     * 消息事件处理线程池最小保持的线程池
     */
    protected int corePoolSize = 10;

    /**
     * 消息事件业务处理线程池最大线程数
     */
    protected int maximumPoolSize = 150;

    /**
     * 消息事件业务处理线程池队列最大值
     */
    protected int queueCapacity = 1000000;

    /**
     * 设置是否心跳检查
     */
    protected boolean checkHeartbeat = true;

    /**
     * 心跳检查时的读空闲时间
     */
    protected int readerIdleTimeSeconds = 0;

    /**
     * 心跳检查时的写空闲事件
     */
    protected int writerIdleTimeSeconds = 0;

    /**
     * 心跳检查时的读写空闲事件
     */
    protected int allIdleTimeSeconds = 90;

    protected IdleStateHandler timeoutHandler;

    protected ChannelHandlerAdapter heartbeatHandler;

    protected LinkedHashMap<String, ChannelHandler> handlers = new LinkedHashMap<>();

    protected List<EventListener> eventListeners = new ArrayList<>();

    protected EventDispatcher eventDispatcher;


    public Service() {
        // 默认工作线程数
        this.workerCount = Runtime.getRuntime().availableProcessors() + 1;
        // 添加JVM关闭时的钩子
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(this));
    }

    protected void init() {
        if (openExecutor) {
            messageExecutor = new ThreadPoolExecutor(
                    this.corePoolSize,
                    this.maximumPoolSize,
                    60L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(this.queueCapacity),
                    new BasicThreadFactory.Builder().namingPattern("MessageEventProcessor-%d").daemon(true).build(),
                    new ThreadPoolExecutor.AbortPolicy());

            exceptionExecutor = Executors.newCachedThreadPool(
                    new BasicThreadFactory.Builder().namingPattern("ExceptionEventProcessor-%d").daemon(true).build());

            channelExecutor = Executors.newCachedThreadPool(
                    new BasicThreadFactory.Builder().namingPattern("ChannelEventProcessor-%d").daemon(true).build());
        }
    }

    public abstract void shutdown();

    public void setExecutor(ExecutorService executor) {
        if (executor == null) {
            throw new NullPointerException("executor is null.");
        }
        ExecutorService preExecutor = this.messageExecutor;
        this.messageExecutor = executor;
        List<Runnable> tasks = preExecutor.shutdownNow();
        if (tasks != null && tasks.size() > 0) {
            for (Runnable task : tasks) {
                this.messageExecutor.execute(task);
            }
        }
    }

    public int getExecutorActiveCount() {
        if (messageExecutor instanceof ThreadPoolExecutor) {
            return((ThreadPoolExecutor)messageExecutor).getActiveCount();
        }
        return -1;
    }

    public long getExecutorCompletedTaskCount() {
        if (messageExecutor instanceof ThreadPoolExecutor) {
            return((ThreadPoolExecutor)messageExecutor).getCompletedTaskCount();
        }
        return -1;
    }

    public int getExecutorLargestPoolSize() {
        if (messageExecutor instanceof ThreadPoolExecutor) {
            return((ThreadPoolExecutor)messageExecutor).getLargestPoolSize();
        }
        return -1;
    }

    public int getExecutorPoolSize() {
        if (messageExecutor instanceof ThreadPoolExecutor) {
            return((ThreadPoolExecutor)messageExecutor).getPoolSize();
        }
        return -1;
    }

    public long getExecutorTaskCount() {
        if (messageExecutor instanceof ThreadPoolExecutor) {
            return((ThreadPoolExecutor)messageExecutor).getTaskCount();
        }
        return -1;
    }

    public int getExecutorQueueSize() {
        if (messageExecutor instanceof ThreadPoolExecutor) {
            return((ThreadPoolExecutor)messageExecutor).getQueue().size();
        }
        return -1;
    }

    public void addEventListener(EventListener listener) {
        this.eventListeners.add(listener);
    }

    public void addChannelHandler(String key, ChannelHandler handler){
        this.handlers.put(key, handler);
    }



    class ShutdownHook extends Thread {

        private Service service;

        public ShutdownHook(Service service) {
            this.service = service;
        }


        @Override
        public void run() {
            service.shutdown();
        }
    }
}
