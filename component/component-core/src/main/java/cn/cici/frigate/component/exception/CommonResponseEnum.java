package cn.cici.frigate.component.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @createDate:2019/7/3$13:48$
 * @author: Heyfan Xie
 */
@Getter
@AllArgsConstructor
public enum  CommonResponseEnum implements BusinessExceptionAssert{

    /**
     * 服务异常
     */
    SERVER_ERROR(500, "SERVER ERROR.")
    ;

    private int code;

    private String message;
}
