package cn.cici.frigate.mqtt.exception;

/**
 * @author xiehf
 * @date 2019/6/16 17:37
 * @concat 370693739@qq.com
 **/
public class SocketRuntimeException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public SocketRuntimeException() {
        super();
    }

    public SocketRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocketRuntimeException(String message) {
        super(message);
    }

    public SocketRuntimeException(Throwable cause) {
        super(cause);
    }

}
