package cn.cici.frigate.mqtt.starter.service.server;

/**
 * @author xiehf
 * @date 2019/6/16 17:57
 * @concat 370693739@qq.com
 **/
public class ServerContext {

    private ServerContext() {
    }

    private static ServerContext instance = new ServerContext();

    public static ServerContext getContext() {
        return instance;
    }

    private Server server;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
