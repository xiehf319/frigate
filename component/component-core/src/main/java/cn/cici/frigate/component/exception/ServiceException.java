package cn.cici.frigate.component.exception;

/**
 * @description:
 * @createDate:2019/6/19$16:53$
 * @author: Heyfan Xie
 */
public class ServiceException extends RuntimeException {

    private String code;

    public ServiceException(String code) {
        super();
        this.code = code;
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    protected ServiceException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
