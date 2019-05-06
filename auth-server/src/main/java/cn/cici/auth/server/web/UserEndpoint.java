package cn.cici.auth.server.web;

import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/users")
@Slf4j
public class UserEndpoint {

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(principal.toString());
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>");
        return principal;
    }
}