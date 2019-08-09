package cn.cici.frigate.sms.producer.exception;

import cn.cici.frigate.exception.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/15 16:03
 * @author: Heyfan Xie
 */
@Getter
@AllArgsConstructor
public enum ValidateCodeRespnseCodeEnum implements BusinessExceptionAssert {

    /**
     * 用户没有发送验证码
     */
    USER_NOT_SEND_CODE(403001, "用户没有发送验证码"),

    /**
     * 验证码已失效
     */
    VALIDATE_CODE_EXPIRED(403002, "验证码已失效"),

    /**
     * 验证码错误
     */
    VALIDATE_CODE_ERROR(403003, "验证码错误"),
    ;


    private int code;

    private String message;

}
