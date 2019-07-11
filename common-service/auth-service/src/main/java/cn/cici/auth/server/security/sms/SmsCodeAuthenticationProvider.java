package cn.cici.auth.server.security.sms;

import cn.cici.auth.server.security.service.CustomUserDetailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @description: 手机号登陆检验逻辑
 * @createDate:2019/7/9$16:09$
 * @author: Heyfan Xie
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private CustomUserDetailService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;
        UserDetails userDetails = userDetailService.loadUserByMobile((String) smsCodeAuthenticationToken.getPrincipal());
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("手机号不存在: " + smsCodeAuthenticationToken.getPrincipal());
        }
        // 已认证的token
        SmsCodeAuthenticationToken authenticationToken = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());

        // 复制之前的请求信息到认证后的token中
        authenticationToken.setDetails(smsCodeAuthenticationToken.getDetails());
        return smsCodeAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public CustomUserDetailService getUserDetailService() {
        return userDetailService;
    }

    public void setUserDetailService(CustomUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }
}
