package cn.cici.frigate.rbac.constant;

import cn.cici.frigate.component.exception.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/18 11:02
 * @author: Heyfan Xie
 */
@Getter
@AllArgsConstructor
public enum  RbacResponseEnum implements BusinessExceptionAssert {

    /**
     * 用户已经存在
     */
    USERNAME_IS_EXISTS(502001, "用户已经存在."),

    ;

    private int code;

    private String message;
}
