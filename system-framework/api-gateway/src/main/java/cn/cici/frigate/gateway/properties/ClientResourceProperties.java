package cn.cici.frigate.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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

        private String checkTokenUrl;

        private String userInfoUrl;

        public String getCheckTokenUrl() {
            return checkTokenUrl;
        }

        public void setCheckTokenUrl(String checkTokenUrl) {
            this.checkTokenUrl = checkTokenUrl;
        }

        public String getUserInfoUrl() {
            return userInfoUrl;
        }

        public void setUserInfoUrl(String userInfoUrl) {
            this.userInfoUrl = userInfoUrl;
        }
    }
}
