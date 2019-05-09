package cn.cici.frigate.rbac.security;

import cn.cici.frigate.component.security.SecurityToken;
import cn.cici.frigate.component.security.SecurityUser;
import cn.cici.frigate.rbac.redis.JdkSerializationStrategy;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.List;

/**
 * @author xiehf
 * @date 2019/5/7 0:19
 * @concat 370693739@qq.com
 **/
public class RedisTokenStore {

    /** rbac 作为key access_token 作为value的 prefix*/
    private static final String USER_TO_ACCESS = "user_to_access:";

    /** access_token 作为key rbac 作为value的 prefix*/
    private static final String ACCESS_TO_USER = "access_to_user:";

    /** refresh_token 作为key rbac 作为value的 prefix*/
    private static final String REFRESH_TO_USER = "refresh_to_user:";

    /** access_token 作为key refresh_token 作为value的 prefix*/
    private static final String ACCESS_TO_REFRESH = "access_to_refresh:";

    /** refresh_token 作为key access_token 作为value的 prefix*/
    private static final String REFRESH_TO_ACCESS = "refresh_to_access:";

    /**
     * redis连接工厂类
     */
    private final RedisConnectionFactory connectionFactory;

    /**
     * userKey 生成
     */
    private UserKeyGenerator userKeyGenerator = new UserKeyGenerator();

    /**
     * 序列化
     */
    private JdkSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    /**
     * 给所有的key添加统一前缀
     */
    private String prefix = "";

