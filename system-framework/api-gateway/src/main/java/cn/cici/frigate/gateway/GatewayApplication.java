package cn.cici.frigate.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }


    @Bean
    @LoadBalanced
    public OAuth2RestTemplate restTemplate(OAuth2ProtectedResourceDetails resource) {
        BaseOAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails = new BaseOAuth2ProtectedResourceDetails();
        oAuth2ProtectedResourceDetails.setAccessTokenUri(resource.getAccessTokenUri());
        oAuth2ProtectedResourceDetails.setClientId("password");
        oAuth2ProtectedResourceDetails.setClientSecret("123456");
        oAuth2ProtectedResourceDetails.setGrantType("password");
        oAuth2ProtectedResourceDetails.setAuthenticationScheme(AuthenticationScheme.form);
        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails);
    }
}
