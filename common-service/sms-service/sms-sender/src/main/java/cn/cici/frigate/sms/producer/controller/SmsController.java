package cn.cici.frigate.sms.producer.controller;

import cn.cici.frigate.component.sms.SmsCodeInfo;
import cn.cici.frigate.component.sms.SmsCodeType;
import cn.cici.frigate.component.sms.ValidateCode;
import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.sms.producer.exception.ValidateCodeRespnseCodeEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/15 15:15
 * @author: Heyfan Xie
 */
@RestController
@Slf4j
public class SmsController {

    @Autowired
    RedisTemplate<String, String> stringRedisTemplate;

    /**
     * 发送验证码 待接入验证码平台
     *
     * @param smsCodeInfo
     * @return
     */
    @PostMapping("/api/sms/code")
    public R sendCode(@RequestBody SmsCodeInfo smsCodeInfo) {
        log.info("send sms code： {} of mobile: {}, type: {}",
                smsCodeInfo.getCode(), smsCodeInfo.getMobile(), smsCodeInfo.getType());
        String prefix = SmsCodeType.getPrefix(smsCodeInfo.getType());
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(prefix, smsCodeInfo.getMobile(), smsCodeInfo.getCode());
        return R.success();
    }

    /**
     * 校验验证码
     *
     * @param smsCodeInfo
     * @return
     */
    @PostMapping("/api/sms/verify")
    public R verifyCode(@RequestBody SmsCodeInfo smsCodeInfo) {
        String mobile = smsCodeInfo.getMobile();
        String code = smsCodeInfo.getCode();
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        String smsCodeCached = hashOperations.get("SMS_CODE", mobile);

        // 验证是否存在验证码
        ValidateCodeRespnseCodeEnum.USER_NOT_SEND_CODE
                .assertNotEmpty(smsCodeCached);

        ValidateCode validateCode = JSON.parseObject(smsCodeCached, ValidateCode.class);

        // 验证验证码是否格式有效
        ValidateCodeRespnseCodeEnum.VALIDATE_CODE_ERROR
                .assertNotNull(validateCode);

        // 验证验证码已失效
        ValidateCodeRespnseCodeEnum.VALIDATE_CODE_EXPIRED
                .assertFalse(LocalDateTime.now().isAfter(validateCode.getExpireTime()));

        // 验证验证码是否相同
        ValidateCodeRespnseCodeEnum.VALIDATE_CODE_ERROR
                .assertTrue(StringUtils.equals(code, validateCode.getCode()));

        // 移除
        hashOperations.delete("SMS_CODE", code);
        return R.success();
    }
}
