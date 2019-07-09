package cn.cici.auth.server.security.controller;

import cn.cici.frigate.component.vo.R;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @createDate:2019/7/9$18:13$
 * @author: Heyfan Xie
 */
@RestController
public class AuthController {

    @PostMapping("/mobile/token")
    public R<OAuth2AccessToken> login(@RequestBody OAuth2Request request) {
        return null;
    }
}
