package cn.cici.frigate.component.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 基础的异常
 * @createDate:2019/7/3$13:48$
 * @author: Heyfan Xie
 */
@Getter
@AllArgsConstructor
public enum CommonResponseEnum implements BusinessExceptionAssert {

    /**
     * 服务异常
     */
    SERVER_ERROR(500, "SERVER ERROR."),

    /**
     * 无权限访问
     */
    UN_AUTHORIZED(401, "SERVER ERROR."),
    ;

    private int code;

    private String message;
}
