package cn.cici.frigate.component.sms;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/15 15:50
 * @author: Heyfan Xie
 */
public enum SmsCodeType {

    /**
     * 手机验证码登陆
     */
    LOGIN(1, "MOBILE-LOGIN"),

    /**
     * 忘记密码
     */
    FORGET_PWD(2, "FORGET_PWD"),
    ;

    public static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(LOGIN.type, LOGIN.prefix);
        map.put(FORGET_PWD.type, FORGET_PWD.prefix);
    }

    private Integer type;
    private String prefix;

    SmsCodeType(int type, String prefix) {
        this.type = type;
        this.prefix = prefix;
    }

    public static String getPrefix(Integer type) {
        return map.get(type);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
