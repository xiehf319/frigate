package cn.cici.auth.server.security.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @description:
 *  验证码发送器
 * @createDate: 2019/7/11 9:41
 * @author: Heyfan Xie
 */
@Component
public class ValidateCodeSender {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送手机验证码
     * @param mobile
     * @param code
     */
    public void sendSmsCode(String mobile, String code) {
        redisTemplate.opsForHash().put("SMS_CODE", mobile, code);
        System.out.println("mobile: " + mobile);
        System.out.println("code: " + code);
    }
}
