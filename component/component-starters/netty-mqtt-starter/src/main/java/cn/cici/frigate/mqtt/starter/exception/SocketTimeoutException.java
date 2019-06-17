package cn.cici.frigate.mqtt.starter.exception;

/**
 * @author xiehf
 * @date 2019/6/16 17:37
 * @concat 370693739@qq.com
 **/
public class SocketTimeoutException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public SocketTimeoutException() {
        super();
    }

    public SocketTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocketTimeoutException(String message) {
        super(message);
    }

    public SocketTimeoutException(Throwable cause) {
        super(cause);
    }

}
