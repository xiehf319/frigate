package cn.cici.auth.server.config;

import cn.cici.auth.server.security.service.UserServiceDetail;
import cn.cici.auth.server.support.CustomRedisTokenStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 *
 * 作为认证服务的配置
 * 1.配置支持的授权类型
 * 2.配置token存储方式
 * 3.配置表单控制
 * 4.配置申请token支持的请求方法
 *
 * AuthorizationServer相关地址
 * [/oauth/authorize]
 * [/oauth/token]
 * [/oauth/check_token]
 * [/oauth/confirm_access]
 * [/oauth/token_key]
 * [/oauth/error]
 *
 *
 * https://www.cnblogs.com/toov5/p/10327138.html
 *
 * @description:
 * @createDate:2019/4/29$10:57$
 * @author: Heyfan Xie
 */
@Configuration
@EnableAuthorizationServer
@Slf4j
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory factory;

    @Autowired
    private AuthorizationEndpoint authorizationEndpoint;


//    @PostConstruct
//    public void init() {
//        authorizationEndpoint.setUserApprovalPage("forward:/oauth/custom_approval");
//        authorizationEndpoint.setErrorPage("forward:/oauth/custom_error");
//    }

    @Bean
    public TokenStore tokenStore() {
        CustomRedisTokenStore customRedisTokenStore = new CustomRedisTokenStore(factory);
        customRedisTokenStore.setPrefix("FRIGATE-AUTH:");
        return customRedisTokenStore;
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("cici");
        return converter;
    }


    /**
     * 配置客户端的详情信息 也就是 oauth_client_details 表存储的信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    /**
     * 配置token的生成方式和存储方式
     * 以及可以配置授权码模式下存储方式
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 授权存储方式
                .approvalStore(approvalStore())
                // 授权码模式code存储方式，线上使用数据库存储
                // .authorizationCodeServices(authorizationCodeServices())
                // token存储方式
                .tokenStore(tokenStore())
                // 使用jwt增强
                .tokenEnhancer(jwtAccessTokenConverter())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .authenticationManager(authenticationManager);

        // 配置tokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        endpoints.tokenServices(tokenServices);
    }

    /**
     * 配置token endpoint的安全与权限访问
     *
     * 		String tokenKeyPath = handlerMapping.getServletPath("/oauth/token_key");
     * 		String checkTokenPath = handlerMapping.getServletPath("/oauth/check_token");
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
                // 允许表单认证
                .allowFormAuthenticationForClients()
                // /oauth/token_key 不需要鉴权
                .tokenKeyAccess("permitAll()")
                // /oauth/check_token 需要鉴权
                .checkTokenAccess("isAuthenticated()");
    }
}