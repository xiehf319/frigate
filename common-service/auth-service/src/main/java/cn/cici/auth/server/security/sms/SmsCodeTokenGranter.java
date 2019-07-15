package cn.cici.auth.server.security.sms;

import cn.cici.auth.server.security.service.CustomUserDetailService;
import cn.cici.frigate.sms.api.SmsCodeClient;
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

    private SmsCodeClient smsCodeClient;

    public SmsCodeTokenGranter(CustomUserDetailService userDetailService,
                               SmsCodeClient smsCodeClient,
                               AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userDetailService = userDetailService;
        this.smsCodeClient = smsCodeClient;
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
        // 调用短信服务校验验证码
        smsCodeClient.verify(mobile, smsCode);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        auth.setDetails(parameters);
        OAuth2Request oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(oAuth2Request, auth);
    }
}
