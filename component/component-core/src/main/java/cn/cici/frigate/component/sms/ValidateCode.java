package cn.cici.frigate.component.sms;

import java.time.LocalDateTime;

/**
 * @description: 短信验证码
 * @createDate:2019/7/11$9:35$
 * @author: Heyfan Xie
 */
public class ValidateCode {

    private String code;

    private LocalDateTime expireTime;

    public ValidateCode(String code, int validityMinutes) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusMinutes(validityMinutes);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }
}
