package cn.cici.auth.server.security.sms;

import com.alibaba.fastjson.JSONObject;
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
     * 发送手机验证码 同时缓存到redis
     * @param mobile 手机号
     * @param validateCode 验证码
     */
    public void sendSmsCode(String mobile, ValidateCode validateCode) {
        redisTemplate.opsForHash().put("SMS_CODE", mobile, JSONObject.toJSONString(validateCode));
        System.out.println("mobile: " + mobile);
        System.out.println("code: " + validateCode.getCode());
    }
}
