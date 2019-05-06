package cn.cici.frigate.user.security;

import cn.cici.frigate.user.redis.JdkSerializationStrategy;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.*;

/**
 * @author xiehf
 * @date 2019/5/7 0:19
 * @concat 370693739@qq.com
 **/
public class RedisTokenStore {


    private static final String ACCESS = "access:";
    private static final String AUTH_TO_ACCESS = "auth_to_access:";
    private static final String AUTH = "auth:";
    private static final String REFRESH_AUTH = "refresh_auth:";
    private static final String ACCESS_TO_REFRESH = "access_to_refresh:";
    private static final String REFRESH = "refresh:";
    private static final String REFRESH_TO_ACCESS = "refresh_to_access:";
    private static final String CLIENT_ID_TO_ACCESS = "client_id_to_access:";
    private static final String UNAME_TO_ACCESS = "uname_to_access:";

    /**
     * redis连接工厂类
     */
    private final RedisConnectionFactory connectionFactory;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new AuthenticationKeyGenerator();
    private JdkSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    /**
     * 给所有的key添加统一前缀
     */
    private String prefix = "";

    public RedisTokenStore(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
        this.authenticationKeyGenerator = authenticationKeyGenerator;
    }

    public void setSerializationStrategy(JdkSerializationStrategy serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    private RedisConnection getConnection() {
        return this.connectionFactory.getConnection();
    }

    private byte[] serialize(Object object) {
        return this.serializationStrategy.serialize(object);
    }

    private byte[] serializeKey(String object) {
        return this.serialize(this.prefix + object);
    }

    private AccessToken deserializeAccessToken(byte[] bytes) {
        return this.serializationStrategy.deserialize(bytes, AccessToken.class);
    }

    private Authentication deserializeAuthentication(byte[] bytes) {
        return this.serializationStrategy.deserialize(bytes, Authentication.class);
    }

    private RefreshToken deserializeRefreshToken(byte[] bytes) {
        return this.serializationStrategy.deserialize(bytes, RefreshToken.class);
    }

    private byte[] serialize(String string) {
        return this.serializationStrategy.serialize(string);
    }

    private String deserializeString(byte[] bytes) {
        return this.serializationStrategy.deserializeString(bytes);
    }

    /**
     * 获取token
     *
     * @param authentication
     * @return
     */

    public AccessToken getAccessToken(Authentication authentication) {
        String key = this.authenticationKeyGenerator.extractKey(authentication);
        byte[] serializedKey = this.serializeKey(AUTH_TO_ACCESS + key);
        byte[] bytes = null;
        RedisConnection conn = this.getConnection();

        try {
            bytes = conn.get(serializedKey);
        } finally {
            conn.close();
        }

        AccessToken accessToken = this.deserializeAccessToken(bytes);
        if (accessToken != null) {
            Authentication storedAuthentication = this.readAuthentication(accessToken.getValue());
            if (storedAuthentication == null || !key.equals(this.authenticationKeyGenerator.extractKey(storedAuthentication))) {
                this.storeAccessToken(accessToken, authentication);
            }
        }

        return accessToken;
    }


    public Authentication readAuthentication(AccessToken token) {
        return this.readAuthentication(token.getValue());
    }


    public Authentication readAuthentication(String token) {
        byte[] bytes = null;
        RedisConnection conn = this.getConnection();

        try {
            bytes = conn.get(this.serializeKey(AUTH + token));
        } finally {
            conn.close();
        }

        Authentication var4 = this.deserializeAuthentication(bytes);
        return var4;
    }


    public Authentication readAuthenticationForRefreshToken(RefreshToken token) {
        return this.readAuthenticationForRefreshToken(token.getValue());
    }

    public Authentication readAuthenticationForRefreshToken(String token) {
        RedisConnection conn = this.getConnection();

        Authentication var5;
        try {
            byte[] bytes = conn.get(this.serializeKey(REFRESH_AUTH + token));
            Authentication auth = this.deserializeAuthentication(bytes);
            var5 = auth;
        } finally {
            conn.close();
        }

        return var5;
    }


    /**
     * 保存 token
     *
     * @param token
     * @param authentication
     */

    public void storeAccessToken(AccessToken token, Authentication authentication) {
        byte[] serializedAccessToken = this.serialize(token);
        byte[] serializedAuth = this.serialize(authentication);
        byte[] accessKey = this.serializeKey(ACCESS + token.getValue());
        byte[] authKey = this.serializeKey(AUTH + token.getValue());
        byte[] authToAccessKey = this.serializeKey(AUTH_TO_ACCESS + this.authenticationKeyGenerator.extractKey(authentication));
        byte[] approvalKey = this.serializeKey(UNAME_TO_ACCESS + getApprovalKey(authentication));
        byte[] clientId = this.serializeKey(CLIENT_ID_TO_ACCESS + authentication.getRequest().getClientId());
        RedisConnection conn = this.getConnection();

        try {
            conn.openPipeline();
            conn.stringCommands().set(accessKey, serializedAccessToken);
            conn.stringCommands().set(authKey, serializedAuth);
            conn.stringCommands().set(authToAccessKey, serializedAccessToken);
            if (!authentication.isClientOnly()) {
                conn.rPush(approvalKey, new byte[][]{serializedAccessToken});
            }

            conn.rPush(clientId, new byte[][]{serializedAccessToken});
            if (token.getExpiration() != null) {
                int seconds = token.getExpiresIn();
                conn.expire(accessKey, (long) seconds);
                conn.expire(authKey, (long) seconds);
                conn.expire(authToAccessKey, (long) seconds);
                conn.expire(clientId, (long) seconds);
                conn.expire(approvalKey, (long) seconds);
            }

            RefreshToken refreshToken = token.getRefreshToken();
            if (refreshToken != null && refreshToken.getValue() != null) {
                byte[] refresh = this.serialize(token.getRefreshToken().getValue());
                byte[] auth = this.serialize(token.getValue());
                byte[] refreshToAccessKey = this.serializeKey(REFRESH_TO_ACCESS + token.getRefreshToken().getValue());
                conn.stringCommands().set(refreshToAccessKey, auth);
                byte[] accessToRefreshKey = this.serializeKey(ACCESS_TO_REFRESH + token.getValue());
                conn.stringCommands().set(accessToRefreshKey, refresh);
                Date expiration = refreshToken.getExpiration();
                if (expiration != null) {
                    int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L).intValue();
                    conn.expire(refreshToAccessKey, (long) seconds);
                    conn.expire(accessToRefreshKey, (long) seconds);
                }
            }

            conn.closePipeline();
        } finally {
            conn.close();
        }
    }

