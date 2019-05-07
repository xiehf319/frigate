package cn.cici.frigate.user.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xiehf
 * @date 2019/5/7 0:33
 * @concat 370693739@qq.com
 **/
public class UserKeyGenerator {

    public UserKeyGenerator() {

    }

    public String extractKey(SecurityUser securityUser) {
        Map<String, String> values = new LinkedHashMap();
        values.put("userId", securityUser.getUserId());
        values.put("username", securityUser.getUsername());
        return this.generateKey(values);
    }

    protected String generateKey(Map<String, String> values) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (NoSuchAlgorithmException var4) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", var4);
        } catch (UnsupportedEncodingException var5) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).", var5);
        }
    }
}
