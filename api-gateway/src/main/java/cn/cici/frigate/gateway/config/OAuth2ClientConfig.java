//package cn.cici.frigate.gateway.config;
//
//import feign.RequestInterceptor;
//import org.omg.PortableInterceptor.ClientRequestInterceptor;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
//
///**
// * @description:
// * @createDate:2019/5/6$12:06$
// * @author: Heyfan Xie
// */
//@EnableOAuth2Client
//@EnableConfigurationProperties
//@Configuration
//public class OAuth2ClientConfig {
//
//    @Bean
//    @ConfigurationProperties(prefix = "security.oauth2.client")
//    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
//        return new ClientCredentialsResourceDetails();
//    }
//
//    @Bean
//    public RequestInterceptor clientRequestInterceptor() {
//        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
//    }
//
//    @Bean
//    public OAuth2RestTemplate clientCredentialRestTemplate() {
//        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
//    }
//}
