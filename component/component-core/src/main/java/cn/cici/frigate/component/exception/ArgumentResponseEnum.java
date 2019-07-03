package cn.cici.frigate.component.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @createDate:2019/7/3$14:01$
 * @author: Heyfan Xie
 */
@Getter
@AllArgsConstructor
public enum ArgumentResponseEnum implements BusinessExceptionAssert{

    /**
     * 参数校验错误
     */
    VALID_ERROR(400, "parameter valid error"),;

    private int code;

    private String message;
}
