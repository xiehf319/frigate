package cn.cici.auth.server.security.controller;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.component.vo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @description:
 * @createDate:2019/5/6$10:43$
 * @author: Heyfan Xie
 */
@RestController
@Slf4j
public class UserController {

    @RequestMapping(value = "/auth/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        return principal;
    }
}
