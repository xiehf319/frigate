package cn.cici.auth.server.security.mobile;

import cn.cici.auth.server.security.service.CustomUserDetailService;
import cn.cici.auth.server.security.mobile.MobileAuthenticationToken;
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
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private CustomUserDetailService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
        UserDetails userDetails = userDetailService.loadUserByMobile((String) mobileAuthenticationToken.getPrincipal());
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("手机号不存在: " + mobileAuthenticationToken.getPrincipal());
        }
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(userDetails);
        return mobileAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public CustomUserDetailService getUserDetailService() {
        return userDetailService;
    }

    public void setUserDetailService(CustomUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }
}