    private static String getApprovalKey(Authentication authentication) {
        String userName = authentication.getUsername() == null ? "" : authentication.getUsername();
        return getApprovalKey(authentication.getRequest().getClientId(), userName);
    }

    private static String getApprovalKey(String clientId, String userName) {
        return clientId + (userName == null ? "" : ":" + userName);
    }


    public void removeAccessToken(AccessToken accessToken) {
        this.removeAccessToken(accessToken.getValue());
    }


    public AccessToken readAccessToken(String tokenValue) {
        byte[] key = this.serializeKey(ACCESS + tokenValue);
        byte[] bytes = null;
        RedisConnection conn = this.getConnection();

        try {
            bytes = conn.get(key);
        } finally {
            conn.close();
        }

        AccessToken var5 = this.deserializeAccessToken(bytes);
        return var5;
    }

    public void removeAccessToken(String tokenValue) {
        byte[] accessKey = this.serializeKey(ACCESS + tokenValue);
        byte[] authKey = this.serializeKey(AUTH + tokenValue);
        byte[] accessToRefreshKey = this.serializeKey(ACCESS_TO_REFRESH + tokenValue);
        RedisConnection conn = this.getConnection();

        try {
            conn.openPipeline();
            conn.get(accessKey);
            conn.get(authKey);
            conn.del(new byte[][]{accessKey});
            conn.del(new byte[][]{accessToRefreshKey});
            conn.del(new byte[][]{authKey});
            List<Object> results = conn.closePipeline();
            byte[] access = (byte[]) results.get(0);
            byte[] auth = (byte[]) results.get(1);
            Authentication authentication = this.deserializeAuthentication(auth);
            if (authentication != null) {
                String key = this.authenticationKeyGenerator.extractKey(authentication);
                byte[] authToAccessKey = this.serializeKey(AUTH_TO_ACCESS + key);
                byte[] unameKey = this.serializeKey(UNAME_TO_ACCESS + getApprovalKey(authentication));
                byte[] clientId = this.serializeKey(CLIENT_ID_TO_ACCESS + authentication.getRequest().getClientId());
                conn.openPipeline();
                conn.del(new byte[][]{authToAccessKey});
                conn.lRem(unameKey, 1L, access);
                conn.lRem(clientId, 1L, access);
                conn.del(new byte[][]{this.serialize(ACCESS + key)});
                conn.closePipeline();
            }
        } finally {
            conn.close();
        }

    }


