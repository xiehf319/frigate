package cn.cici.frigate.sso.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author ChengJianSheng
 * @date 2019-02-12
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
//
//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

    @RequestMapping("/oauth/user")
    @ResponseBody
    public Principal user(Principal principal) {
        return principal;
    }

}
