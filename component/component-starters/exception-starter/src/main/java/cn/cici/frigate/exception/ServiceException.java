package cn.cici.frigate.exception;

/**
 * @description:
 * @createDate:2019/6/19$16:53$
 * @author: Heyfan Xie
 */
public class ServiceException extends RuntimeException {

    private String code;

    private Object[] params;

    /**
     * @param code 异常码对应国际化属性文件的key
     */
    public ServiceException(String code) {
        super();
        this.code = code;
    }

    /**
     * @param code   异常码对应国际化属性文件的key
     * @param params 占位符参数
     */
    public ServiceException(String code, Object[] params) {
        super();
        this.code = code;
        this.params = params;
    }


    /**
     * @param code           异常码对应国际化属性文件的key
     * @param defaultMessage 国际化不存在时，使用默认值
     */
    public ServiceException(String code, String defaultMessage) {
        super(defaultMessage);
        this.code = code;
    }


    /**
     * @param code           异常码对应国际化属性文件的key
     * @param params         占位符参数
     * @param defaultMessage 国际化不存在时，使用默认值
     */
    public ServiceException(String code, Object[] params, String defaultMessage) {
        super(defaultMessage);
        this.code = code;
        this.params = params;
    }

    /**
     * @param code           异常码对应国际化属性文件的key
     * @param params         占位符参数
     * @param defaultMessage 国际化不存在时，使用默认值
     * @param cause          具体抛出的异常，可以作为日志记录
     */
    public ServiceException(String code, Object[] params, String defaultMessage, Throwable cause) {
        super(defaultMessage, cause);
        this.code = code;
        this.params = params;
    }

    /**
     * @param code   异常码对应国际化属性文件的key
     * @param params 占位符参数
     * @param cause  具体抛出的异常，可以作为日志记录
     */
    public ServiceException(String code, Object[] params, Throwable cause) {
        super(cause);
        this.code = code;
        this.params = params;

    }


    public String getCode() {
        return code;
    }

    public Object[] getParams() {
        return params;
    }
}
