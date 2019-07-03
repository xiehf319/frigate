package cn.cici.frigate.component.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 *
 *  使用方法  BizResponseEnum
 * @createDate:2019/7/3$12:10$
 * @author: Heyfan Xie
 */
@Getter
@AllArgsConstructor
public enum  BizResponseEnum implements BusinessExceptionAssert {

    /**
     * 用户不存在
     */
    USERNAME_NOT_FOUND(7001, "user not fount"),;

    private int code;

    private String message;

    /**
     * todo 使用方法
     * @param args
     */
    public static void main(String[] args) {
        Object obj = null;
        BizResponseEnum.USERNAME_NOT_FOUND.assertNotNull(obj);
    }

}
