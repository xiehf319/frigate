package cn.cici.auth.server.security.sms;

import cn.cici.auth.server.security.service.CustomUserDetailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: 类介绍：
 * 手机验证码 token授权
 * @createDate: 2019/7/11 14:03
 * @author: Heyfan Xie
 */
public class SmsCodeTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "sms_code";

    private CustomUserDetailService userDetailService;

    private RedisTemplate<String, String> redisTemplate;

    public SmsCodeTokenGranter(RedisTemplate<String, String> redisTemplate, CustomUserDetailService userDetailService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userDetailService = userDetailService;
        this.redisTemplate = redisTemplate;
    }

    protected SmsCodeTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());

        // 手机号
        String mobile = parameters.get("username");

        // 验证码
        String smsCode = parameters.get("smsCode");

        // 获取用户信息
        UserDetails userDetails = userDetailService.loadUserByMobile(mobile);
        if (userDetails == null) {
            throw new InvalidGrantException("用户不存在");
        }

        HashOperations<String, String, String> hashOptions = redisTemplate.opsForHash();
        String smsCodeCached = hashOptions.get("SMS_CODE", mobile);
        if (StringUtils.isBlank(smsCodeCached)) {
            throw new InvalidGrantException("用户没有发送验证码");
        }
        if (!StringUtils.equals(smsCode, smsCodeCached)) {
            throw new InvalidGrantException("验证码错误");
        }
        // 移除
        hashOptions.delete("SMS_CODE", smsCode);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        auth.setDetails(parameters);

        OAuth2Request oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(oAuth2Request, auth);
    }
}
