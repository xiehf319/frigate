package cn.cici.auth.server.security.controller;

import cn.cici.auth.server.security.sms.CodeGenerator;
import cn.cici.auth.server.security.sms.ValidateCode;
import cn.cici.auth.server.security.sms.ValidateCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/11 9:49
 * @author: Heyfan Xie
 */
@RestController
@RequestMapping("/code")
public class ValidateCodeController {

    /**
     * 验证码存放session的key
     */
    public static final String SESSION_CODE_KEY = "code";

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
    public void createSmsCode(HttpServletRequest request, String mobile) {
        ValidateCode validateCode = CodeGenerator.generate(length, validityMinutes);
        // 存储验证码到session中
        request.getSession().setAttribute(SESSION_CODE_KEY, validateCode);
        // 调用发送器发送验证码
        validateCodeSender.sendSmsCode(mobile, validateCode.getCode());
    }
}
