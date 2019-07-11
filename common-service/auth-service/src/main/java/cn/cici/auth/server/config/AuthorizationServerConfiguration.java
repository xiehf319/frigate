package cn.cici.auth.server.config;

import cn.cici.auth.server.security.service.CustomUserDetailService;
import cn.cici.auth.server.security.sms.SmsCodeTokenGranter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 作为认证服务的配置
 * 1.配置支持的授权类型
 * 2.配置token存储方式
 * 3.配置表单控制
 * 4.配置申请token支持的请求方法
 * <p>
 * AuthorizationServer相关地址
 * [/oauth/authorize]
 * [/oauth/token]
 * [/oauth/check_token]
 * [/oauth/confirm_access]
 * [/oauth/token_key]
 * [/oauth/error]
 * <p>
 * <p>
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
    private CustomUserDetailService userServiceDetail;

    @Autowired
    private RedisTokenStore redisTokenStore;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

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
        endpoints.tokenStore(redisTokenStore)
                .tokenGranter(tokenGranter())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .userDetailsService(userServiceDetail)
                .authenticationManager(authenticationManager);
    }


    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(redisTokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    private OAuth2RequestFactory requestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService());
    }


    private TokenGranter tokenGranter() {
        return new TokenGranter() {

            private CompositeTokenGranter delegate;

            @Override
            public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                if (delegate == null) {
                    delegate = new CompositeTokenGranter(getDefaultTokenGranters());
                }
                return delegate.grant(grantType, tokenRequest);
            }
        };
    }

    /**
     * 授权码存储方式
     *
     * @return
     */
    private AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 所有支持的tokenGranter
     */
    private List<TokenGranter> getDefaultTokenGranters() {

        // 客户端信息服务
        ClientDetailsService clientDetailsService = clientDetailsService();

        // 服务端token生成服务
        AuthorizationServerTokenServices tokenServices = tokenServices();

        // 授权码生成服务
        AuthorizationCodeServices authorizationCodeServices = authorizationCodeServices();

        // 处理scope 和 grant_type工厂
        OAuth2RequestFactory requestFactory = requestFactory();

        List<TokenGranter> tokenGranters = new ArrayList<>();
        // 授权码模式
        tokenGranters.add(new AuthorizationCodeTokenGranter(
                tokenServices, authorizationCodeServices, clientDetailsService, requestFactory
        ));
        // 刷新token
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // 隐式模式
        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // 客户端模式
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));
        if (authenticationManager != null) {
            // 用户名密码模式
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(
                    authenticationManager, tokenServices, clientDetailsService, requestFactory
            ));
            // 手机验证码
            tokenGranters.add(new SmsCodeTokenGranter(
                    redisTemplate, userServiceDetail, tokenServices, clientDetailsService, requestFactory
            ));
        }
        return tokenGranters;
    }


    /**
     * 配置token endpoint的安全与权限访问
     * <p>
     * String tokenKeyPath = handlerMapping.getServletPath("/oauth/token_key");
     * String checkTokenPath = handlerMapping.getServletPath("/oauth/check_token");
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
