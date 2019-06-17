package cn.cici.frigate.mqtt.starter.service.server;

import cn.cici.frigate.mqtt.starter.codec.JsonDecoder;
import cn.cici.frigate.mqtt.starter.codec.JsonEncoder;
import cn.cici.frigate.mqtt.starter.codec.MqttWebSocketCodec;
import cn.cici.frigate.mqtt.starter.count.CountHandler;
import cn.cici.frigate.mqtt.starter.count.CountInfo;
import cn.cici.frigate.mqtt.starter.listener.DefaultMessageEventListener;
import cn.cici.frigate.mqtt.starter.pojo.MqttRequest;
import cn.cici.frigate.mqtt.starter.pojo.Request;
import cn.cici.frigate.mqtt.starter.pojo.Response;
import cn.cici.frigate.mqtt.starter.service.EventDispatcher;
import cn.cici.frigate.mqtt.starter.service.Service;
import cn.cici.frigate.mqtt.starter.service.SocketType;
import cn.cici.frigate.mqtt.starter.service.WrappedChannel;
import cn.cici.frigate.mqtt.starter.service.center.ServerStateReportJob;
import cn.cici.frigate.mqtt.starter.service.client.Client;
import cn.cici.frigate.mqtt.starter.status.StatusServer;
import cn.cici.frigate.mqtt.starter.util.AddressUtil;
import cn.cici.frigate.mqtt.starter.util.Sequence;
import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiehf
 * @date 2019/6/16 17:47
 * @concat 370693739@qq.com
 **/
@Slf4j
@Data
public class Server extends Service {

    protected ConcurrentHashMap<String, WrappedChannel> channels = new ConcurrentHashMap<>();

    /**
     * 服务端名称
     */
    protected String serviceName;

    /**
     * 服务端启动事件
     */
    protected long startTime = System.currentTimeMillis();

    protected EventLoopGroup bossGroup;

    protected EventLoopGroup workerGroup;

    protected ServerBootstrap bootstrap;

    /**
     * server 统计信息
     */
    protected CountInfo countInfo = new CountInfo();

    /**
     * 是否开启统计信息
     */
    protected boolean openCount = true;

    /**
     * 统计信息端口
     */
    protected int statusPort = 8001;

    /**
     * 是否开启status端口
     */
    protected boolean openStatus = false;

    /**
     * 统计handler
     */
    protected CountHandler countHandler;

    /**
     * 注册中心地址
     */
    protected String centerAddr;

    /**
     * 重连注册中心时的时间间隔
     */
    protected int centerReConnectTimerInterval = 10000;

    /**
     * 重连注册中心的Timer
     */
    private ScheduledExecutorService centerConnectTimer;

    /**
     * 重试次数
     */
    private AtomicInteger retryTimes = new AtomicInteger(0);

    /**
     * 是否注册成功
     */
    private AtomicBoolean registered = new AtomicBoolean(false);

    private String webSocketPath = "/";

    private String mqttVersion = "server, mattv3.1, mqttv3.1.1";

