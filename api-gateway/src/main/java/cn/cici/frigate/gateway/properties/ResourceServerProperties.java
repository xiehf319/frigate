package cn.cici.frigate.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @createDate:2019/5/5$9:36$
 * @author: Heyfan Xie
 */
@ConfigurationProperties(prefix = "security.oauth2")
@Component
public class ResourceServerProperties {

    private Client client;

    private Resource resource;

    private List<String> permitAllUri;

    public List<String> getPermitAllUri() {
        return permitAllUri;
    }

    public void setPermitAllUri(List<String> permitAllUri) {
        this.permitAllUri = permitAllUri;
    }

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

        private String accessTokenUri;

        private String grantType;

        private String scope;

        public String getGrantType() {
            return grantType;
        }

        public void setGrantType(String grantType) {
            this.grantType = grantType;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
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

        public String getAccessTokenUri() {
            return accessTokenUri;
        }

        public void setAccessTokenUri(String accessTokenUri) {
            this.accessTokenUri = accessTokenUri;
        }
    }

    public static class Resource{

        private String userInfoUri;

        private String checkTokenUri;

        public String getUserInfoUri() {
            return userInfoUri;
        }

        public void setUserInfoUri(String userInfoUri) {
            this.userInfoUri = userInfoUri;
        }

        public String getCheckTokenUri() {
            return checkTokenUri;
        }

        public void setCheckTokenUri(String checkTokenUri) {
            this.checkTokenUri = checkTokenUri;
        }
    }
}