    public RedisTokenStore(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
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

    private SecurityToken deserializeToken(byte[] bytes) {
        return this.serializationStrategy.deserialize(bytes, SecurityToken.class);
    }

    private SecurityUser deserializeUser(byte[] bytes) {
        return this.serializationStrategy.deserialize(bytes, SecurityUser.class);
    }

    private byte[] serialize(String string) {
        return this.serializationStrategy.serialize(string);
    }

    private String deserializeString(byte[] bytes) {
        return this.serializationStrategy.deserializeString(bytes);
    }

    /**
     * 根据用户 获取token
     *
     * @param securityUser
     * @return
     */
    public SecurityToken getAccessToken(SecurityUser securityUser) {
        String key = this.userKeyGenerator.extractKey(securityUser);
        byte[] serializedKey = this.serializeKey(USER_TO_ACCESS + securityUser.getUserId());
        byte[] bytes = null;
        RedisConnection conn = this.getConnection();

        try {
            bytes = conn.get(serializedKey);
        } finally {
            conn.close();
        }

        SecurityToken securityToken = this.deserializeToken(bytes);
        if (securityToken != null) {
            SecurityUser storedSecurityUser = this.readSecurityUserByAccessToken(securityToken.getAccessToken());
            if (storedSecurityUser == null || !key.equals(this.userKeyGenerator.extractKey(storedSecurityUser))) {
                // 续期
                this.storeAccessToken(securityToken, securityUser);
            }
        }

        return securityToken;
    }

    public SecurityUser readSecurityUserByAccessToken(String token) {
        byte[] bytes = null;
        RedisConnection conn = this.getConnection();
        try {
            bytes = conn.get(this.serializeKey(ACCESS_TO_USER + token));
        } finally {
            conn.close();
        }
        SecurityUser var4 = this.deserializeUser(bytes);
        return var4;
    }

    public SecurityUser readUserForRefreshToken(String token) {
        RedisConnection conn = this.getConnection();

        SecurityUser var5;
        try {
            byte[] bytes = conn.get(this.serializeKey(REFRESH_TO_USER + token));
            SecurityUser auth = this.deserializeUser(bytes);
            var5 = auth;
        } finally {
            conn.close();
        }
        return var5;
    }


    /**
     * 保存 token
     *
     * @param securityToken
     * @param securityUser
     */
    public void storeAccessToken(SecurityToken securityToken, SecurityUser securityUser) {

        // 保存user与token的关系
        byte[] serializedAccessToken = this.serialize(securityToken);
        byte[] serializedUser = this.serialize(securityUser);
        byte[] accessToUserKey = this.serializeKey(ACCESS_TO_USER + securityToken.getAccessToken());
        byte[] userToAccessKey = this.serializeKey(USER_TO_ACCESS + securityUser.getUserId());
        RedisConnection conn = this.getConnection();

        try {
            conn.openPipeline();

            // 先设置一个无限期的
            conn.stringCommands().set(accessToUserKey, serializedUser);
            conn.stringCommands().set(userToAccessKey, serializedAccessToken);

            if (securityToken.getExpiration() != null) {

                // 如果有, 设置过期时间
                int seconds = securityToken.getExpiresIn();
                conn.expire(accessToUserKey, (long) seconds);
                conn.expire(userToAccessKey, (long) seconds);
            }

            String refreshToken =  securityToken.getRefreshToken();
            if (refreshToken != null && refreshToken.length() > 0) {

                // 序列化 refresh_token
                byte[] serializedRefreshToken = this.serialize(refreshToken);

                // refresh --> access
                byte[] refreshToAccessKey = this.serializeKey(REFRESH_TO_ACCESS + refreshToken);

                // access --> refresh
                byte[] accessToRefreshKey = this.serializeKey(ACCESS_TO_REFRESH + securityToken.getAccessToken());

                conn.stringCommands().set(refreshToAccessKey, serializedAccessToken);
                conn.stringCommands().set(accessToRefreshKey, serializedRefreshToken);

                Integer expires = securityToken.getRefreshExpiresIn();
                if (expires != null && expires > 0) {
                    // 存储refresh_token 和 access_token的关系
                    conn.expire(refreshToAccessKey, (long) expires);
                    conn.expire(accessToRefreshKey, (long) expires);
                }
            }

            conn.closePipeline();
        } finally {
            conn.close();
        }
    }

    public void removeAccessToken(String accessToken) {
        byte[] accessToUserKey = this.serializeKey(ACCESS_TO_USER + accessToken);
        byte[] accessToRefreshKey = this.serializeKey(ACCESS_TO_REFRESH + accessToken);

        RedisConnection conn = this.getConnection();
        try {
            conn.openPipeline();
            conn.get(accessToUserKey);
            conn.get(accessToRefreshKey);

            // 移除 access_to_user
            conn.del(new byte[][]{accessToUserKey});

            // 移除 access_to_refresh
            conn.del(new byte[][]{accessToRefreshKey});

            List<Object> results = conn.closePipeline();

            // 从删除的通道中依次取出结果
            byte[] user = (byte[]) results.get(0);
            byte[] refresh = (byte[]) results.get(1);

            SecurityUser securityUser = this.deserializeUser(user);
            String  refreshToken = this.deserializeString(refresh);
            if (securityUser != null) {
                byte[] userToAccessKey = this.serializeKey(USER_TO_ACCESS + securityUser.getUserId());
                conn.openPipeline();

                // 移除 user_to_access
                conn.del(new byte[][]{userToAccessKey});
                conn.closePipeline();
            }
            if (refreshToken != null) {
                byte[] refreshToAccessKey = this.serializeKey(REFRESH_TO_ACCESS + refreshToken);
                conn.openPipeline();

                // 移除 refresh_to_access
                conn.del(new byte[][]{refreshToAccessKey});
                conn.closePipeline();
            }
        } finally {
            conn.close();
        }
    }

    /**
     * 通过token 获取刷新token
     *
     * @param accessToken 访问token
     * @return
     */
    public String readRefreshToken(String accessToken) {
        byte[] key = this.serializeKey(ACCESS_TO_REFRESH + accessToken);
        byte[] bytes = null;
        RedisConnection conn = this.getConnection();
        try {
            bytes = conn.get(key);
        } finally {
            conn.close();
        }

        String var5 = this.deserializeString(bytes);
        return var5;
    }

    /**
     * 移除refresh_token
     * @param refreshToken
     */
    public void removeRefreshToken(String refreshToken) {
        byte[] refresh2AuthKey = this.serializeKey(REFRESH_TO_USER + refreshToken);
        byte[] refresh2AccessKey = this.serializeKey(REFRESH_TO_ACCESS + refreshToken);
        RedisConnection conn = this.getConnection();

        try {
            conn.openPipeline();
            conn.del(new byte[][]{refresh2AuthKey});
            conn.del(new byte[][]{refresh2AccessKey});
            conn.closePipeline();
        } finally {
            conn.close();
        }

    }

}
