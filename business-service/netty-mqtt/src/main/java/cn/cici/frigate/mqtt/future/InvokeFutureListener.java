package cn.cici.frigate.mqtt.future;

/**
 * @author xiehf
 * @date 2019/6/16 17:32
 * @concat 370693739@qq.com
 **/
public interface InvokeFutureListener {

    /**
     * 完成操作
     * @param future
     * @throws Exception
     */
    void operationComplete(InvokeFuture future) throws Exception;
}
