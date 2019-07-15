package cn.cici.frigate.component.sms;

import lombok.Data;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/15 15:22
 * @author: Heyfan Xie
 */
@Data
public class SmsCodeInfo {

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 短信验证码
     */
    private String code;

    /**
     *
     */
    private Integer type;
}
