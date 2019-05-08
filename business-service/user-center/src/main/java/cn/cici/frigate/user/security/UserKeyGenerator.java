package cn.cici.frigate.user.security;

import cn.cici.frigate.component.security.SecurityUser;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiehf
 * @date 2019/5/7 0:33
 * @concat 370693739@qq.com
 **/
public class UserKeyGenerator {

    public UserKeyGenerator() {

    }

    /**
     * 对用户信息进行MD5编码
     *
     * 如果用户信息有修改就会对token进行续期
     *
     * @param securityUser
     * @return
     */
    public String extractKey(SecurityUser securityUser) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(securityUser.toString().getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (NoSuchAlgorithmException var4) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", var4);
        } catch (UnsupportedEncodingException var5) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).", var5);
        }
    }
}
