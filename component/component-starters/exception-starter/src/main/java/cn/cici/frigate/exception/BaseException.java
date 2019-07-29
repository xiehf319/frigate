package cn.cici.frigate.exception;

import lombok.Getter;

/**
 * @description:
 * @createDate:2019/7/3$11:58$
 * @author: Heyfan Xie
 */
@Getter
public class BaseException extends RuntimeException {

    private ResponseEnum responseEnum;
    private Object[] args;

    public BaseException() {
        super();
    }

    public BaseException(ResponseEnum responseEnum) {
        super();
        this.responseEnum = responseEnum;
    }

    public BaseException(ResponseEnum responseEnum, String message) {
        super(message);
        this.responseEnum = responseEnum;
    }

    public BaseException(ResponseEnum responseEnum, Object[] args, String message) {
        super(message);
        this.args = args;
        this.responseEnum = responseEnum;
    }

    public BaseException(ResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(message, cause);
        this.args = args;
        this.responseEnum = responseEnum;
    }

}
