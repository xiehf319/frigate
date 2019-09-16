package cn.cici.frigate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 *  自定义异常写法
 * @createDate:2019/7/3$14:01$
 * @author: Heyfan Xie
 */
@Getter
@AllArgsConstructor
public enum CustomResponseEnum implements BusinessExceptionAssert {

    ;

    private int code;

    private String message;
}
