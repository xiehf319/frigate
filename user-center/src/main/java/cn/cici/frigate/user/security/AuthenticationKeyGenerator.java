package cn.cici.frigate.user.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author xiehf
 * @date 2019/5/7 0:33
 * @concat 370693739@qq.com
 **/
public class AuthenticationKeyGenerator {

    private static final String CLIENT_ID = "client_id";
    private static final String SCOPE = "scope";
    private static final String USERNAME = "username";

    public AuthenticationKeyGenerator() {
    }

    public String extractKey(Authentication authentication) {
        Map<String, String> values = new LinkedHashMap();
        Request request = authentication.getRequest();
        if (!authentication.isClientOnly()) {
            values.put("username", authentication.getName());
        }
        values.put("client_id", request.getClientId());
        if (request.getScope() != null) {
            values.put("scope", SecurityUtils.formatParameterList(new TreeSet<>(request.getScope())));
        }

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
