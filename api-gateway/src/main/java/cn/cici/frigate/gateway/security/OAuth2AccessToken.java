package cn.cici.frigate.gateway.security;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author xiehf
 * @date 2019/5/2 22:20
 * @concat 370693739@qq.com
 **/
public interface OAuth2AccessToken {

    String BEARER_TYPE = "Bearer";

    String OAUTH2_TYPE = "OAuth2";

    String ACCESS_TOKEN = "access_token";

    String TOKEN_TYPE = "token_type";

    String EXPIRES_IN = "expires_in";

    String REFRESH_TOKEN ="refresh_token";

    String SCOPE = "scope";

    Map<String, Object> getAdditionalInformation();

    Set<String> getScope();

    String getTokenType();

    boolean isExpired();

    Date getExpiraion();

    int getExpiresIn();

    String getValue();
}
