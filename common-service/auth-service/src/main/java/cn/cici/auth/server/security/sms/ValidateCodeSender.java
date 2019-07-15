package cn.cici.auth.server.security.sms;

import cn.cici.frigate.component.sms.SmsCodeType;
import cn.cici.frigate.component.sms.ValidateCode;
import cn.cici.frigate.sms.api.SmsCodeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 验证码发送器
 * @createDate: 2019/7/11 9:41
 * @author: Heyfan Xie
 */
@Component
public class ValidateCodeSender {

    @Autowired
    private SmsCodeClient smsCodeClient;

    /**
     * 发送手机验证码 同时缓存到redis
     *
     * @param mobile       手机号
     * @param validateCode 验证码
     */
    public void sendSmsCode(String mobile, ValidateCode validateCode) {
        smsCodeClient.send(mobile, validateCode.getCode(), SmsCodeType.LOGIN.getType());
    }
}
