package cn.cici.frigate.component.sms;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @description: 验证码生成器
 * @createDate:2019/7/11$9:39$
 * @author: Heyfan Xie
 */
public class CodeGenerator {

    /**
     * 验证码生成方法
     *
     * @param length
     * @param validityMinutes
     * @return
     */
    public static ValidateCode generate(int length, int validityMinutes) {
        String code = RandomStringUtils.randomNumeric(length);
        return new ValidateCode(code, validityMinutes);
    }
}