    private ScheduledExecutorService stateReportService =
            new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("StateReportProcessor-%d").daemon(true).build());

    public Server() {
        super();
        // Server端默认使用业务处理线程池
        this.openExecutor = true;
    }


    @Override
    protected void init() {
        super.init();

        if (useEpoll()) {
            bossGroup = new EpollEventLoopGroup(workerCount, new ScheduledThreadPoolExecutor(workerCount, new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_BOSS_" + index.incrementAndGet());
                }
            }));
            workerGroup = new EpollEventLoopGroup(workerCount, new ScheduledThreadPoolExecutor(workerCount, new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_WORK_" + index.incrementAndGet());
                }
            }));
        } else {
            bossGroup = new NioEventLoopGroup(workerCount,  new ScheduledThreadPoolExecutor(workerCount, new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_BOSS_" + index.incrementAndGet());
                }
            }));
            workerGroup = new NioEventLoopGroup(workerCount, new ScheduledThreadPoolExecutor(workerCount, new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_WORK_" + index.incrementAndGet());
                }
            }));
        }

        // 将一些handler放在这里初始化是为了防止多例的产生。
        if (checkHeartbeat) {
            timeoutHandler = new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
            heartbeatHandler = new ServerHeartbeatHandler();
        }
        eventDispatcher = new EventDispatcher(this);
        this.addEventListener(new DefaultMessageEventListener());

        if (openCount) {
            countHandler = new CountHandler();
            ServerContext.getContext().setServer(this);
        }
    }

    public ChannelFuture bind() {
        init();

        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, keepAlive);
        bootstrap.childOption(ChannelOption.TCP_NODELAY, tcpNoDelay);
        bootstrap.channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

                // 注册各种自定义Handler
                LinkedHashMap<String, ChannelHandler> handlers = getHandlers();
                for (String key : handlers.keySet()) {
                    pipeline.addLast(key, handlers.get(key));
                }

                if (socketType.equals(SocketType.NORMAL)) {
                    if (checkHeartbeat) {
                        pipeline.addLast("timeoutHandler", timeoutHandler);
                        pipeline.addLast("heartbeatHandler", heartbeatHandler);
                    }
                } else if (socketType.equals(SocketType.MQTT)) {
                    pipeline.addLast("mqttDecoder", new MqttDecoder());
                    pipeline.addLast("mqttEncoder", MqttEncoder.DEFAUL_ENCODER);
                } else if (socketType.equals(SocketType.MQTT_WS)) {
                    pipeline.addLast("httpServerCodec", new HttpServerCodec());
                    pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(65536));
                    pipeline.addLast("mqttWebSocketCodec", new MqttWebSocketCodec());
                    pipeline.addLast("mqttDecoder", new MqttDecoder());
                    pipeline.addLast("mqttEncoder", MqttEncoder.DEFAUL_ENCODER);

                    WebSocketServerProtocolHandler webSocketHandler = new WebSocketServerProtocolHandler(webSocketPath, mqttVersion);
                    pipeline.addLast("webSocketHandler", webSocketHandler);
                }

                // 注册事件分发Handler
                ServerDispatchHandler dispatchHandler = new ServerDispatchHandler(eventDispatcher);
                pipeline.addLast("dispatchHandler", dispatchHandler);

                // 注册server统计信息Handle
                if (openCount) {
                    pipeline.addLast("countHandler", countHandler);
                }
            }
        });

        final InetSocketAddress socketAddress = new InetSocketAddress(port);
        ChannelFuture future = bootstrap.bind(socketAddress);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture ch) throws Exception {
                ch.await();
                if (ch.isSuccess()) {
                    log.info("Server started, listening on '{}.", socketAddress);
                } else {
                    log.error("Failed to start Server '{}', caused by: '{}'.", socketAddress, ch.cause());
                }
            }
        });

        // 是否开启status server
        if (openStatus) {
            StatusServer statusServer = new StatusServer();
            statusServer.setPort(statusPort);
            statusServer.setTcpNoDelay(true);
            statusServer.setKeepAlive(true);
            statusServer.bind();
        }

        // 是否启用注册中心
        if (!StringUtils.isBlank(centerAddr)) {
            if (StringUtils.isBlank(ip)) {
                ip = AddressUtil.getLocalIp();
                log.info("Get socket server local ip '{}'.", ip);
            }

            startRegister2Center(this, centerAddr);
        }

        return future;
    }

    public boolean useEpoll() {
        String osName = System.getProperty("os.name");
        boolean isLinuxPlatform = StringUtils.containsIgnoreCase(osName, "linux");
        return isLinuxPlatform && Epoll.isAvailable();
    }

    @Override
    public void shutdown() {
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
    }

    private void startRegister2Center(final Server server, String centerAddr) {
        log.info("Server '{}' starts to register to center '{}'...", this.serviceName, centerAddr);

        final Client client = new Client();
        client.setCheckHeartbeat(true);
        client.addChannelHandler("jsonDecoder", new JsonDecoder());
        client.addChannelHandler("jsonEncoder", new JsonEncoder());

        SocketAddress[] centers = AddressUtil.parseAddress(centerAddr);
        if (centers == null || centers.length < 1) {
            log.warn("Server '{}' can not get center server list from 'centerAddr' property, no need to step forward.", this.serviceName);
            return;
        }

        List<SocketAddress> serverList = new ArrayList<>(Arrays.asList(centers));

        client.setServerList(serverList);

        centerConnectTimer = Executors.newScheduledThreadPool(1);
        centerConnectTimer.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!registered.get() || (client.getChannel() == null) || !client.getChannel().isActive()) {
                    retryTimes.incrementAndGet();
                    registered.set(false);

                    try {
                        long startTime = System.currentTimeMillis();

                        Channel channel = client.getChannel();
                        if (channel == null) {
                            log.warn("Current center channel in server '{}' is invalid, try to reconnect to register " + "center. Current retry times: {}.",
                                    serviceName, retryTimes.get());

                            channel = client.connect().channel();
                        }

                        if (channel == null) {
                            log.warn("Server '{}' can not connect to register center '{}'. Current retry times: {}.",
                                    new Object[]{serviceName, client.getCurServer(), retryTimes.get()});
                            return;
                        }

                        log.info("Server '{}' connected to center '{}'. Current retry times: {}, time cost: {} ms.",
                                new Object[]{serviceName, client.getCurServer(), retryTimes.get(), (System.currentTimeMillis() - startTime)});

                        Request request = new Request();
                        request.setSequence(Sequence.getInstance().addAndGet("CENTER_CLIENT"));

                        JSONObject json = new JSONObject();
                        json.put("action", "register");
                        json.put("service", serviceName);
                        json.put("heartTime", server.getReaderIdleTimeSeconds() / 3);
                        json.put("ip", ip);
                        json.put("port", port);
                        request.setMessage(json.toString());

                        log.info("Server '{}' tries to send request to register center using sequence '{}'. Current retry times: {}.",
                                serviceName, request.getSequence(), retryTimes.get());

                        startTime = System.currentTimeMillis();

                        Response response = client.sendWithSync(request);
                        if (response == null || Response.SUCCESS != response.getCode()) {
                            log.warn("Server '{}' failed to register to center using sequence '{}'. Current retry times: {}.", serviceName,
                                    request.getSequence(), retryTimes.get());
                            return;
                        }

                        registered.set(true);

                        log.info("Server '{}' registered to center using sequence '{}'. Current retry times: {}, time cost: {} ms.",
                                serviceName, request.getSequence(), retryTimes.get(), (System.currentTimeMillis() - startTime));
                    } catch (Throwable ex) {
                        log.error("Server '{}' failed to register to center. Current retry times: {}.", serviceName, retryTimes.get(), ex);
                    }
                }
            }
        }, centerReConnectTimerInterval, centerReConnectTimerInterval, TimeUnit.MILLISECONDS);

        tryToStartUpStateReportJob(server, client);
    }

    private void tryToStartUpStateReportJob(Server server, Client client) {
        String stateReportJobName = "CENTER-SERVER-STATE-REPORT-JOB";

        log.info("Server '{}' tries to start up state report job '{}'...", serviceName, stateReportJobName);

        this.stateReportService.execute(new ServerStateReportJob(server, client));

        log.info("State report job '{}' in server '{}' started.", stateReportJobName, serviceName);
    }

    public ChannelFuture send(WrappedChannel channel, String topic, MqttRequest request) throws InterruptedException {
        MqttPublishMessage pubMessage = (MqttPublishMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBLISH,
                        request.isDup(),
                        request.getQos(),
                        request.isRetained(),
                        0),
                new MqttPublishVariableHeader(topic, 0),
                Unpooled.buffer().writeBytes(request.getPayload()));
        // 超过高水位，则采取同步模式
        if (channel.isWritable()) {
            return channel.writeAndFlush(pubMessage);
        }
        return channel.writeAndFlush(pubMessage).sync();
    }

    public boolean useMqtt() {
        return socketType.equals(SocketType.MQTT) || socketType.equals(SocketType.MQTT_WS);
    }

    public Map<String, WrappedChannel> getChannels() {
        return channels;
    }

    public WrappedChannel getChannel(String channelId) {
        if (channelId != null) {
            return channels.get(channelId);
        } else {
            return null;
        }
    }

    public boolean hasRegisteredToCenter() {
        return this.registered.get();
    }

}
