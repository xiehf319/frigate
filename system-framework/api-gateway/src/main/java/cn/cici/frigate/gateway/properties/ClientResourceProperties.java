package cn.cici.frigate.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @description:
 * @createDate:2019/5/25$15:09$
 * @author: Heyfan Xie
 */

@ConfigurationProperties(prefix = "security.oauth2")
@Component
public class ClientResourceProperties {

    private Client client;

    private Resource resource;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public static class Client {

        private String clientId;

        private String clientSecret;

        private String oauthTokenUri;

        private Set<String> ignorePatterns;

        public Set<String> getIgnorePatterns() {
            return ignorePatterns;
        }

        public void setIgnorePatterns(Set<String> ignorePatterns) {
            this.ignorePatterns = ignorePatterns;
        }

        public String getOauthTokenUri() {
            return oauthTokenUri;
        }

        public void setOauthTokenUri(String oauthTokenUri) {
            this.oauthTokenUri = oauthTokenUri;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }

    public static class Resource {

        private String checkTokenUri;

        private String userInfoUri;

        public String getCheckTokenUri() {
            return checkTokenUri;
        }

        public void setCheckTokenUri(String checkTokenUri) {
            this.checkTokenUri = checkTokenUri;
        }

        public String getUserInfoUri() {
            return userInfoUri;
        }

        public void setUserInfoUri(String userInfoUri) {
            this.userInfoUri = userInfoUri;
        }
    }
}
