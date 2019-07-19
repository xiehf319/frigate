package cn.cici.auth.server.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/18 15:11
 * @author: Heyfan Xie
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


}
