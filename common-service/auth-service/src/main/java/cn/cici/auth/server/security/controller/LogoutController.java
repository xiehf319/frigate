package cn.cici.auth.server.security.controller;

import cn.cici.frigate.component.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @createDate:2019/5/10$11:25$
 * @author: Heyfan Xie
 */
@RestController
@Slf4j
public class LogoutController {

    @Autowired
    private DefaultTokenServices tokenService;

    private TokenExtractor tokenExtractor = new BearerTokenExtractor();

    @PostMapping("/user/invoke")
    public R exit(HttpServletRequest request, HttpServletResponse response) {
        Authentication extract = tokenExtractor.extract(request);
        log.info("token: {}", extract.getPrincipal());
        tokenService.revokeToken(extract.getPrincipal().toString());
        return R.success();
    }
}
