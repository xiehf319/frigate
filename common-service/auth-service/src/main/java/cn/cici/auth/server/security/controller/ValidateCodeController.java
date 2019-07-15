package cn.cici.auth.server.security.controller;

import cn.cici.auth.server.security.sms.ValidateCodeSender;
import cn.cici.frigate.component.sms.CodeGenerator;
import cn.cici.frigate.component.sms.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/11 9:49
 * @author: Heyfan Xie
 */
@RestController
@RequestMapping("/oauth/code")
public class ValidateCodeController {

    /**
     * 验证码长度
     */
    public int length = 6;

    /**
     * 过期分钟数
     */
    public int validityMinutes = 30;

    @Autowired
    private ValidateCodeSender validateCodeSender;

    @GetMapping("/sms")
    public void createSmsCode(@RequestParam("mobile") String mobile) {
        ValidateCode validateCode = CodeGenerator.generate(length, validityMinutes);
        // 调用发送器发送验证码
        validateCodeSender.sendSmsCode(mobile, validateCode);
    }
}
