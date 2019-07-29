package cn.cici.frigate.exception;

/**
 * @description:
 * @createDate:2019/7/3$12:01$
 * @author: Heyfan Xie
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1;

    public BusinessException(ResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }


    public BusinessException(ResponseEnum responseEnum, String message) {
        super(responseEnum, message);
    }


    public BusinessException(ResponseEnum responseEnum, Object[] args, String message, Throwable t) {
        super(responseEnum, args, message, t);
    }


}
