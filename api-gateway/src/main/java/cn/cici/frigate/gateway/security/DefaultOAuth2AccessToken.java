package cn.cici.frigate.gateway.security;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author xiehf
 * @date 2019/5/2 22:24
 * @concat 370693739@qq.com
 **/
public class DefaultOAuth2AccessToken implements OAuth2AccessToken{

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }

    @Override
    public Set<String> getScope() {
        return null;
    }

    @Override
    public String getTokenType() {
        return null;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public Date getExpiraion() {
        return null;
    }

    @Override
    public int getExpiresIn() {
        return 0;
    }

    @Override
    public String getValue() {
        return null;
    }
}
