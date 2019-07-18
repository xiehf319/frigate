package cn.cici.auth.server.security.provider;

import cn.cici.auth.server.security.service.CustomUserDetail;
import cn.cici.auth.server.security.service.CustomUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/18 11:23
 * @author: Heyfan Xie
 */
@Slf4j
public class UsernamePasswordAuthenticationProvider extends DaoAuthenticationProvider {


    public UsernamePasswordAuthenticationProvider(CustomUserDetailService customUserDetailService, PasswordEncoder passwordEncoder) {
        super();
        setUserDetailsService(customUserDetailService);
        setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        if (authentication.getCredentials() == null) {
            log.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
            log.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }

}