    public void storeRefreshToken(RefreshToken refreshToken, Authentication authentication) {
        byte[] refreshKey = this.serializeKey(REFRESH + refreshToken.getValue());
        byte[] refreshAuthKey = this.serializeKey(REFRESH_AUTH + refreshToken.getValue());
        byte[] serializedRefreshToken = this.serialize(refreshToken);
        RedisConnection conn = this.getConnection();

        try {
            conn.openPipeline();
            conn.stringCommands().set(refreshKey, serializedRefreshToken);
            conn.stringCommands().set(refreshAuthKey, this.serialize(authentication));
            Date expiration = refreshToken.getExpiration();
            if (expiration != null) {
                int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L).intValue();
                conn.expire(refreshKey, (long) seconds);
                conn.expire(refreshAuthKey, (long) seconds);
            }
            conn.closePipeline();
        } finally {
            conn.close();
        }

    }


    public RefreshToken readRefreshToken(String tokenValue) {
        byte[] key = this.serializeKey(REFRESH + tokenValue);
        byte[] bytes = null;
        RedisConnection conn = this.getConnection();

        try {
            bytes = conn.get(key);
        } finally {
            conn.close();
        }

        RefreshToken var5 = this.deserializeRefreshToken(bytes);
        return var5;
    }


    public void removeRefreshToken(RefreshToken refreshToken) {
        this.removeRefreshToken(refreshToken.getValue());
    }

    public void removeRefreshToken(String tokenValue) {
        byte[] refreshKey = this.serializeKey(REFRESH + tokenValue);
        byte[] refreshAuthKey = this.serializeKey(REFRESH_AUTH + tokenValue);
        byte[] refresh2AccessKey = this.serializeKey(REFRESH_TO_ACCESS + tokenValue);
        byte[] access2RefreshKey = this.serializeKey(ACCESS_TO_REFRESH + tokenValue);
        RedisConnection conn = this.getConnection();

        try {
            conn.openPipeline();
            conn.del(new byte[][]{refreshKey});
            conn.del(new byte[][]{refreshAuthKey});
            conn.del(new byte[][]{refresh2AccessKey});
            conn.del(new byte[][]{access2RefreshKey});
            conn.closePipeline();
        } finally {
            conn.close();
        }

    }


    public void removeAccessTokenUsingRefreshToken(RefreshToken refreshToken) {
        this.removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    private void removeAccessTokenUsingRefreshToken(String refreshToken) {
        byte[] key = this.serializeKey(REFRESH_TO_ACCESS + refreshToken);
        List<Object> results = null;
        RedisConnection conn = this.getConnection();

        try {
            conn.openPipeline();
            conn.get(key);
            conn.del(new byte[][]{key});
            results = conn.closePipeline();
        } finally {
            conn.close();
        }

        if (results != null) {
            byte[] bytes = (byte[]) results.get(0);
            String accessToken = this.deserializeString(bytes);
            if (accessToken != null) {
                this.removeAccessToken(accessToken);
            }

        }
    }


    public Collection<AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        byte[] approvalKey = this.serializeKey(UNAME_TO_ACCESS + getApprovalKey(clientId, userName));
        List<byte[]> byteList = null;
        RedisConnection conn = this.getConnection();

        try {
            byteList = conn.lRange(approvalKey, 0L, -1L);
        } finally {
            conn.close();
        }

        if (byteList != null && byteList.size() != 0) {
            List<AccessToken> accessTokens = new ArrayList(byteList.size());
            Iterator var7 = byteList.iterator();

            while (var7.hasNext()) {
                byte[] bytes = (byte[]) var7.next();
                AccessToken accessToken = this.deserializeAccessToken(bytes);
                accessTokens.add(accessToken);
            }

            return Collections.unmodifiableCollection(accessTokens);
        } else {
            return Collections.emptySet();
        }
    }


    public Collection<AccessToken> findTokensByClientId(String clientId) {
        byte[] key = this.serializeKey(CLIENT_ID_TO_ACCESS + clientId);
        List<byte[]> byteList = null;
        RedisConnection conn = this.getConnection();

        try {
            byteList = conn.lRange(key, 0L, -1L);
        } finally {
            conn.close();
        }

        if (byteList != null && byteList.size() != 0) {
            List<AccessToken> accessTokens = new ArrayList(byteList.size());
            Iterator var6 = byteList.iterator();

            while (var6.hasNext()) {
                byte[] bytes = (byte[]) var6.next();
                AccessToken accessToken = this.deserializeAccessToken(bytes);
                accessTokens.add(accessToken);
            }

            return Collections.unmodifiableCollection(accessTokens);
        } else {
            return Collections.emptySet();
        }
    }

}
